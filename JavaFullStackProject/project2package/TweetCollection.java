package project2package;
import java.util.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Tim Webb
 * 10/15/2021
 * CSCI 3381
 * 
 */

public class TweetCollection{
	
	private ArrayList<Tweet> tweets;
	private String fileName;  
	private String positiveWords;
	private String negativeWords;
	private ArrayList<String> negativeWordList;
	private ArrayList<String> positiveWordList;
	private static double predictedPosPolarity;
	private static double predictedNegPolarity;
	private static double predictedNeuPolarity;
	private static double correctPosPolarity;
	private static double correctNegPolarity;
	private static double correctNeuPolarity;
	private static double numPosPolarity;
	private static double numNegPolarity;
	private static double numNeuPolarity;
	

	/**
	 * Default Constructor
	 */
	public TweetCollection() {
		fileName="";
		tweets = new ArrayList<Tweet>();
		positiveWordList = new ArrayList<String>();
		negativeWordList = new ArrayList<String>();
		negativeWords="negWords.txt";
		positiveWords="posWords.txt";
		readPredictWords();
	}
	
	
	/**
	 * Parameterized constructor for filename.
	 * 
	 * @param fn   Filename of the tweets list.
	 */
	public TweetCollection(String fn) {
		this();		
		fileName=fn;
		readFile();
		readPredictWords();
	}
	
//	/**
//	 * get Tweet @ index
//	 * @return
//	 */
//	public Tweet getTweetAtIndex(int i) {
//		return tweets.get(i);
//	}
	
	
	public int getSize() {
		return tweets.size();
	}

	
	/**
	 * Adds a tweet to the ArrayList
	 * 
	 * @param tw   Tweet to be added
	 */
	public void add(Tweet tw) {
		if(!tweets.contains(tw)) {
			tweets.add(tw);
		}
	}
	
	/**
	 * Retrieves a tweet based on tweet ID
	 * 
	 * @param rid     ID correlating to some tweet in the collection
	 * @return Tweet  If found, return the tweet.
	 * @return null   If not found, return null.
	 */
	public Tweet get(long rid) {
		int index = tweets.indexOf(new Tweet(rid));
		if(index < 0)
			return null;
			
		return tweets.get(index);
	}
	
	public Tweet getAtIndex(int index){
		return tweets.get(index);
	}
	
	/**
	 * Retrieves all ID's of the tweets in a collection
	 * 
	 * @return ids  A list of ids of all tweets
	 */
	public ArrayList<Long> getIds() {
		ArrayList <Long> ids = new ArrayList<Long>();

		for(int i = 0; i < tweets.size(); i ++) {
			if(!ids.contains(tweets.get(i).getId()))
				ids.add(tweets.get(i).getId());
		}
		return ids;
	}
	
	public void empty() {
		tweets.clear();
	}

	/**
	 * Retrieves all tweet ids of a specific author
	 * 
	 * @param name       A tweets author username
	 * @return toReturn  A list of tweet ids by the author
	 */
	public ArrayList<Long> getUserTweetIds(String name) {
		ArrayList<Long> toReturn = new ArrayList<Long>();
		
		for(int i = 0; i < tweets.size(); i++) {
			if(tweets.get(i).getUser().equals(name)) {
				toReturn.add(tweets.get(i).getId());
			}
		}
		return toReturn;
	}
	
	public boolean contains(Tweet t) {
		if(!tweets.contains(t))
			return false;
		else
			return true;
	}
	/**
	 * Removes a specific tweet from ArrayList
	 * 
	 * @param tw   Tweet object to be removed.
	 */
	public void removeTweet(Tweet tw) {
		if(!tweets.contains(tw))
			System.out.println("Tweet does not exist");
		else
			tweets.remove(tw);
			
	}
	
