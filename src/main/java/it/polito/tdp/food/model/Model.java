package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {

	private FoodDao dao;
	
	
	private Graph<String, DefaultWeightedEdge> grafo;       
	private List<Adiacenza> adiacenze;			
     
	private List<String> best;  
	private int max;

	public Model() {
		this.dao = new FoodDao();
		
	}

	public void creaGrafo(double c) {  
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		Graphs.addAllVertices(grafo, dao.getVertici(c));	
		adiacenze= new ArrayList<Adiacenza>(dao.getAdiacenze());
		
		for(Adiacenza a: adiacenze) {

			if(grafo.vertexSet().contains(a.getTipo1()) && grafo.vertexSet().contains(a.getTipo2())){
				if(grafo.getEdge(a.getTipo1(), a.getTipo2())==null)
			        Graphs.addEdge(grafo,a.getTipo1(), a.getTipo2(), (double) a.getPeso());	
			}
		
		}
		System.out.println( "#Vertici "+this.getNVertici()+" "+"#archi= "+this.getNArchi());
}
	

	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}

    public int getNArchi(){
        return this.grafo.edgeSet().size();
    }
	
    public Graph<String, DefaultWeightedEdge> getGrafo(){
    	return this.grafo;
    }
    
    public List<Adiacenza> getCorrelate(String tipo){
    	List<Adiacenza> corr = new ArrayList<>();
    	for(String t: Graphs.neighborListOf(this.grafo, tipo)) {
    		Adiacenza a = new Adiacenza(tipo, t, (int)this.grafo.getEdgeWeight(this.grafo.getEdge(tipo, t)));
    		corr.add(a);
    	}
    	return corr;
    }

    public List<String> percorsoMax(int N, String partenza){ 
    	this.best=null; 
    	this.max=0; 
    	int somma=0;
    	List<String> parziale= new ArrayList<>();
    	parziale.add(partenza);  
    	ricorsione(parziale,N,somma); 
    	return this.best;
    }
    private void ricorsione(List<String> parziale, int n,int somma){
    	
    	if (parziale.size()==n){ 
    		if(this.best==null || somma>this.max){ 
    			this.max=somma;
    			this.best= new ArrayList<>(parziale);
    			return;
    		}
    		else //ho trovato una soluzione ma non è la migliore
    			return;
    	}
    	for(DefaultWeightedEdge e : grafo.outgoingEdgesOf(parziale.get(parziale.size()-1))){ 
    		String prossimo= Graphs.getOppositeVertex(grafo, e, parziale.get(parziale.size()-1)); 
    		
    			if (!parziale.contains(prossimo)){
    				parziale.add(prossimo);
    				somma+= (int)grafo.getEdgeWeight(e);
    				ricorsione(parziale,n,somma); 
    				somma-= (int)grafo.getEdgeWeight(e);
    				parziale.remove(parziale.size()-1);
    			}
    		}
    	}

    public int getPesoMax() {
    	return this.max;
    }
}
	
