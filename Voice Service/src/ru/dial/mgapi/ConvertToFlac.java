package ru.dial.mgapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

/**
 * This class provides convert input data from microphone to FLAC format
 * 
 * @author Lemdan
 */
public class ConvertToFlac {
	
	
	/**
	 * This method get data from microphone and then convert them into output stream to FLAC format
	 * 
	 * @param line - This is microphone line {@link javax.sound.sampled.TargetDataLine}
	 * @return baos - This is ouput data {@link java.io.ByteArrayOutputStream} wrapper {@link java.io.OutputStream}
	 */
	public static OutputStream getDataFromLine(TargetDataLine line){
		AudioInputStream ais = new AudioInputStream(line);
		OutputStream baos  = new ByteArrayOutputStream();
		try {
			System.out.println("Audio_start");
			AudioSystem.write(ais, FLACFileWriter.FLAC, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Audio_stop");
		
		return baos;
	}
}
