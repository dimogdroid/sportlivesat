package difed.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Canales implements Parcelable{
    
    /**
     * 
     */
    private String nombre;
    private List<Frecuencias> lFrecuencias;
    
    
    
    public Canales() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Canales(Parcel in) {

    	lFrecuencias=new ArrayList<Frecuencias>();
		readFromParcel(in);
	}
	public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public List<Frecuencias> getlFrecuencias() {
        return lFrecuencias;
    }
    public void setlFrecuencias(List<Frecuencias> lFrecuencias) {
        this.lFrecuencias = lFrecuencias;
    }
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(nombre);
		dest.writeTypedList(lFrecuencias);
		
	}
	private void readFromParcel(Parcel in) {
		nombre = in.readString();
				
		in.readTypedList(lFrecuencias, Frecuencias.CREATOR);
    }
	public static final Creator<Canales> CREATOR
    = new Creator<Canales>() {
        public Canales createFromParcel(Parcel in) {
            return new Canales(in);
        }
 
        public Canales[] newArray(int size) {
            return new Canales[size];
        }
    };
    

}
