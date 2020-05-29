package it.polito.tdp.artsmia.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private ArtsmiaDAO dao;
	private Graph<Artista, DefaultWeightedEdge> graph;
	private Map<Integer, Artista> idMap;
	private List<Adiacenza> listAdiacenze;
	private int max;
	private List<Artista> best;
	private double bestPeso;
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<>();
		for(Artista a: dao.listArtisti())
			idMap.put(a.getId(), a);
		this.listAdiacenze = new LinkedList<Adiacenza>();
	}
	
	/**
	 * Metodo di creazione del grafo
	 * @param role ruolo degli artisti da considerare
	 */
	public void creaGrafo(String role) {
		this.graph = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.listAdiacenze = dao.listAdiacenze(role, idMap);
		
		for(Adiacenza a: this.listAdiacenze) {
			Artista a1 = a.getA1();
			Artista a2 = a.getA2();
			
			if(!this.graph.containsVertex(a1))
					this.graph.addVertex(a1);
			
			if(!this.graph.containsVertex(a2))
					this.graph.addVertex(a2);
			
			Graphs.addEdge(graph, a1, a2, a.getPeso());
			
					
		}
		
//		return this.graph.edgeSet()+"";
		
	}
	
	public List<Adiacenza> getAdiacenze(){
		Collections.sort(this.listAdiacenze);	
		
		return this.listAdiacenze;
		
	}
	
	
	public List<String> getRole(){
		return this.dao.listRole();
	}
	
	/**
	 * Metodo di ricerca ricorsiva
	 * @param id identificativo dell'artista
	 * @return Stringa esplicativa del percorso più lungo
	 */
	public String recursive(int id) {
		
		if(this.idMap.get(id) == null)
			return "ERRORE: non esiste nessun artista con quell'id";
		
		Artista start = idMap.get(id);
		this.max = 0;
		this.best = new LinkedList<>();
		this.bestPeso = 0;
		List<Artista> parziale = new LinkedList<>();
		parziale.add(start);
		
		for(Artista a1: Graphs.neighborListOf(graph, start)) {
			DefaultWeightedEdge e = this.graph.getEdge(start, a1);
			this.ricerca(parziale, 0, this.graph.getEdgeWeight(e));
		}
			
		
		
		return "Il percorso maggiore è lungo: "+this.max+" vertici \nIl numero di esposizioni coinvolte è:"+this.bestPeso+"\ne contiene i vertici: \n"+this.best;
	}
	
	private void ricerca(List<Artista> parziale, int livello, double peso) {

		Artista last = parziale.get(parziale.size()-1);

		//trovato max
		if(livello>max) {
			this.max=livello;
			this.bestPeso = peso;
			this.best.clear();
			this.best = new LinkedList(parziale);
		}
		
		for(Artista a: Graphs.neighborListOf(graph, last)) {
			DefaultWeightedEdge e = this.graph.getEdge(last, a);
			if(this.graph.getEdgeWeight(e)==peso && !parziale.contains(a)) {

				//creo nuova soluzione
				parziale.add(a);
				
				//chiamata
				this.ricerca(parziale, livello+1, peso);
				
				//backtrack
				parziale.remove(a);
				}
			}
		}
			
		
		

	
	
	
	
	

}
