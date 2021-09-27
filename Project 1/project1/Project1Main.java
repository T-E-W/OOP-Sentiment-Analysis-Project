package project1;

public class Project1Main {

	public static void main(String[] args) {
		
		//Create a TweetCollection and filled with testTweets.txt 
		TweetCollection tc1 = new TweetCollection("testTweets.txt");
		
		//create a tweet
		Tweet t1 = new Tweet("0", "123", "bob", "if i didn't still have a history presentation to pull out of the air by tomorrow; i'd be kicking back with the half-blood prince ");
		System.out.println(tc1);
		
		// Adding a tweet to collection
		tc1.add(t1);
		System.out.println("\nAfter addition of Tweet: " + tc1);
		
		// Removing a tweet from collection
		tc1.removeTweet(t1);
		System.out.println("\nPost removal from " + tc1);
		
		tc1.add(t1);
		// Getting a tweet from collection
		System.out.println("\nTweet correlating to id 15: " + tc1.get("15"));
		
		// Getting tweet ID's from collection by user
		System.out.println("\nAll ids for bob: " + tc1.getUserTweetIds("bob"));
		
		// Getting all ID's in the collection
		System.out.println("\nAll ids: " + tc1.getIds());
		
		// Predicting a single tweet		
		System.out.println("\nOutcome of " + tc1.get("15") + ", \nis(0 = neg, 4 = pos): " + t1.predict(tc1.get("15")) + "\n");
		
		// resetting Predictor Variables. Nice for after addition of tweets to Tweet Collection
		tc1.resetPdr();
		
		// Predicting percent accuracy given a testing of a collection. Input true to do entirety of tweets, false for first and last 10.
		System.out.println("\nPredicting all Tweets in tc1: \n" + tc1.predict(false));
		
		//Equals Method
		System.out.println("\n" + t1.equals(t1));
		
		
		// Writing to file
		tc1.writeFile();
		
		
		
	}
	

}
