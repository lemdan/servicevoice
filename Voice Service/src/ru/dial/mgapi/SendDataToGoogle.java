package ru.dial.mgapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class SendDataToGoogle {
	
	URL url;
	URLConnection connection;
	String response;
	
	public String sendFLACStream(OutputStream flacStream) throws Exception{
		
		byte[] flacByteArray = ((ByteArrayOutputStream)flacStream).toByteArray();
		connectToServerGoogle();
		
		OutputStream out = connection.getOutputStream();
		
		for (int i = 0; i < flacByteArray.length; i++) {
			out.write(flacByteArray[i]);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		out.close();
		
		response = br.readLine();
		
		 br.close();
		
		return response;
	}
	
	private void connectToServerGoogle() throws Exception{
		url = new URL("https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium&lang=ru-RU");
		connection = url.openConnection();
		
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "audio/x-flac; rate=8000");
		connection.setRequestProperty("Accept-Charset", "UTF-8");
		
       
        		
}
	
	public String[] parseResponse(String rawResponse) {
        if (!rawResponse.contains("utterance"))
            return null;

        String[] parsedResponse = new String[2];

        String[] strings = rawResponse.split(":");

        parsedResponse[0] = strings[4].split("\"")[1];
        parsedResponse[1] = strings[5].replace("}]}", "");

        return parsedResponse;
    }
}

