package body_Payloads;

public class Body_Payload_Json {
	public static String get_PostBody()
	{
		String body = "{"
				+ "\"location\":{"
				+ "\"lat\": -33.8669710,"
				+ "\"lng\": 151.1958750},"
				+ "\"accuracy\": 50,"
				+ "\"name\": \"Google Shoes!\","
				+ "\"phone_number\": \"(02) 9374 4000\","
				+ "\"address\": \"48 Pirrama Road, Pyrmont, NSW 2009, Australia\","
				+ "\"types\": [\"shoe_store\"],"
				+ "\"website\": \"http://www.google.com.au/\","
				+ "\"language\": \"en-AU\""
				+ "}";
		return body;
	}
	
	public static String get_DeleteBody(String place)
	{
		String body = "{"+"\"place_id\":\""+place+"\""+"}";
		return body;
	}
}
