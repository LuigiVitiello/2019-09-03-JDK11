/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Adiacenza;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtCalorie"
    private TextField txtCalorie; // Value injected by FXMLLoader

    @FXML // fx:id="txtPassi"
    private TextField txtPassi; // Value injected by FXMLLoader

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCorrelate"
    private Button btnCorrelate; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="boxPorzioni"
    private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco cammino peso massimo...");
    	
    	int N=0;
    	try {
    		N = Integer.parseInt(this.txtPassi.getText());
    	} catch(NumberFormatException ex) {
    		txtResult.appendText("ERRORE: Il campo soglia deve essere numerico\n");
    		return ;
    	}
    	if(this.model.getGrafo()==null) {
    		this.txtResult.setText("Creare il grafo prima");
    		return;
    	}
    	if(this.boxPorzioni.getValue() == null) {
    		this.txtResult.setText("Scegliere un tipo");
    		return;
    	}
    	List<String> camm = this.model.percorsoMax(N, this.boxPorzioni.getValue());
    	this.txtResult.setText("Cammino di peso: "+this.model.getPesoMax()+" :\n");
    	for(String s: camm) {
    		this.txtResult.appendText(s+"\n");
    	}
    }

    @FXML
    void doCorrelate(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Cerco porzioni correlate...");
    	
    	String tipo = this.boxPorzioni.getValue();
    	if(tipo==null) {
    		this.txtResult.setText("Scegliere un tipo");
    		return;
    	}
    	if(this.model.getGrafo()==null) {
    		this.txtResult.setText("Creare il grafo prima");
    		return;
    	}
    	List<Adiacenza> ad = this.model.getCorrelate(tipo);
    	this.txtResult.setText("Correllate a "+tipo+"\n");
    	for(Adiacenza a: ad) {
    		this.txtResult.appendText(a.getTipo2()+" con peso: "+a.getPeso()+"\n");
    	}
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Creazione grafo...");
    	
    	int c =0;
        try {
    		c = Integer.parseInt(this.txtCalorie.getText());
    	} catch(NumberFormatException ex) {
    		txtResult.appendText("ERRORE: Il campo soglia deve essere numerico\n");
    		return ;
    	}
        
        this.model.creaGrafo(c);
        this.txtResult.setText("# Vertici: "+this.model.getNVertici()+"\n"+"# Archi: "+this.model.getNArchi());
        this.boxPorzioni.getItems().addAll(this.model.getGrafo().vertexSet());
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
        assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
