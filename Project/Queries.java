package image;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import gui.DisplayImage;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Queries{
	static String searchUrl =  "https://images-api.nasa.gov/search?q={";//Need to add closing bracket in methods
	static String imageAndYear = "&page=1&media=image&yearStart=1969&yearEnd=2019";
	static BufferedReader reader;
	static String line;
	static StringBuffer responseContent = new StringBuffer();

	Queries(){
		getRequest(null);
	}
	private static void searchMercury() {
		String mercuryString = searchUrl;
		mercuryString = mercuryString + "Mercury}";//Example URL String being generated
		mercuryString = mercuryString + imageAndYear;
		URL mercuryUrl;
		try {
			mercuryUrl = new URL(mercuryString);//Building our URL with the string above
			getRequest(mercuryUrl);//Calling GET request method with our built URL
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchVenus() {
		String venusString = searchUrl;//The above behavior is mimiced for each URL
		venusString = venusString + "Venus}";
		venusString = venusString + imageAndYear;
		URL venusUrl;
		try {
			venusUrl = new URL(venusString);
			getRequest(venusUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchEarth() {
		String earthString = searchUrl;
		earthString = earthString + "Earth}";
		earthString = earthString + imageAndYear;
		URL earthUrl;
		try {
			earthUrl = new URL(earthString);
			getRequest(earthUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchMars() {
		String marsString = searchUrl;
		marsString = marsString + "Mars}";
		marsString = marsString + imageAndYear;
		URL marsUrl;
		try {
			marsUrl = new URL(marsString);
			getRequest(marsUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchJupiter() {
		String jupiterString = searchUrl;
		jupiterString = jupiterString + "Jupiter}";
		jupiterString = jupiterString + imageAndYear;
		URL jupiterUrl;
		try {
			jupiterUrl = new URL(jupiterString);
			getRequest(jupiterUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchSaturn() {
		String saturnString = searchUrl;
		saturnString = saturnString + "Saturn}";
		saturnString = saturnString + imageAndYear;
		URL saturnUrl;
		try {
			saturnUrl = new URL(saturnString);
			getRequest(saturnUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchNeptune() {
		String neptuneString = searchUrl;
		neptuneString = neptuneString + "Neptune}";
		neptuneString = neptuneString + imageAndYear;
		URL neptuneUrl;
		try {
			neptuneUrl = new URL(neptuneString);
			getRequest(neptuneUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static void searchUranus() {
		String uranusString = searchUrl;
		uranusString = uranusString + "Uranus}";
		uranusString = uranusString + imageAndYear;
		URL uranusUrl;
		try {
			uranusUrl = new URL(uranusString);
			getRequest(uranusUrl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			System.out.println(jsonResponse);
			System.out.println(responseContent.toString());//Content is converted to string and printed
			
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
