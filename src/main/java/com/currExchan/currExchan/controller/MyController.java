package com.currExchan.currExchan.controller;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.InputStreamReader;
@RestController
public class MyController {
	
	private static HttpURLConnection conn;
	
	@GetMapping("/function")
    public int function(HttpServletRequest req) throws IOException{
		
		
		String fromCurr = req.getParameter("fromCurr");
		int amt1 = Integer.parseInt(req.getParameter("amt1"));
		String targetCurr = req.getParameter("targetCurr");
		
		BufferedReader reader;
		String line;
		StringBuilder responseContent = new StringBuilder();
		
		try{
			URL url = new URL("http://api.exchangeratesapi.io/v1/latest?access_key=75cf42e00b9a97e6cd8c73b1b2653c7c&format=1&symbols="+fromCurr+","+targetCurr);
			conn = (HttpURLConnection) url.openConnection();
			
			// Request setup
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
			conn.setReadTimeout(5000);
			
			// Test if the response from the server is successful
			int status = conn.getResponseCode();
			
			if (status >= 300) {
				reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			else {
				reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);
				}
				reader.close();
			}
			//System.out.println(responseContent.toString());

			JSONObject object = new JSONObject(responseContent.toString());
			JSONObject rates = object.getJSONObject("rates");
			Double from = rates.getDouble(fromCurr);
			Double to = rates.getDouble(targetCurr);
			
			return (int)((amt1*to)/from);
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			conn.disconnect();
		}
		
		
		
		return 0;
		
		
    }
	
}