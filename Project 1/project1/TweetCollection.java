package project1;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;


public class TweetCollection{
	private ArrayList<Tweet> tweets;
	private String fileName;

//	Variables for predictor    
	private String positiveWords;
	private String negativeWords;
	private static double predictedPosPolarity;
	private static double predictedNegPolarity;
	private static double predictedNeuPolarity;
	private static double correctPosPolarity;
	private static double correctNegPolarity;
	private static double correctNeuPolarity;
	private static double numPosPolarity;
	private static double numNegPolarity;
	private static double numNeuPolarity;
	
	// TC Default Constructor
	public TweetCollection() {
		fileName="";
		tweets = new ArrayList<Tweet>();
		//negative & positive words for predictor
		negativeWords="negWords.txt";
		positiveWords="posWords.txt";
		
	}
	public TweetCollection(String fn) {
		this();		
		fileName=fn;
		readFile();
	}
	
	// Add New Tweet To Collection
	public void add(Tweet tw) {
		tweets.add(tw);
	}
	
	// Retrieve tweet based off ID;
	public Tweet get(String rid) {
		int index = tweets.indexOf(new Tweet(rid));
		return tweets.get(index);
	}
	
	// Return list of all IDs of the Tweets in the Collection
	public ArrayList<String> getIds() {
		ArrayList <String> ids = new ArrayList<String>();
		int i;
		if(tweets.size()>20) {
			for(i = 0; i < 10; i++){
				ids.add(tweets.get(i).getId());
			}
			for(i = tweets.size() - 10; i < tweets.size(); i ++) {
				ids.add(tweets.get(i).getId());
			}
			return ids;
		}
		else
			for(i = 0; i < tweets.size(); i ++) {
				ids.add(tweets.get(i).getId());
			}
			return ids;
	}
	
	// Return all IDs of tweets from specific User Name
	public ArrayList<String> getUserTweetIds(String name) {
		ArrayList<String> toReturn = new ArrayList<String>();
		for(int i = 0; i < tweets.size(); i++) {
			if(tweets.get(i).getUser().equals(name)) {
				toReturn.add(tweets.get(i).getId());
			}
		}
		return toReturn;
	}
	
	// Delete/Remove tweet from collection.
	public void removeTweet(Tweet tw) {
		tweets.remove(tw);
	}
	
	// Feeds predictor(Tweet tw) with tweets from collection. Takes a boolean for testing first and last 10 tweets(false) or all(true).
	public String predict(boolean pdAll) {
		if(pdAll == true) {
			for(int i = 0; i < tweets.size(); i++) {
				//System.out.println((this.predict(tweets.get(i))));
				predict(tweets.get(i));
			}
		}
		else {
			for(int i = 0; i < 10; i++) {
				predict(tweets.get(i));
			}
			for(int i = tweets.size()-10; i < tweets.size(); i++) {
				predict(tweets.get(i));
			}
		}
		
		double percentPos = (correctPosPolarity / numPosPolarity) * 100;
		double percentNeg = (correctNegPolarity / numNegPolarity) * 100;
		double percentNeu = (correctNeuPolarity / numNeuPolarity) * 100;
		
		if(numPosPolarity == 0)
			percentPos = 0;
		if(numNegPolarity == 0)
			percentNeg = 0;
		if(numNeuPolarity == 0)
			percentNeu = 0;
		
		double percentTotal = ((correctPosPolarity + correctNegPolarity + correctNeuPolarity) / (numPosPolarity + numNegPolarity + numNeuPolarity)) * 100;
		
		
		return "Predictor Results: "
		     + "\n  Actual # Positive Tweets: " + (int)numPosPolarity + 
			   "\n  Actual # Negative Tweets: " + (int)numNegPolarity +
			    "\n  Actual # Neutral Tweets: " + (int)numNeuPolarity + 
			  "\n  Predicted Positive Tweets: " + (int)predictedPosPolarity +
			  "\n  Predicted Negative Tweets: " + (int)predictedNegPolarity +
			   "\n  Predicted Neutral Tweets: " + (int)predictedNeuPolarity + 
		   "\n  Correct Positive Predictions: " + (int)correctPosPolarity +
		   "\n  Correct Negative Predictions: " + (int)correctNegPolarity +
		    "\n  Correct Neutral Predictions: " + (int)correctNeuPolarity + 
		   "\n  % of Positives Guessed Right: " + String.format("%.2f",percentPos) + "%" +
		   "\n  % of Negatives Guessed Right: " + String.format("%.2f",percentNeg) + "%" +
		     "\n  % of Neutral Guessed Right: " + String.format("%.2f",percentNeu) + "%" + 
			  "\n  Total Accuracy Percentage: " + String.format("%.2f",percentTotal) + "%";
	}
	
