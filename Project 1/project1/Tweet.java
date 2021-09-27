package project1;

public class Tweet extends TweetCollection{
	private String polarity;
	private String id;
	private String user;
	private String bodyText;
	
	// Default Constructor 
	public Tweet() {
		id = "not set";
		polarity = "not set";		
		user = "not set";
		bodyText = "not set";
	}
	// Used for get/remove methods in tweetcollection
	public Tweet(String ids) {
		this();
		id = ids;
	}
	
	
	// Parameterized Constructor
	public Tweet(String polarity, String id, String user, String bodyText) {
		super();
		this.polarity = polarity;
		this.id = id;
		this.user = user;
		this.bodyText = bodyText;
	}
	
	
//  Getters
	public String getId() {
		return id;
	}
	public String getUser() {
		return user;
	}
	public String getPolarity() {
		return polarity;
	}
	public String getBodyText() {
		return bodyText;
	}
	
	// Equals method from source. Slightly edited for only relevant information.
	@Override
	public boolean equals(Object obj) {
		Tweet other = (Tweet) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
//  toString, returns the tweet.
	@Override
	public String toString() {
		return "Tweet [polarity=" + polarity + ", id=" + id + ", user=" + user + ", bodyText=" + bodyText + "]";
	}
	
}
