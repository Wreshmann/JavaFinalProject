package request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Testing_HTTP_Request {
	static String searchUrl =  "https://images-api.nasa.gov/search?keywords={";
	static String imageAndYear = "&media_type=image&year_start=1969&year_end=2019";
	static BufferedReader reader;
	static String line;
	static StringBuffer responseContent = new StringBuffer();
	static String test_full_url = "https://images-api.nasa.gov/search?keywords={earth}";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = searchUrl;
		test = test + "earth}";
		test = test + imageAndYear;
		try {
			URL testUrl = new URL(test);
			String response = getRequest(testUrl);
			System.out.println(response);
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private static void searchPlanet(String planet) {
		switch(planet) {
			case "Mercury":
				//searchMercury();
				break;
			case "Venus":
				//searchVenus();
				break;
			case "Earth":
				//searchEarth();
				break;
			case "Mars":
				//searchMars();
				break;
			case "Jupiter":
				//searchJupiter();
				break;
			case "Neptune":
				//searchNeptune();
				break;
			case "Uranus":
				//searchUranus();
				break;
		}
	}

	private static String getRequest(URL url){
		String jsonResponse = null;
		HttpURLConnection urlconnection = null;
		InputStream inputStream = null;
		try {
			//Request Setup
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			urlconnection.setReadTimeout(10000);
			urlconnection.setConnectTimeout(15000);
			urlconnection.connect();//Connection Established
			
			int status = urlconnection.getResponseCode();//Getting a response code to check success
			System.out.println(status);
			String response = urlconnection.getResponseMessage();
			System.out.println(response);
			System.out.println(urlconnection.getURL());
			String content_type = urlconnection.getContentType();
			System.out.println(content_type);
			
			if (status >299) {
				reader = new BufferedReader(new InputStreamReader(urlconnection.getErrorStream()));//Gets error message from response
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));//Gets JSON response data if successful
				while((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			jsonResponse = responseContent.toString();
			
		} catch(IOException e) {
			//Handle Exception
		}
		finally {
			if (urlconnection != null) {
				urlconnection.disconnect();//Connection Closed
			}
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return jsonResponse;
	}
}
