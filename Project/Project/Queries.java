package Project;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

public class Queries{
	private static String searchUrl =  "https://images-api.nasa.gov/search?keywords={";
	private static String imageAndYear = "&page=1&media_type=image&year_start=1969&year_end=2019";
	private static String[] yearArray = {"&page=1&media_type=image&year_start=","start","&year_end=","end"};
	private static String data;
	private static ArrayList<String> imageLinks;
	private static ArrayList<String> imageLinksInfo;
	private static int currentIndex;
	
	
	public static void searchPlanet(String planet) {
		String searchString = searchUrl;
		searchString += planet+"}";
		searchString += imageAndYear;
		URL searchUrl;
		try {
			searchUrl = new URL(searchString);
			data = getRequest(searchUrl);
			ArrayList[] d = distillData(); //scans returned string and sorts relevant information
			imageLinks = d[0];
			imageLinksInfo = d[1];
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public static void searchPlanet(String planet, String yearStart, String yearEnd) {
		yearArray[1] = yearStart;
		yearArray[3] = yearEnd;
		String searchString = searchUrl;
		searchString += planet+"}";
		
		for (int i = 0; i < yearArray.length; i++) {//Loops through Array containing the custom year URL contents
			searchString += i;//Builds the URL with custom year data passed from parameters
		}
		
		URL searchUrl;
		try {
			searchUrl = new URL(searchString);
			data = getRequest(searchUrl);
			ArrayList[] d = distillData(); //scans returned string and sorts relevant information
			imageLinks = d[0];
			imageLinksInfo = d[1];
			
		}catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

		
	private static String getRequest(URL url){
		StringBuffer responseContent = new StringBuffer();
		String jsonResponse = null;
		HttpURLConnection urlconnection = null;
		InputStream inputStream = null;
		BufferedReader reader;
		String line;
		try {
			//Request Setup
			urlconnection = (HttpURLConnection) url.openConnection();
			urlconnection.setRequestMethod("GET");
			urlconnection.setReadTimeout(10000);
			urlconnection.setConnectTimeout(15000);
			urlconnection.connect();//Connection Established
			
				int status = urlconnection.getResponseCode();//Getting a response code to check success
				String response = urlconnection.getResponseMessage();
				String content_type = urlconnection.getContentType();
			
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
	
	
	public static BufferedImage getImage(int i) {
		//pulls an image from imageLinks given an index input
		if (imageLinks.size() != 0) {
			try {
				return ImageIO.read(new URL(imageLinks.get(i)));
				
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IndexOutOfBoundsException e) {
				return null;
			}
		}
		return null;
	}
	
	
	private static ArrayList[] distillData() {
		//breaks down string into tokens containing '.jpg' 
		//and builds an arrayList of all the links and info
		ArrayList<String> links = new ArrayList<String>();
		ArrayList<String> linksInfo = new ArrayList<String>();
		Scanner scan = new Scanner(data);
		scan.useDelimiter(",");
		Object[] tokens = scan.tokens().toArray();
		
		for(Object t: tokens) {
			
			String token = (String) t;
			
			if(token.contains(".jpg")) {
				int start = token.indexOf("https");
				int end = token.indexOf(".jpg")+4;
				String link = token.substring(start,end);
				links.add(link);

			}
			
			String s = "\"description\"";
			if(token.contains(s)) {
				int start = token.indexOf("\":\"");
				s = token.substring(start+3);
				
				linksInfo.add(s);
			}
			
		}
		ArrayList[] returnSet = {links, linksInfo};
		return returnSet;
	}
	
	
	public static BufferedImage getRandomImage() {
		//selects a random index
		if (imageLinks.size() != 0) {
			int randomIndex = (int)(Math.random()*imageLinks.size());
			currentIndex = randomIndex;
			return getImage(randomIndex);
		}
		return null;
	}
	
	
	public static String getImageInfo() {

		return imageLinksInfo.get(currentIndex);
	}
	
	
	public static Object[] nextImage() {
		//increments the index of the array list by one
		currentIndex++;
		if(imageLinks.size()-currentIndex == 0) {
			currentIndex = 0;
		}
		
		Object[] returnSet = new Object[2];
		returnSet[0] = getImage(currentIndex);
		returnSet[1] = imageLinksInfo.get(currentIndex);
		
		return returnSet;
	}
	
	
	public static Object[] previousImage() {
		//decrements the index of the array list by one
		currentIndex--;
		if(imageLinks.size()+currentIndex < imageLinks.size()) {
			currentIndex = imageLinks.size()-1;
		}
		
		Object[] returnSet = new Object[2];
		returnSet[0] = getImage(currentIndex);
		returnSet[1] = imageLinksInfo.get(currentIndex);
		
		return returnSet;
	}
	
	
}
