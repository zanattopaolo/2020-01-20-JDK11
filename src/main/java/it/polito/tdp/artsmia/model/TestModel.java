package it.polito.tdp.artsmia.model;

public class TestModel {

	public static void main(String[] args) {
		Model m = new Model();
		
		m.creaGrafo("Manufacturer");
		System.out.println("Grafo creato!\n");
		
//		for(Adiacenza a: m.getAdiacenze())
//			System.out.println(a.toString());
		
		System.out.println(m.recursive(120));

	}

}
