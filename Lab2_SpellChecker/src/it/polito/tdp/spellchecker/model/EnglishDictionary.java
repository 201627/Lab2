package it.polito.tdp.spellchecker.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EnglishDictionary extends Dictionary {
	
	

	public EnglishDictionary() {
		dictionary = new ArrayList<RichWord>();
	}

	@Override
	public void loadDictionary() {
		
		try {
			FileReader fr = new FileReader("rsc/English.txt");
			BufferedReader br = new BufferedReader(fr);
			String riga = null;
			while ((riga = br.readLine())!= null){
				
				dictionary.add(new RichWord(riga.trim().toLowerCase()));		
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RichWord> spellCheckText(List<String> inputTextList) {
		
		LinkedList<RichWord> risultato = new LinkedList<RichWord>();
		for (String s : inputTextList){
			RichWord rw = new RichWord(s);
			if(!dictionary.contains(rw)){
				rw.setCorretta(false);
			}
			risultato.add(rw);
		}
		return risultato;
			
	}

	
}
