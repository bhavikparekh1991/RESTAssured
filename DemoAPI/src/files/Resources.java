package files;

public class Resources {
	public static String G_placePostData()
	{
		String res="/maps/api/place/add/json";
		return res;
	}
	
	public static String G_deletePostedData()
	{
		String res="/maps/api/place/delete/json";
		return res;
	}
	
	public static String Create_Tweet()
	{
		String res="/update.json";
		return res;
	}
	
	public static String Get_Tweets()
	{
		String res="/user_timeline.json";
		return res;
	}
	
	
	public static String Delete_Tweet(String TweetId)
	{
		String res="/destroy/"+TweetId+".json";
		return res;
	}
	
	
}
