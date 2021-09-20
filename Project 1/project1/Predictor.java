package project1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Predictor{
	private String positiveWords;
	private String negativeWords;
	public static double predictedPosPolarity;
	public static double predictedNegPolarity;
	public static double correctPosPolarity;
	public static double correctNegPolarity;
	public static double numPosPolarity;
	public static double numNegPolarity;
	//public static int neutralPolarity;
	public Predictor() {
		negativeWords="negWords.txt";
		positiveWords="posWords.txt";
	}	
	
	public Predictor(String neg, String pos) {
		negativeWords = neg;
		positiveWords = pos;
	}
	
	public int predict(Tweet tw) {
		int negwords = 0;
		
		//Was using this to do a poswords/negwords comparison, went with adding/subtracting from negwords instead.
		//int poswords = 0;
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
					 negwords--;
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
		
		//adding 1 to num if it is pos or neg.
		//Tracks how many of each tweet there is without having to know
		if(tw.getPolarity().equals("0"))
			numNegPolarity += 1;
		if(tw.getPolarity().equals("4"))
			numPosPolarity += 1;
		
		// if the polarity equals 4 and i predicted positive, add one (Correct predictions add one to either neg/pos)
		if(tw.getPolarity().equals("4") && negwords <= 0) 
			correctPosPolarity += 1;		
		else if(tw.getPolarity().equals("0") && negwords > 0)
			correctNegPolarity += 1;

		// if I predict neg or positive, add 1 accordingly.
		if(negwords > 0) {
			predictedNegPolarity += 1;
			return 0;
		}
		else{
			predictedPosPolarity += 1;
			return 4;
		}

	}
	
	//Added to allow reset of all tracking data throughout predictor use. 
	public void reset() {
		predictedPosPolarity = 0;
		predictedNegPolarity = 0;
		correctPosPolarity = 0;
		correctNegPolarity = 0;
		numPosPolarity = 0;
		numNegPolarity = 0;
	}

	@Override
	public String toString() {
		//Will output the actual percentage, even if num of predicted pos/neg tweets are greater than # of actual in existence
		// thanks to trackers in code above.

		double percentPos = (correctPosPolarity / numPosPolarity) * 100;
		double percentNeg = (correctNegPolarity / numNegPolarity) * 100;
		
		if(numPosPolarity == 0)
			percentPos = 0;
		if(numNegPolarity == 0)
			percentNeg = 0;
		
		double percentTotal = ((correctPosPolarity + correctNegPolarity) / (numPosPolarity + numNegPolarity)) * 100;
		
		
		return "Predictor "
				+ "\n Total Positive Tweets: " + numPosPolarity + 
				  "\n Total Negative Tweets: " + numNegPolarity +
				  "\n Predicted Positive Tweets: " + predictedPosPolarity +
				  "\n Predicted Negative Tweets: " + predictedNegPolarity +
				  "\n % of Positives Guessed Right: " + String.format("%.2f",percentPos) +
				 "%\n % of Negatives Guessed Right: " + String.format("%.2f",percentNeg) + "%" +
				  "\n Total Accuracy Percentage: " + String.format("%.2f", percentTotal) + "%";
	}
	
}




