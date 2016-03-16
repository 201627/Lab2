package it.polito.tdp.spellchecker.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class TestModel {

	public static void main(String[] args) {

		Dictionary d = new Dictionary();
		d.setLingua("English");
		d.loadDictionary();
		List<String> inputTextList =  new LinkedList<String>();
		String testo = "My home is very beautiful your suchks  ciao  bbya, d342..enjdenk ncjkn ckjds ";
		System.out.println(testo.trim().toLowerCase().replaceAll("[^A-Za-z0-9 ]", ""));
		StringTokenizer st = new StringTokenizer(testo, " ");
		try {
			String s= null;
		
			while ((s = st.nextToken())!= null){
				inputTextList.add(s);
			}
		}catch(Exception e){
			
		}
		List<RichWord> output = new ArrayList<RichWord>(d.spellCheckText(inputTextList));
		for (RichWord rw : output){
			System.out.println(rw.getWord() + " " + rw.isCorretta());
		}
	}

}
