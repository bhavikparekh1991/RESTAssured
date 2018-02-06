import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;
import files.ReusableMethods;

public class Twitter_GET {
	
	String Consumer_Key = "wsiduE05VZqukp0NxRC5K3Fns";
	String Consumer_Secret = "RcDSijYUyrb4uCs6ly35tktGtqfcmzUnZWJcZ6lsS26mqH5tev";
	String Token = "145893875-6rq21NW0gRNqDn35K5q4L2HrVNdKlCKWHCr1zcNR";
	String Token_Secret = "SK0kN8Q8RaLgkrkeL1jWw9NWHccDmzmEI4AiEttlnDlMW";
	String TweetCreated;
	@Test
	public void Create_Tweet() 
	{
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		Response res= given().auth().oauth(Consumer_Key, Consumer_Secret, Token, Token_Secret).
		queryParam("status", "from REST API Automation").
		when().post("/update.json").then().assertThat().statusCode(200).and().
	       contentType(ContentType.JSON).extract().response();
		
		
		//System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(res);
		//System.out.println(js.get("text").toString());
		//System.out.println(js.get("id").toString());
		TweetCreated = js.get("id").toString();
		System.out.println("Tweets After Adding");
		
	}
	
	@Test(priority = 2,dependsOnMethods = { "Create_Tweet" })
	public void Get_Tweets() 
	{
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		Response res= given().auth().oauth(Consumer_Key, Consumer_Secret, Token, Token_Secret).
		queryParam("count", "3").
		when().get("/user_timeline.json").then().assertThat().statusCode(200).and().
	       contentType(ContentType.JSON).extract().response();
		
		
		//System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(res);
		int count =js.get("text.size");
		System.out.println(count);
		for(int i=0; i<count; i++)
		{
			System.out.println(js.getString("text["+i+"]"));
		}
		
	}
	
	@Test(priority = 3,dependsOnMethods = { "Get_Tweets" })
	public void Delete_Tweet() 
	{
	
		RestAssured.baseURI="https://api.twitter.com/1.1/statuses";
		Response res= given().auth().oauth(Consumer_Key, Consumer_Secret, Token, Token_Secret).
		when().post("/destroy/"+TweetCreated+".json").then().assertThat().statusCode(200).and().
	    contentType(ContentType.JSON).extract().response();
		
		
		//System.out.println(response);
		JsonPath js = ReusableMethods.rawToJson(res);
		System.out.println(js.get("text").toString());
		System.out.println(js.get("truncated").toString());
		System.out.println("Tweets After deleting");
		Get_Tweets();
		
	}

}
