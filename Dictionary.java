import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;

public class Dictionary{
	
	/*
	 * private members
	 */
	private HashSet<String> dict = new HashSet<String>();
	private Iterator<String> i;
	
	/**
	 * Constructor
	 */
	public Dictionary() {
		readFile("Words.txt");
	}
	
	/*
	 * Read in Words.txt to HashSet
	 */
	public void readFile(String fileName) {
		try {
			FileReader readFile = new FileReader(fileName);
		
			BufferedReader bufferRead = new BufferedReader(readFile);
	
			Scanner scan = new Scanner(bufferRead);
		
			String current = "";
		
			while(scan.hasNextLine()) {
				current = scan.nextLine();
				dict.add(current);
			}
			i = dict.iterator();
			scan.close();
		}
		catch(IOException error) {
			error.printStackTrace();
		}
	}
	
	/*
	 * show if Dictionary contains the word
	 */
	public boolean contains(String word) {
		return dict.contains(word);
	}
	
	/**
	 * add in alphabet next to the word to see if it correct
	 * @param word - used to determine if there is correct spelling
	 * @return changed word if correct
	 */
	public String Missing(String word) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		
		for(int i = 0; i <= word.length(); i++) {
			for(int a = 0; a < alphabet.length(); a++) {
				String change = word;
				StringBuilder sb = new StringBuilder(change);
				sb.insert(i, alphabet.charAt(a));
				
				if(contains(sb.toString())) {
					return sb.toString();
				}
			}

		}
		return "0";
	}
	
	
	/**
	 * delete a letter in the String to see if it correct
	 * @param word - used to determine if there is correct spelling
	 * @return changed word if correct
	 */
	public String Add(String word) {
		for(int i = 0; i < word.length(); i++) {
			String change = word;
			StringBuilder sb = new StringBuilder(change);
			sb.deleteCharAt(i);
			if(contains(sb.toString())) {
				return sb.toString();
			}
		}
		return "0";
	}
	

	/**
	 * Reverse the index letter and the next index
	 * @param word - used to determine if there is correct spelling
	 * @return the changed word if correct
	 */
	public String Reverse(String word) {
		for(int i = 0; i < word.length(); i++) {
			char[] change = word.toCharArray();
			if(i < word.length()-1) {
				char tmp = change[i];
				change[i] = change[i+1];
				change[i+1] = tmp;
			}
			String changeWord = String.valueOf(change);
			if(contains(changeWord)) {
				return changeWord;
			}	
		}
		return "0";
	}
	
	
	
	
}
