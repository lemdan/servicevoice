package ru.dial.mgapi;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javaFlacEncoder.FLACFileWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import vad.ShortTermEnergy;
import vad.Thresholds;
import vad.ZeroCrossing;

/**
 * This class provides convert input data from microphone to FLAC format
 * 
 * @author Lemdan
 */
public class ConvertToFlac {
	
	public static boolean stop = true;
	/**
	 * This method get data from microphone and then convert them into output stream to FLAC format
	 * 
	 * @param line - This is microphone line {@link javax.sound.sampled.TargetDataLine}
	 * @return baos - This is ouput data {@link java.io.ByteArrayOutputStream} wrapper {@link java.io.OutputStream}
	 */
	public OutputStream getDataFromLine(TargetDataLine line){
		
		byte[] data = new byte[line.getBufferSize()/50];
		int countSilenece = 0;
		ZeroCrossing[] zcr = new ZeroCrossing[10];
		ShortTermEnergy[] steArray = new ShortTermEnergy[10];
		float[] a = new float[10];
		float[] n = new float[10];
		float srA = 0;
		float srN = 0;
		File f = new File("F:\\111.txt");
		FileWriter fw = null;
		WaveData wd = new WaveData();
		try {
			f.createNewFile();
			fw = new FileWriter(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while( stop){
			line.read(data, 0, data.length);
			
			ZeroCrossing zc = new ZeroCrossing(data);
			ShortTermEnergy ste = new ShortTermEnergy(data);
			if (countSilenece <= 9){
				zcr[countSilenece] = zc;
				//steArray[countSilenece] = ste;
				a[countSilenece] = GaussianNoise.averageValue(data);
				n[countSilenece] = GaussianNoise.normalDistribution(data, a[countSilenece]);
				countSilenece++;
				
			}
				
			if (countSilenece == 10){
				
				srA = Thresholds.getIZCR(ZeroCrossing.meanZCR(zcr), ZeroCrossing.standardZCR(zcr));
				countSilenece++;
				//System.out.println();
				//System.out.println();
				
				//Thresholds.getITL(ShortTermEnergy.maxSTE(steArray), ShortTermEnergy.meanSTE(steArray));
			}else{
				try {
					
					if(new ZeroCrossing(data).getZCR() < srA) fw.write("0" + "\n");
					else fw.write("1" + "\n");
					System.out.println(srA);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			//try {
				//float[] audioData = wd.extractFloatDataFromAmplitudeByteArray(line.getFormat(), data);
				//for(int i = 0; i < data.length; i++)
				//fw.write(Byte.valueOf(data[i]).toString()+ "\n");
			//} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			//}
			
			
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AudioInputStream ais = new AudioInputStream(line);
		ByteArrayOutputStream outputFlacStream = new ByteArrayOutputStream();
		try {
			System.out.println("Audio_start");
			FLACFileWriter ffw = new FLACFileWriter();
			File file = new File("F:\\111.flac");
			file.createNewFile();
			ffw.write(ais, FLACFileWriter.FLAC, file);
			//AudioSystem.write(ais, FLACFileWriter.FLAC, outputFlacStream);
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
