import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import org.testng.annotations.Test;

import files.ReusableMethods;
public class tweet {

	String Consumer_Key = "wsiduE05VZqukp0NxRC5K3Fns";
	String Consumer_Secret = "RcDSijYUyrb4uCs6ly35tktGtqfcmzUnZWJcZ6lsS26mqH5tev";
	String Token = "145893875-6rq21NW0gRNqDn35K5q4L2HrVNdKlCKWHCr1zcNR";
	String Token_Secret = "SK0kN8Q8RaLgkrkeL1jWw9NWHccDmzmEI4AiEttlnDlMW";
	@Test
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
}
