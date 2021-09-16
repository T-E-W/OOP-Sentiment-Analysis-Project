package project1;

public class Project1Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tweet t1 = new Tweet();
		TweetCollection tc = new TweetCollection("tweets.txt");
		
		tc.add(t1);
		//print tweet
		System.out.println(t1);
		//print collection
		System.out.println(tc);
		
		tc.add(new Tweet("1", "444", "Bob", "Hello World"));
		
		System.out.println(tc);
	
		tc.writeFile();
	}
	

}
