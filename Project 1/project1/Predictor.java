package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Predictor{
	private String positiveWords;
	private String negativeWords;
	public static int posPolarity;
	public static int negPolarity;
	public static int neutralPolarity;
	public Predictor() {
		negativeWords="negWords.txt";
		positiveWords="posWords.txt";
//		posPolarity = 0;
//		negPolarity = 0;
	}	
	
	public int predict(Tweet tw) {
		int negwords = 0;
		int poswords = 0;
		BufferedReader lineReader = null;
		FileReader fr;
		
		//negative words
		try {
			fr = new FileReader(negativeWords);
			lineReader = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		String line = null;
		
		try {
			while ((line = lineReader.readLine())!=null) {
				if(tw.getBodyText().toLowerCase().contains(line)) {
					 negwords++;
				}
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			lineReader.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//positive words
		try {
			fr = new FileReader(positiveWords);
			lineReader = new BufferedReader(fr);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	
		line = null;
		
		try {
			while ((line = lineReader.readLine())!=null) {
				if(tw.getBodyText().toLowerCase().contains(line)) {
					 poswords++;
				}
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		try {
			lineReader.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		if(negwords > 1) {
			this.negPolarity += 1;
			return 0;
		}
		else{
			this.posPolarity += 1;
			return 4;
		}

	}

	@Override
	public String toString() {
		return "Predictor [positive tweets=" + posPolarity + ", negative tweets=" + negPolarity + "]";
	}
	
	
}




