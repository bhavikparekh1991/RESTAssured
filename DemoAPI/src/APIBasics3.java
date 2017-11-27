import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import files.resources;
import files.body_payload;
public class APIBasics3 {
	Properties Repository = new Properties();
	@BeforeTest
	public void loadPropertiesFile() throws IOException
	{
		File f = new File(System.getProperty("user.dir") + "//src//files//env.properties");
		FileInputStream FI = new FileInputStream(f);
		Repository.load(FI);
	}
	
	@Test
	public void Add_DeletePlace()
	{
		
		
		//Task 1 Grab the response
		RestAssured.baseURI=Repository.getProperty("HOST");
		Response AddrawRes = given().
				
		queryParam("key", Repository.getProperty("KEY")).
		body(body_payload.get_PostBody()).
		when().
		post(resources.placePostData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).
		extract().response();
		
		//Task 2 Grab place id from the response
		String response= AddrawRes.asString();
		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String place = js.get("place_id");
		Response DeleterawRes =given().queryParam("key", Repository.getProperty("KEY")).
		body(body_payload.get_DeleteBody(place)).
		when().
		post(resources.deletePostedData()).
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("status",equalTo("OK")).
		extract().response();
		
		System.out.println(DeleterawRes.asString());
	}
	
}
