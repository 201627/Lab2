package it.polito.tdp.spellchecker.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.spellchecker.model.RichWord;

public class DictionaryDAO {
	
	private List<String> dictionary = new ArrayList<String>();

	public void loadDictionary(){
		
		String jdbcURL = "jdbc:mysql://localhost/dizionario?user=root&password=";
		String sql = "select nome from parola;";
		
		
		try {
			Connection conn = DriverManager.getConnection(jdbcURL);
			
			Statement st = conn.createStatement();
			
			ResultSet res = st.executeQuery(sql);
			
			while (res.next())
				dictionary.add(res.getString("nome"));
			
			System.out.println("Ho caricato il db!");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
		} 
		
		
	}
	
public List<RichWord> spellCheckText(List<String> inputTextList) {
		List<RichWord> res = new LinkedList<RichWord>();
		for(String s : inputTextList){
			RichWord rw = new RichWord(s);
			rw.setCorretta(this.isCorretta(s));
			res.add(rw);
		}
		
		return res;
			
	}
public boolean isCorretta(String s){
	int start = 0 ;
	int end = dictionary.size() - 1 ;
	int half ;
	
	
	while (end > start){
		
		half = ( (start + end)/2);
		
		System.out.println(start +" " + end + "  "+ half);
		if (dictionary.get(half).compareTo(s)==0)
			return true;
		if(dictionary.get(half).compareTo(s)< 0 ){
			// è minore
			end = half;
			
		}else{
			// è maggiore
			start = half +1;
			
		}

	}
	return false;
	
}
public static void main(String[] args){
	DictionaryDAO test = new DictionaryDAO();
	test.loadDictionary();
}
}
