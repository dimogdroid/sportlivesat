package difed.entidades;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class FechaPartidos implements Parcelable{
	
	
    private String fecha;
    private List<Partidos> lpartidos;
    
   
    
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public List<Partidos> getLpartidos() {
        return lpartidos;
    }
    public void setLpartidos(List<Partidos> lpartidos) {
        this.lpartidos = lpartidos;
    }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(fecha);
		dest.writeTypedList(lpartidos);
		
	}
	public FechaPartidos(){
		super();
	}
	public FechaPartidos (Parcel in) {
		lpartidos=new ArrayList<Partidos>();
		readFromParcel(in);
		
	}
	private void readFromParcel(Parcel in) {
		fecha = in.readString();
		
		in.readTypedList(lpartidos, Partidos.CREATOR);    
	}
	public static final Creator<FechaPartidos> CREATOR
    = new Creator<FechaPartidos>() {
        public FechaPartidos createFromParcel(Parcel in) {
            return new FechaPartidos(in);
        }
 
        public FechaPartidos[] newArray(int size) {
            return new FechaPartidos[size];
        }
    };
    
}
