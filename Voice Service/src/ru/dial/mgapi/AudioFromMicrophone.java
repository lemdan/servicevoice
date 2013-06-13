package ru.dial.mgapi;

import java.io.OutputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

/**
 * This class provides basic audio stream read from microphone.
 * 
 * @author Lemdan
 */

public class AudioFromMicrophone {
	
	private AudioFormat format = null;
	private DataLine.Info info = null;
	private TargetDataLine line = null;
	private OutputStream os;
	
	/**
	   * Constructor. Create a default AudioFromMicrophone using AudioFormat
	   * with parameters:<br>
	   * sampleRate - 8000.0F <br>
	   * sampleSizeInBits - 16 <br>
	   * channels - 1 <br>
	   * signed - true <br>
	   * bigEndia - false <br>
	   * And create TargetDataLine using this audio format 
	   *
	   */
	
	public AudioFromMicrophone() {
		format = new AudioFormat(8000.0F, 16, 1, true, false);
		info = new DataLine.Info(TargetDataLine.class, format);
	}
	
	/**
	   * Constructor. Create a AudioFromMicrophone using the given audio format.
	   *
	   * @param format - audio format {@link javax.sound.sampled.AudioFormat}}
	   */
	public AudioFromMicrophone(AudioFormat format){
		this.format = format;
		this.info = new DataLine.Info(TargetDataLine.class, format);
	}
	
	/**
	   * Constructor. Create a AudioFromMicrophone using the given audio format and line.
	   *
	   * @param format - audio format {@link javax.sound.sampled.AudioFormat} <br> {@link javax.sound.sampled.TargetDataLine}
	   */
	public AudioFromMicrophone(AudioFormat format, DataLine.Info info){
		this.format = format;
		this.info = info;
		
	}
	
	/**
	 * This method capture audio data from microphone
	 * @throws LineUnavailableException
	 */
	public  void captureFromMicrophone() throws LineUnavailableException{
		
	
	if (!AudioSystem.isLineSupported(info)) {
	    	throw new LineUnavailableException("Is Line not Supported: " + info);
	    }
	    
	try {
		line = (TargetDataLine) AudioSystem.getLine(info);
	    line.open(format);
	} catch (LineUnavailableException ex) {
	        throw ex; 
	}
	
		Thread t = new Thread (new CaptureThread());
		t.start();
	}
	
	public void stopCapture(){
		line.stop();
		line.close();
		
	}
	
	public OutputStream getOS() {
		return os;
	}
	
	//Thread for audio capture
	private class CaptureThread implements Runnable{

		@Override
		public void run() {
			// Begin audio capture.
			line.start();
			//Convert data to FLAC format
			ConvertToFlac flac = new ConvertToFlac();
			os = flac.getDataFromLine(line);
			
		}
		
	}
}
