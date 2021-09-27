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
		System.out.println("\n After addition of Tweet: " + tc1);
		
		// Removing a tweet from collection
		tc1.remove("123");
		System.out.println("\n Post removal of tweet: " + tc1);
		
		tc1.add(t1);
		// Getting a tweet from collection
		System.out.println("Tweet correlating to id 123: " + tc1.get("123"));
		
		// Getting tweet ID's from collection by user
		System.out.println("All ids for bob: " + tc1.getUserTweetIds("bob"));
		
		// Getting all ID's in the collection
		System.out.println("All ids: " + tc1.getIds());
		
		// Predicting a single tweet		
		System.out.println("\nOutcome of " + t1 + ", \nFor tweet: " + t1.predict(t1) + "\n");
		
		// resetting Predictor Variables.
		tc1.resetPdr();
		
		// Predicting percent accuracy given a testing of a collection
		System.out.println("\nPredicting all Tweets in tc1: \n" + tc1.predict());
		
		// Writing to file
		tc1.writeFile();
		
		
		
	}
	

}
