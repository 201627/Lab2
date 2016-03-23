package it.polito.tdp.spellchecker.controller;

import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.db.DictionaryDAO;
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
import javafx.scene.text.TextFlow;

public class SpellCheckerController {
	
	
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
    private TextFlow txtOutput;

    @FXML
    private Label lblErrori;

    @FXML
    private Button btnClear;

    @FXML
    private Label lblTime;

	private DictionaryDAO ita;

	private Dictionary eng;

    @FXML
    void doCheck(ActionEvent event) {
    	
    	List<RichWord>  outputList;
    	String input;
    	String word;
    	LinkedList<String> inputList = new LinkedList<String>();
    	
    	input = txtInput.getText();
    	input.trim().toLowerCase().replaceAll("[^A-Za-z0-9 ]", "");
    	
    	StringTokenizer st = new StringTokenizer(input, " ");
    	while (st.hasMoreTokens())
    		inputList.add(st.nextToken());
    	
    	
    	double t0 = System.nanoTime();
    	if (cboxLanguage.getValue().equals("Italian")){
    		outputList = ita.spellCheckText(inputList);
    	}else{
    		outputList = eng.spellCheckText(inputList);	
    	}
    	double t1 = System.nanoTime();
    	
    	
    	int numErrori = 0;
    	
    	for (RichWord rw : outputList){
    		Text t = new Text();
    		t.setText(rw.getWord() + " ");
    		if(rw.isCorretta()==false){
    			t.setFill(Color.RED);
    			numErrori++;
    		}
    		
    		txtOutput.getChildren().addAll(t);
    		}
    	
    	lblErrori.setText("Ci sono " + numErrori + " errori!!");
    	lblTime.setText("Tempo impiegato: " + ((t1-t0)/(Math.pow(10, 9))) + "s");
    	
    	}
    

    @FXML
    void doReset(ActionEvent event) {

    	txtInput.clear();
    	txtOutput.getChildren().clear();
    	
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


	public void setModelIta(DictionaryDAO ita2) {
		this.ita = ita2;
		this.ita.loadDictionary();
	}


	public void setModelEng(Dictionary eng) {
		this.eng = eng;
		this.eng.loadDictionary();
	}
}
