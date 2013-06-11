package ru.dial.mgapi;

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
	
	AudioFormat format;
	DataLine.Info info;
	
	public AudioFromMicrophone() {
		format = new AudioFormat(8000.0F, 16, 1, true, false);
		info = new DataLine.Info(TargetDataLine.class, format);
	}
	
	public AudioFromMicrophone(AudioFormat format){
		this.format = format;
		this.info = new DataLine.Info(TargetDataLine.class, format);
	}
	
	
	public AudioFromMicrophone(AudioFormat format, DataLine.Info info){
		this.format = format;
		this.info = info;
		
	}

	public  void captureFromMicrofon() throws LineUnavailableException{
		
	
	if (!AudioSystem.isLineSupported(info)) {
	    	throw new LineUnavailableException("Is Line not Supported: " + info);
	    }
	    
	try {
		TargetDataLine line = (TargetDataLine) AudioSystem.getLine(info);
	    line.open(format);
	} catch (LineUnavailableException ex) {
	        throw ex; 
	}
	}
}
