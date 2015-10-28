package difed.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import difed.util.CustomLog;

public class Conexion {

	public static final int POST_TYPE = 1;
	public static final int GET_TYPE = 2;

	// private static final String HOME = "http://liveonsat.com/";
	private static final String HOME2 = "?&selTZ=";
	private static final String HOMETODAY = "&selTZ=";

	private static final String HOME = "https://sites.google.com/site/dimogaru/espana/";

	private String pais;
	String url;

	public Conexion(String pais, String gmz) {

		this.pais = pais;
		// url=HOME + pais + HOME2 + gmz;
		url = HOME + pais + ".txt";

	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
		if (pais.indexOf("_day") == 1) {
			this.pais = pais;
			url = HOME + pais + HOMETODAY;
		} else {
			this.pais = pais;
			url = HOME + pais + HOME2;
		}

	}

	public static void checkConnection(Context context)
			throws ConnectionException {
		final ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo networkInfo = connMgr
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		try {

			if (!networkInfo.isConnected()) {
				networkInfo = connMgr
						.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			}
			if (!networkInfo.isConnected()) {
				throw new ConnectionException(ConnectionException.ConnectionError.NO_CONNECTION);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public InputStream conectar(Context context) throws ConnectionException {

		checkConnection(context);

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpRequestBase method = null;
		try {
			method = new HttpPost(url);
		} catch (IllegalArgumentException e) {
			CustomLog.error("conectar", e.getMessage());
		}

		InputStream result = null;
		if (method != null) {
			try {
				HttpResponse response = client.execute(method);
				StatusLine status = response.getStatusLine();
				int codigo = status.getStatusCode();
				CustomLog.debug("conectar", "Status code: " + codigo);
				if (codigo == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						try {
							result = entity.getContent();
						} catch (IOException e) {
							CustomLog.error("conectar", e.getMessage());
							throw new ConnectionException(
									ConnectionException.ConnectionError.READING_ERROR);
						}
					} else {
						CustomLog.error("conectar", status.getReasonPhrase());
						throw new ConnectionException(ConnectionException.ConnectionError.NO_DATA);
					}
				} else {
					throw new ConnectionException(
							ConnectionException.ConnectionError.getErrorByCode(codigo));
				}
			} catch (IOException e) {
				CustomLog.error("conectar", "Error: " + e.getMessage()); // Conexi�n
																			// rechazada
				throw new ConnectionException(
						ConnectionException.ConnectionError.CONNECTION_REJECTED);
			}
		}
		return result;
	}

	public InputStream obtenerPrefijo(String urlInicial)
			throws ConnectionException {

		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		int timeoutConnection = 5000;
		HttpConnectionParams.setConnectionTimeout(httpParameters,
				timeoutConnection);
		DefaultHttpClient client = new DefaultHttpClient(httpParameters);

		HttpRequestBase method = null;
		try {
			method = new HttpPost(urlInicial);
		} catch (IllegalArgumentException e) {
			CustomLog.error("obtenerPrefijo", e.getMessage());
		}

		InputStream result = null;
		if (method != null) {
			try {
				HttpResponse response = client.execute(method);
				StatusLine status = response.getStatusLine();
				int codigo = status.getStatusCode();
				CustomLog.debug("obtenerPrefijo", "Status code: " + codigo);
				if (codigo == 200) {
					HttpEntity entity = response.getEntity();
					if (entity != null) {
						try {
							result = entity.getContent();
						} catch (IOException e) {
							CustomLog.error("conectar", e.getMessage());
							throw new ConnectionException(
									ConnectionException.ConnectionError.READING_ERROR);
						}
					} else {
						CustomLog.error("obtenerPrefijo",
								status.getReasonPhrase());
						throw new ConnectionException(ConnectionException.ConnectionError.NO_DATA);
					}
				} else {
					throw new ConnectionException(
							ConnectionException.ConnectionError.getErrorByCode(codigo));
				}
			} catch (IOException e) {
				CustomLog.error("obtenerPrefijo", "Error: " + e.getMessage()); // Conexi�n
																				// rechazada
				throw new ConnectionException(
						ConnectionException.ConnectionError.CONNECTION_REJECTED);
			}
		}
		return result;
	}

}