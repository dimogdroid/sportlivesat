package difed.entidades;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Frecuencias implements Parcelable{

    /**
     * 
     */
    private String posicion;
    private String satelite;
    private String frecuencia;
    private String symbol;
    private String encriptacion;
    
    
    
    public Frecuencias() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Frecuencias(Parcel in) {
    	readFromParcel(in);
	}
    private void readFromParcel(Parcel in) {
    	posicion = in.readString();
    	satelite = in.readString();
    	frecuencia = in.readString();
    	symbol = in.readString();
    	encriptacion = in.readString();
    }	
	public String getPosicion() {
        return posicion;
    }
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    public String getSatelite() {
        return satelite;
    }
    public void setSatelite(String satelite) {
        this.satelite = satelite;
    }
    public String getFrecuencia() {
        return frecuencia;
    }
    public void setFrecuencia(String frecuencia) {
        this.frecuencia = frecuencia;
    }
    public String getSymbol() {
        return symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getEncriptacion() {
        return encriptacion;
    }
    public void setEncriptacion(String encriptacion) {
        this.encriptacion = encriptacion;
    }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(posicion);
		dest.writeString(satelite);
		dest.writeString(frecuencia);
		dest.writeString(symbol);
		dest.writeString(encriptacion);
		
		
	}
	
	
	public static final Creator<Frecuencias> CREATOR
    = new Creator<Frecuencias>() {
        public Frecuencias createFromParcel(Parcel in) {
            return new Frecuencias(in);
        }
 
        public Frecuencias[] newArray(int size) {
            return new Frecuencias[size];
        }
    };
    
    
    
}
