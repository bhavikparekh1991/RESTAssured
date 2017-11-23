import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class APIBasics {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//BaseURL or Host
	RestAssured.baseURI="https://maps.googleapis.com";
	
	//given() has Request headers, Parameters, Request cookies
	//when() has get(resource) or put(resource) or post(resource)
	//then() has assertions to make sure we are getting correct response
	given().
	       param("location","-33.8670522,151.1957362").
	       param("radius","500").
	       param("key","AIzaSyDTuIBpunv2dYjYTY1w5QRWyPVNPszBpU8").
	       when().
	       get("/maps/api/place/nearbysearch/json").
	       then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
	       body("results[0].name",equalTo("Sydney"));
	       
	       /*header("key","value").
	       cookie("value").
	       body()*/
	
	
	
	
	//extract() in this we can pull out body response
	
	}

}
