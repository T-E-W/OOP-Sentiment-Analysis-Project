package project1;
import java.util.*;

import javax.swing.text.html.HTMLDocument.Iterator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;


public class TweetCollection {
	private ArrayList<Tweet> tweets;
	private String fileName;
	
	public TweetCollection() {
		fileName="tweets";
		tweets = new ArrayList<Tweet>();
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
			System.err.println("No tweet correlates to id: " + rid);
			
		}
		return null;		
	}
	
	// Return list of all IDs of the Tweets in the Collection
	public ArrayList<String> getIds() {
		ArrayList <String> ids = new ArrayList<String>();
		
		for(int i = 0; i < tweets.size(); i++){
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
	
	@Override
	public String toString() {
		return "TweetCollection [tweets=" + tweets + "]";
	}
	
	private void readFile () {
		BufferedReader lineReader = null;
		try {
			FileReader fr = new FileReader(fileName);
			lineReader = new BufferedReader(fr);
			String line = null;
			while ((line = lineReader.readLine())!=null) {
				
				String[] parts = line.split(",");
				Tweet tw = new Tweet(parts[0], parts[1], parts[2], parts[3]);
				tweets.add(tw);
				
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
		// this method writes all of the data in the persons array to a file
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
