package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SpellCheckerController {
	
	Dictionary model;
	
	public void setModel(Dictionary model){
		this.model =  model;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> cboxLanguage;

    @FXML
    private TextArea txtInput;

    @FXML
    private Button btnCheck;

    @FXML
    private TextArea txtOutput;

    @FXML
    private Label lblErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTime;

	private Dictionary ita;

	private Dictionary eng;

    @FXML
    void doCheck(ActionEvent event) {
    	
    	
    	String input = txtInput.getText();
    	input.trim().toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
    	
    	StringTokenizer st = new StringTokenizer(input, " ");
    	String word = null;
    	LinkedList<String> inputList = new LinkedList<String>();

    	try{
    		while ((word = st.nextToken() )!=null){
    			
    			inputList.add(word);
    			
    		}
    	}catch(NoSuchElementException e){
    		
    	}
    	List<RichWord>  outputList;
    	double t0 = System.nanoTime();
    	if (cboxLanguage.getValue().equals("Italian")){
    		outputList = ita.spellCheckText(inputList);
    	}else{
    		outputList = eng.spellCheckText(inputList);
    		
    	}
    	double t1 = System.nanoTime();
    	
    	Text t = new Text();
    	int i = 0;
    	
    	for (RichWord rw : outputList){
    		t.setText(rw.getWord());
    		if(rw.isCorretta()==false){
    			t.setFill(Color.RED);
    			txtOutput.appendText(rw.getWord()+ " ");
    			i++;
    		}
    		
    		}
    	
    	lblErrori.setText("Ci sono " + i + " errori!!");
    	lblTime.setText("Tempo impiegato: " + (t1-t0)/(10^9) + "s");
    	
    	}
    

    @FXML
    void doReset(ActionEvent event) {

    	txtInput.clear();
    	txtOutput.clear();
    }

    @FXML
    void initialize() {
        assert cboxLanguage != null : "fx:id=\"cboxLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtInput != null : "fx:id=\"txtInput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnCheck != null : "fx:id=\"btnCheck\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtOutput != null : "fx:id=\"txtOutput\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        LinkedList<String> ss = new LinkedList<String>(Arrays.asList("Italian","English"));
        cboxLanguage.getItems().addAll("Italian","English");
        
        }


	public void setModelIta(Dictionary ita) {
		this.ita = ita;
		this.ita.loadDictionary();
	}


	public void setModelEng(Dictionary eng) {
		this.eng = eng;
		this.eng.loadDictionary();
	}
}
