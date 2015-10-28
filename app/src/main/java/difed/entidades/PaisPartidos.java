package difed.entidades;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class PaisPartidos implements Parcelable{
    private String url;
    private String gmz;
    private List<FechaPartidos> lpartidos;
    
    private String pais;
    
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getGmz() {
        return gmz;
    }
    public void setGmz(String gmz) {
        this.gmz = gmz;
    }
    public List<FechaPartidos> getLpartidos() {
        return lpartidos;
    }
    public void setLpartidos(List<FechaPartidos> lpartidos) {
        this.lpartidos = lpartidos;
    }
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(url);
		dest.writeString(gmz);
		dest.writeTypedList(lpartidos);
		
	}
	
	public PaisPartidos(){
		super();
	}
	public PaisPartidos (Parcel in) {
		lpartidos=new ArrayList<FechaPartidos>();
		readFromParcel(in);
		
	}
	private void readFromParcel(Parcel in) {
		url = in.readString();
		gmz = in.readString();
		in.readTypedList(lpartidos, FechaPartidos.CREATOR);    
	}
	public static final Creator<PaisPartidos> CREATOR
    = new Creator<PaisPartidos>() {
        public PaisPartidos createFromParcel(Parcel in) {
            return new PaisPartidos(in);
        }
 
        public PaisPartidos[] newArray(int size) {
            return new PaisPartidos[size];
        }
    };
    
    
}
