package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Artista a1;
	private Artista a2;
	private int peso;
	
	public Adiacenza(Artista a1, Artista a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public Artista getA1() {
		return a1;
	}

	public void setA1(Artista a1) {
		this.a1 = a1;
	}

	public Artista getA2() {
		return a2;
	}

	public void setA2(Artista a2) {
		this.a2 = a2;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Adiacenza other) {
		return -(this.peso-other.peso);
	}

	@Override
	public String toString() {
		return "Adiacenza: " + a1 + ", " + a2 + ", peso=" + peso + "";
	}

	
	
	

}
