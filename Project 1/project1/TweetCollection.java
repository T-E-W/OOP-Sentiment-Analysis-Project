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
	private String positiveWords;
	private String negativeWords;
	public static double predictedPosPolarity;
	public static double predictedNegPolarity;
	public static double correctPosPolarity;
	public static double correctNegPolarity;
	public static double numPosPolarity;
	public static double numNegPolarity;
	
	
	
	
	
	// TC Default Constructor
	public TweetCollection() {
		fileName="tweets";
		tweets = new ArrayList<Tweet>();
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
		int i = 0;
		while(i < tweets.size() && !tweets.get(i).getId().equals(rid) ) {
			i++;
		}
		
		try {
			if(tweets.get(i).getId().equals(rid))
				return tweets.get(i);
		}
		catch (Exception e) {
			System.err.println("No tweet correlates to id " + rid);
			
		}
		return null;	
	}
	
	// Return list of all IDs of the Tweets in the Collection
	public ArrayList<String> getIds() {
		ArrayList <String> ids = new ArrayList<String>();
		int i;
		for(i = 0; i < tweets.size(); i++){
			ids.add(tweets.get(i).getId());
		}
		return ids;
	}
	
	// Return all IDs of tweets from specific User Name
	public ArrayList<String> getUserTweetIds(String name) {
		ArrayList<String> ids = new ArrayList<String>();
		for(int i = 0; i < tweets.size(); i++) {
			if(tweets.get(i).getUser().equals(name)) {
				ids.add(tweets.get(i).getId());
			}
		}
		return ids;
	}
	
	// Delete/Remove tweet using ID from collection.
	public void remove(String id) {
		int i = 0;

		while(i < tweets.size() && !tweets.get(i).getId().equals(id) ) {
			i++;
		}
		
		try {
			if(tweets.get(i).getId().equals(id))
				tweets.remove(i);
		}
		catch (Exception e) {
			System.err.println("No tweet correlates to id: " + id);
		}

	}
	
	public String predict() {
		for(int i = 0; i < tweets.size(); i++) {
			//System.out.println((this.predict(tweets.get(i))));
			predict(tweets.get(i));
		}
		
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

	public void resetPdr() {
		predictedPosPolarity = 0;
		predictedNegPolarity = 0;
		correctPosPolarity = 0;
		correctNegPolarity = 0;
		numPosPolarity = 0;
		numNegPolarity = 0;
	}
	
	@Override
	public String toString() {
		return "TweetCollection [tweets=" + tweets + "]";
	}
	
	//read/write files as done in HW, edited to fit.
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