	/**
	 * Feeds predictor(Tweet tw) with tweets from collection.
	 * 
	 * @param pdAll     Boolean to determine scope of test (First and Last 10 (false), All(true))
	 * @return String   Percentage data of test results.
	 */
	public String predict(int numToPredict) {
		int portion;
		
		if(numToPredict >= (tweets.size() / 2)) {
			for(int i = 0; i < numToPredict; i++) {
				//System.out.println((this.predict(tweets.get(i))));
				predict(tweets.get(i));
			}
		}
		else {
			portion = numToPredict/2;

			for(int i = 0; i < portion; i++) {
				//System.out.println((this.predict(tweets.get(i))));
				predict(tweets.get(i));
			}
			if(numToPredict%2 != 0) {
				portion += 1;
			}
			for(int i = (tweets.size()-portion); i < tweets.size(); i++) {
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
		
		
		return     
	  "Predicted vs Actual Positive Tweets: " + (int)predictedPosPolarity + " | " + (int)numPosPolarity + 
    "\nPredicted vs Actual Negative Tweets: " + (int)predictedNegPolarity + " | " + (int)numNegPolarity +
	 "\nPredicted vs Actual Neutral Tweets: " + (int)predictedNeuPolarity + " | " + (int)numNeuPolarity +
		   "\nCorrect Positive Predictions: " + (int)correctPosPolarity +
		   "\nCorrect Negative Predictions: " + (int)correctNegPolarity +
		    "\nCorrect Neutral Predictions: " + (int)correctNeuPolarity + 
		   "\n% of Positives Guessed Right: " + String.format("%.2f",percentPos) + "%" +
		   "\n% of Negatives Guessed Right: " + String.format("%.2f",percentNeg) + "%" +
		     "\n% of Neutral Guessed Right: " + String.format("%.2f",percentNeu) + "%" + 
			  "\nTotal Accuracy Percentage: " + String.format("%.2f",percentTotal) + "%";
	}
	
	/**
	 * Fed by direct call in tester, or by predict(boolean). Returns a 0 or 4 for testing singles, or updates global variables to do
	 * math on a collection of tweets to output accuracy(%). 
	 * 
	 * @param tw     Tweet that is currently being tested.
	 * @return int   4(Positive), 2(Neutral), 0(Negative).
	 */
	public int predict(Tweet tw) {
		int polarity = 0;
		
		//negative words
		for(int i = 0; i < negativeWordList.size(); i++) {
			if(tw.getBodyText().contains(negativeWordList.get(i))) {
				polarity--;
			}
		}
		for(int i = 0; i < positiveWordList.size(); i++) {
			if(tw.getBodyText().contains(positiveWordList.get(i))) {
				polarity++;
			}
		}
			
		
		/**
		 *  adding 1 to num if it is pos or neg.
		 *  Tracks how many of each tweet there is without having to know
		 */		
		if(tw.getPolarity().equals("0"))
			numNegPolarity += 1;
		if(tw.getPolarity().equals("4"))
			numPosPolarity += 1;
		if(tw.getPolarity().equals("2"))
			numNeuPolarity += 1;
		
		// if the polarity equals 4 and i predicted positive, add one (Correct predictions add one to either neg/pos)
		if(tw.getPolarity().equals("4") && polarity >= 1) 
			correctPosPolarity += 1;		
		else if(tw.getPolarity().equals("0") && polarity <= -1)
			correctNegPolarity += 1;
		else if(tw.getPolarity().equals("2") && (polarity == 0))
			correctNeuPolarity += 1;

		// if I predict neg or positive, add 1 accordingly.
		if(polarity <= -1) {
			predictedNegPolarity += 1;
			return 0;
		}
		else if(polarity >= 1){
			predictedPosPolarity += 1;
			return 4;
		}
		else{
			predictedNeuPolarity += 1;
			return 2;
		}

	}
	
	

	/**
	 * Resets tester variables to reset testing data
	 */
	public void resetPdr() {
		predictedPosPolarity = 0;
		predictedNegPolarity = 0;
		correctPosPolarity = 0;
		correctNegPolarity = 0;
		numPosPolarity = 0;
		numNegPolarity = 0;
		predictedNeuPolarity = 0;
		correctNeuPolarity = 0;
		numNeuPolarity = 0;
	}
	
	/**
	 * Print out relevant data of object.
	 */
	@Override
	public String toString() {
		ArrayList<Tweet> toReturn = new ArrayList<Tweet>();
//		int i = 0;
//		
//		if(tweets.size()>20) {
//			for(i = 0; i < 10; i++) {
//				toReturn.add(tweets.get(i));
//			}
//			for(i = tweets.size() - 10; i < tweets.size(); i++) {
//				toReturn.add(tweets.get(i));
//			}
//			return "" + toReturn + "\n";
//		}
//		else
		return "" + tweets + "\n";
	}
	/**
	 * Returns 
	 * @return
	 */
	public HashSet<String> getAllUsernames(){
		
		HashSet<String> toReturn = new HashSet<String>();
		for(int i = 0; i < tweets.size(); i++) {
			if(!toReturn.contains(tweets.get(i).getUser()))
				toReturn.add(tweets.get(i).getUser()+"\n");
		}
		return toReturn;
	}
	
	/**
	 * Read files of negative/positive word list and store them in separate ArrayLists
	 */
	private void readPredictWords() {
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(negativeWords);
			lineReader = new BufferedReader(fr);
			String line = null;
			while((line = lineReader.readLine())!=null) {
				negativeWordList.add(line);
			}
			fr = new FileReader(positiveWords);
			lineReader = new BufferedReader(fr);
			line = null;
			while((line = lineReader.readLine())!=null) {
				positiveWordList.add(line);
			}
		}
		catch(Exception e) {
			System.err.println("there was a problem with the file reader, try different read type.");
			try {
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(negativeWords.substring(1))));
				String line = null;
				while((line = lineReader.readLine())!= null) {
					negativeWordList.add(line);
				}
				lineReader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(positiveWords.substring(1))));
				line = null;
				while((line = lineReader.readLine())!= null) {
					positiveWordList.add(line);
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
				
			
		
	/**
	 * Read file of tweets and store them in ArrayList
	 */
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
					Tweet tw = new Tweet(parts[0], Long.parseLong(parts[1]), parts[2], parts[3]);
					tweets.add(tw);
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
						tweets.add(new Tweet(parts[0], Long.parseLong(parts[1]), parts[2], parts[3]));
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
