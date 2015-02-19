package wucrazylabs.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HTTPTools {
	
	public static String stringFromURL(URL url) throws IOException{
		URLConnection connection = url.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                    connection.getInputStream()));

        StringBuilder response = new StringBuilder();
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
            response.append(inputLine + "\n");

        in.close();

        return response.toString();
	}

	public static String stringFromURL(String url) throws IOException{
		return stringFromURL(new URL(url));
	}
	
}
