package ru.dial.mgapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

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
	private static int SILENCE_FRAMES = 5;
	private static int BYTES_IN_AUDIO_DATA = 128;
	/**
	 * This method get data from microphone and then convert them into output stream to FLAC format
	 * 
	 * @param line - This is microphone line {@link javax.sound.sampled.TargetDataLine}
	 * @return baos - This is ouput data {@link java.io.ByteArrayOutputStream} wrapper {@link java.io.OutputStream}
	 */
	public OutputStream getDataFromLine(TargetDataLine line){
		float energy = 0;
		float max = 0;
		boolean stop = true;
		byte[] audioData = new byte[BYTES_IN_AUDIO_DATA];
		int countSilence = 0;
		float averageSilenceFrames[] = new float[SILENCE_FRAMES];
		float normalSilenceFrames[] = new float[SILENCE_FRAMES];
		float averengThresh = 0;
		float normalThresh = 0;
		while( stop){
			
			line.read(audioData, 0, audioData.length);
			
			
			
			if (countSilence < SILENCE_FRAMES)
			{
				
				averageSilenceFrames[countSilence] = GaussianNoise.averageValue(audioData);
				normalSilenceFrames[countSilence] = GaussianNoise.normalDistribution(audioData, averageSilenceFrames[countSilence]);
				countSilence++;
				if (countSilence == SILENCE_FRAMES){
					for(int i = 0; i <= SILENCE_FRAMES - 1; i++){
						averengThresh += averageSilenceFrames[i];
						normalThresh += normalSilenceFrames[i];
					}
					
				}
			}else{
			
			int a = GaussianNoise.getA(audioData, averengThresh/SILENCE_FRAMES, normalThresh/SILENCE_FRAMES);
				
					System.out.println("----------------------------------" + a);
			}
				
			
		}
		AudioInputStream ais = new AudioInputStream(line);
		ByteArrayOutputStream outputFlacStream = new ByteArrayOutputStream();
		try {
			System.out.println("Audio_start");
			AudioSystem.write(ais, FLACFileWriter.FLAC, outputFlacStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Audio_stop");
		
		return outputFlacStream;
	}

	/**
	 * Return link to output stream
	 */
	
	
	
}