	// Fed by direct call in tester, or by predict(boolean). Returns a 0 or 4 for testing singles, or updates global variables to do
	// math on a collection of tweets to output accuracy(%). 
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
		
		// adding 1 to num if it is pos or neg.
		// Tracks how many of each tweet there is without having to know
		if(tw.getPolarity().equals("0"))
			numNegPolarity += 1;
		if(tw.getPolarity().equals("4"))
			numPosPolarity += 1;
		if(tw.getPolarity().equals("2"))
			numNeuPolarity += 1;
		
		// if the polarity equals 4 and i predicted positive, add one (Correct predictions add one to either neg/pos)
		if(tw.getPolarity().equals("4") && negwords < 1) 
			correctPosPolarity += 1;		
		else if(tw.getPolarity().equals("0") && negwords > 2)
			correctNegPolarity += 1;
		else if(tw.getPolarity().equals("2") && (negwords <=2 || negwords >=1))
			correctNeuPolarity += 1;

		// if I predict neg or positive, add 1 accordingly.
		if(negwords > 2) {
			predictedNegPolarity += 1;
			return 0;
		}
		else if(negwords < 1){
			predictedPosPolarity += 1;
			return 4;
		}
		else{
			predictedNeuPolarity += 1;
			return 2;
		}

	}

	// Resets global variables so that we can add new tweets after a prediction in main and reset to prevent duplicate prediction %'s
	public void resetPdr() {
		predictedPosPolarity = 0;
		predictedNegPolarity = 0;
		correctPosPolarity = 0;
		correctNegPolarity = 0;
		numPosPolarity = 0;
		numNegPolarity = 0;
	}
	
	//toString
	@Override
	public String toString() {
		ArrayList<Tweet> toReturn = new ArrayList<Tweet>();
		int i = 0;
		
		if(tweets.size()>20) {
			for(i = 0; i < 10; i++) {
				toReturn.add(tweets.get(i));
			}
			for(i = tweets.size() - 10; i < tweets.size(); i++) {
				toReturn.add(tweets.get(i));
			}
			return "TweetCollection [tweets=" + toReturn + "]";
		}
		else
			return "TweetCollection [tweets=" + tweets + "]";
	}
	
	//read/write files, edited to fit from HW.
	private void readFile () {
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String line = null;
			// While line is not empty, split line, make new tweet(tw), add it
			while ((line = lineReader.readLine())!=null) {
				if(line != null) {
					String[] parts = line.split(",");
					Tweet tw = new Tweet(parts[0], parts[1], parts[2], parts[3]);
					tweets.add(new Tweet(parts[0], parts[1], parts[2], parts[3]));
				}
				
			}
		} catch (Exception e) {
			System.err.println("there was a problem with the file reader, try different read type.");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName.substring(1))));
				String line = null;
				while ((line = lineReader.readLine())!=null) {
					String[] parts = lineReader.readLine().split(",");
					for(String part : parts) {
						tweets.add(new Tweet(parts[1], parts[2], parts[3], parts[4]));
					}
				}
			} catch (Exception e2) {
				System.err.println("there was a problem with the file reader, try again.  either no such file or format error");
			} finally {
				if (lineReader != null)
					try {
						lineReader.close();
					} catch (IOException e2) {
						System.err.println("could not close BufferedReader");
					}
			}			
		} finally {
			if (lineReader != null)
				try {
					lineReader.close();
				} catch (IOException e) {
					System.err.println("could not close BufferedReader");
				}
		}
	} // end of readFile method	
	public void writeFile () {
		// overloaded method: this calls doWrite with file used to read data
		// use this for saving data between runs
		doWrite(fileName);
	} // end of writeFile method

	public void writeFile(String altFileName) {
		// overloaded method: this calls doWrite with different file name 
		// use this for testing write
		doWrite(altFileName);		
	}// end of writeFile method
	
	private void doWrite(String fn) {
		// this method writes all of the data in the Tweets array to a file.
		try
		{

			FileWriter fw = new FileWriter(fn);
			BufferedWriter myOutfile = new BufferedWriter(fw);
			
			for (int i = 0; i < tweets.size(); i++) {
				Tweet tweet = tweets.get(i);
				if (tweet instanceof Tweet) {
					myOutfile.write (tweet.getPolarity()+","+tweet.getId()+","+tweet.getUser()+","+tweet.getBodyText()+"\n");
					
				}
				else {
					System.err.println("error in array, instance type not found");
				}
			}
			myOutfile.flush();
			myOutfile.close();
		}
		catch (Exception e) {
			e.printStackTrace();
			System.err.println("Didn't save to " + fn);
		}
	}	
}
