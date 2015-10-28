package difed.entidades;


import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class Partidos implements Parcelable{

    
    private String frase;
    private String frase2;
    private String imagem_png;
    private String hora;
    private List<Canales> lcanales;
    
    public String getImagem_png() {
        return imagem_png;
    }
    public void setImagem_png(String imagemPng) {
        imagem_png = imagemPng;
    }
    public String getHora() {
        return hora;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public List<Canales> getLcanales() {
        return lcanales;
    }
    public void setLcanales(List<Canales> lcanales) {
        this.lcanales = lcanales;
    }
    public String getFrase() {
        return frase;
    }
    public void setFrase(String frase) {
        this.frase = frase;
    }
	public String getFrase2() {
		return frase2;
	}
	public void setFrase2(String frase2) {
		this.frase2 = frase2;
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		
		dest.writeString(frase);
		dest.writeString(frase2);
		dest.writeString(imagem_png);
		dest.writeString(hora);
		dest.writeTypedList(lcanales);
		
	}
	
	
    
	public Partidos() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Partidos (Parcel in) {
		lcanales=new ArrayList<Canales>();
		readFromParcel(in);
		
	}
	private void readFromParcel(Parcel in) {
		frase = in.readString();
		frase2 = in.readString();
		imagem_png = in.readString();
		hora = in.readString();
		
		in.readTypedList(lcanales, Canales.CREATOR);
    }
    
	public static final Creator<Partidos> CREATOR
    = new Creator<Partidos>() {
        public Partidos createFromParcel(Parcel in) {
            return new Partidos(in);
        }
 
        public Partidos[] newArray(int size) {
            return new Partidos[size];
        }
    };
    
    
    
}
