package ru.dial.mgapi;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class SendDataToGoogle {
	
	URL url;
	URLConnection connection;
	String response;
	private final static String URL = "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium&lang=ru-RU";
	
	/**
	 * Constructor.Create a URLConnection with Google Service to the default string.
	 * Default string - "https://www.google.com/speech-api/v1/recognize?xjerr=1&client=chromium&lang=ru-RU"
	 */
	public SendDataToGoogle() {
		try {
			url = new URL(URL);
			connectToServerGoogle();
			
		} catch (IOException e) {
			System.err.print("Service not avalible or not correct URL");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Constructor. Create a URLConnection with Google Service to the specified string. 
	 * @param urlServiceGoogle - specified string.
	 * @param contentType - Content Type
	 * @param charset - Accept Charset
	 * @throws IOException
	 */
	public SendDataToGoogle(String urlServiceGoogle, String contentType, String charset) throws IOException{
		url = new URL(urlServiceGoogle);
		connectToServerGoogle(contentType, charset);
	}
	
	/**
	 * Send to Google Service request with array bytes to flac format data
	 * 
	 * @param flacStream - output FLAC stream 
	 * @return String with response server
	 * @throws Exception
	 */
	public String sendFLACStream(OutputStream flacStream) throws Exception{
		
		byte[] flacByteArray = ((ByteArrayOutputStream)flacStream).toByteArray();
		connectToServerGoogle();
		
		OutputStream out = connection.getOutputStream();
		
		for (int i = 0; i < flacByteArray.length; i++) {
			out.write(flacByteArray[i]);
		}
		
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		
		out.close();
		flacStream.close();
		response = br.readLine();
		
		 br.close();
		
		return response;
	}
	
	/**
	 * Default connectinon.
	 * @throws IOException
	 */
	private void connectToServerGoogle() throws IOException{
		connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", "audio/x-flac; rate=8000");
		connection.setRequestProperty("Accept-Charset", "UTF-8");       		
		}
	
	/**
	 * Connection with specific params.
	 * @param contentType - Content Type
	 * @param charset - Accept Charset
	 * @throws IOException
	 */
	private void connectToServerGoogle(String contentType, String charset) throws IOException{
		connection = url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);
		connection.setRequestProperty("Content-Type", contentType);
		connection.setRequestProperty("Accept-Charset", charset);       		
		}
	
	/**
	 * Parse responce from server
	 * @param rawResponse
	 * @return Array string params responce from server 
	 */
	
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

