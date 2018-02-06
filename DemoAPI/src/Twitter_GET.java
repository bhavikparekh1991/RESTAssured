import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import files.Resources;
import files.ReusableMethods;

public class Twitter_GET {
	
/*	String Consumer_Key = "wsiduE05VZqukp0NxRC5K3Fns";
	String Consumer_Secret = "RcDSijYUyrb4uCs6ly35tktGtqfcmzUnZWJcZ6lsS26mqH5tev";
	String Token = "145893875-6rq21NW0gRNqDn35K5q4L2HrVNdKlCKWHCr1zcNR";
	String Token_Secret = "SK0kN8Q8RaLgkrkeL1jWw9NWHccDmzmEI4AiEttlnDlMW";*/
	String TweetCreated;
	
	Properties Repository = new Properties();
	
	@BeforeTest
	public void loadPropertiesFile() throws IOException
	{
		File f = new File(System.getProperty("user.dir") + "//src//files//env.properties");
		FileInputStream FI = new FileInputStream(f);
		Repository.load(FI);
	}
	
	@Test()
	public void Create_Tweet() 
	{
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		queryParam("status", "from REST API Automation").
		when().post(Resources.Create_Tweet()).then().assertThat().statusCode(200).and().
	    contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		
		TweetCreated = js.get("id").toString();
		System.out.println("--------------Tweets After Adding------------------");
		
	}
	
	@Test(priority = 2,dependsOnMethods = { "Create_Tweet" })
	public void Get_Tweets() 
	{
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		queryParam("count", "3").
		when().get(Resources.Get_Tweets()).then().assertThat().statusCode(200).and().
		contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		int count =js.get("text.size");

		for(int i=0; i<count; i++)
		{
			System.out.println(js.getString("text["+i+"]"));
		}
		
	}
	
	@Test(priority = 3,dependsOnMethods = { "Get_Tweets" })
	public void Delete_Tweet() 
	{
	
		RestAssured.baseURI=Repository.getProperty("T_HOST");
		Response res= given().auth().oauth(Repository.getProperty("Consumer_Key"), Repository.getProperty("Consumer_Secret"), Repository.getProperty("Token"), Repository.getProperty("Token_Secret")).
		when().post(Resources.Delete_Tweet(TweetCreated)).then().assertThat().statusCode(200).and().
	    contentType(ContentType.JSON).extract().response();
		
		JsonPath js = ReusableMethods.rawToJson(res);
		System.out.println("Deleted tweet is - " + js.get("text").toString());
		System.out.println("Truncated status is - " + js.get("truncated").toString());
		System.out.println("--------------Tweets After Deleting------------------");
		Get_Tweets();
	}

}
