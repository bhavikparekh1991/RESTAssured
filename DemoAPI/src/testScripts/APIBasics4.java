package testScripts;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import reusableMethods.ReusableMethods;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;


public class APIBasics4 {

	@Test
	public void test1() 
	{
		//BaseURL or Host
		RestAssured.baseURI="https://maps.googleapis.com";
		//given() has Request headers, Parameters, Request cookies
		//when() has get(resource) or put(resource) or post(resource)
		//then() has assertions to make sure we are getting correct response
		Response res =given().
		       param("location","-33.8670522,151.1957362").
		       param("radius","500").
		       param("key","AIzaSyDTuIBpunv2dYjYTY1w5QRWyPVNPszBpU8").log().all().
		       when().
		       get("/maps/api/place/nearbysearch/json").
		       then().assertThat().statusCode(200).and().
		       contentType(ContentType.JSON).and().
		       body("results[0].name",equalTo("Sydney")).and().
		       body("results[0].place_id",equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
		       header("Server", "scaffolding on HTTPServer2").log().ifValidationFails().extract().response();
		JsonPath js = ReusableMethods.rawToJson(res);
		int count = js.get("results.size");
		System.out.println(count);
		for(int i=0; i<count; i++)
		{
			System.out.println(js.getString("results["+i+"].name"));
		}
		/*header("key","value").
		cookie("value").
		body()*/
		//extract() in this we can pull out body response
	}

}
