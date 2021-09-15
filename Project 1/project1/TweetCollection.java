package project1;
import java.util.ArrayList;


public class TweetCollection {
	private ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	
	
	// Add New Tweet To Collection
	public void add(Tweet tw) {
		tweets.add(tw);
	}
	
	// Retrieve tweet based off ID;
	public Tweet get(String rid) {
		int index = tweets.indexOf(new Tweet(rid));
		if(index > -1) {
			Tweet searched = tweets.get(index);
			return searched;
		}
		else
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
			if(tweets.get(i).getUser() == name) {
				ids.add(tweets.get(i).getUser());
			}
		}
		return ids;
	}
	
	// Delete/Remove tweet using ID from collection.
	public void remove(Tweet tw) {
		tweets.remove(tw);
	}

	
}
