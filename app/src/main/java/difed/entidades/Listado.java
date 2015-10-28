package difed.entidades;

import android.graphics.drawable.Drawable;

public class Listado {
	
	private int id;
	
	protected Drawable bandera;
	private String texto;
	private String pais;
	private boolean titulo;
	
	

	public Listado(int i, Drawable bandera, String texto, String pais,boolean titulo) {
		super();
		this.id = i;
		this.bandera=bandera;
		this.texto = texto;
		this.pais=pais;
		this.titulo = titulo;
	}
	
	



	public String getPais() {
		return pais;
	}





	public void setPais(String pais) {
		this.pais = pais;
	}





	public Drawable getBandera() {
		return bandera;
	}





	public void setBandera(Drawable bandera) {
		this.bandera = bandera;
	}





	public boolean isTitulo() {
		return titulo;
	}

	public void setTitulo(boolean titulo) {
		this.titulo = titulo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	
	
}
