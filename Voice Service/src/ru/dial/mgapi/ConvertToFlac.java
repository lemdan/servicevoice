package ru.dial.mgapi;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import javaFlacEncoder.FLACFileWriter;

import javax.activation.CommandInfo;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;

import ru.dial.vad.Complex;
import ru.dial.vad.Energy;
import ru.dial.vad.FFT;
import ru.dial.vad.SFM;

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
	public OutputStream getDataFromLine(TargetDataLine line){
		float energy = 0;
		float max = 0;
		boolean stop = true;
		//line.getBufferSize() / 5
		byte[] data = new byte[16];
		System.out.println(data[10]);
		int countSilence = 0;
		int count = 0;
		GaussianNoise gn = new GaussianNoise(700.0F, 700.0F);
		
		Complex comPrim = new Complex(0, 185);
		int ePrim = 40;
		double sfmPrim = 5;
		
		double minF = 0;
		int minE = 0;
		double minSF = 0;
		
		double ThreshF = comPrim.getIm();
		int ThreshE = ePrim; 
		double ThreshSF = sfmPrim;
		
		while( stop){
			
			System.out.println(line.read(data, 0, data.length));
			/*for(int i = 0; i < data.length ;i++){
				if(data[i] > max)
					max = Math.abs(data[i]);
				energy += (data[i]*data[i]);
			}*/
			
		//System.out.println(Calendar.getInstance().getTime() +"----------"+ energy/(line.getBufferSize() / 5) + "---------" + max);
		//energy = 0;
		//max =0;
			
			//WaveData wd = new WaveData();
			//float[] audioData = wd.extractFloatDataFromAmplitudeByteArray(line.getFormat(), data);
			/*for(int i = 0; i < audioData.length ;i++){
				if(data[i] > max)
					max = audioData[i];
				energy += (audioData[i]*audioData[i]);
			}
			System.out.println(Calendar.getInstance().getTime() +"----------"+ energy/(line.getBufferSize() / 5) + "---------" + max);
			energy = 0;
			max =0;*/
			
			//count++;
			
			//if ((count > 1) && (count < 3)){
			
			//gn.averageValue(data);
			//gn.normalDistribution(data);
			//}
			//if (count > 5)
			/*int a = gn.getA(data);
				System.out.println(a + "======== " + gn.getAverage() + "============ " + gn.getNormal());
				if (a == 0){
					countSilence++;
				}
				else countSilence = 0;
				if (countSilence == 11){
					System.out.println("STOP----------------------------------");
				}*/
			
			Complex[] complex = FFT.fft(data);
			Complex com = new Complex();
			for (Complex c: complex){
				if (Math.abs(c.getRe())>com.getRe())
					com = c;
			}
			int e = Energy.shortTimeEnergy(data);
			
			double sfm = SFM.spectralFlatnessMeasure(SFM.arithmetic(complex), SFM.geometry(complex));
			
			if (countSilence < 30){
				countSilence++;
				System.out.println("+++++++++++++++++++++++++" + sfm);
				if (minF > com.getIm())
					minF = com.getIm();
				if(minE > e)
					minE = e;
				if (minSF > sfm)
					minSF = sfm;
			}else{
				//if(com.getIm()>=ThreshF)
					//count++;
				//if(e >= ThreshE)
					//count++;
				if(sfm >= ThreshSF)
					count++;
			}
			
			System.out.println("=======================" + sfm);
			count = 0;
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
