package ru.dial.mgapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

public class ConvertToFlac {

	public OutputStream getDataFromLine(TargetDataLine line){
		AudioInputStream ais = new AudioInputStream(line);
		OutputStream baos  = new ByteArrayOutputStream();
		try {
			System.out.println("Audio_start");
			AudioSystem.write(ais, FLACFileWriter.FLAC, baos);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Audio_stop: ");
		
		return baos;
	}
}
