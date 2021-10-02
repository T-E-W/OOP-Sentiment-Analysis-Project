package project1;

public class Tweet extends TweetCollection{
	private String polarity;
	private long id;
	private String user;
	private String bodyText;
	
	// Default Constructor   
	public Tweet() {
		id = 0;
		polarity = "not set";		
		user = "not set";
		bodyText = "not set";
	}
	// Used for get/remove methods in tweetcollection
	public Tweet(long ids) {
		this();
		id = ids;
	}
	
	
	// Parameterized Constructor
	public Tweet(String polarity, long id, String user, String bodyText) {
		this.polarity = polarity;
		this.id = id;
		this.user = user;
		this.bodyText = bodyText;
	}
	
    //Setters
	public void setPolarity(String polarity) {
		this.polarity = polarity;
	}
	public void setId(long id) {
		this.id = id;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}
	
	//  Getters
	public long getId() {
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
		Tweet tobj = (Tweet) obj;
		return this.getId() == tobj.getId();
	}

	public int compareTo (Object obj) {
		Tweet tobj = (Tweet)obj;
		if(this.getId() == tobj.getId()) {
			return 0;
		}
		if(this.getId() > tobj.getId()) {
			return 1;
		}
		return -1;
	}
	
//  toString, returns the tweet.
	@Override
	public String toString() {
		return "Tweet [polarity=" + polarity + ", id=" + id + ", user=" + user + ", bodyText=" + bodyText + "]";
	}
	
}
