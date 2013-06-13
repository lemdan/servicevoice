package ru.dial.mgapi;

import javax.sound.sampled.LineUnavailableException;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AudioFromMicrophone a = new AudioFromMicrophone();
		a.captureFromMicrophone();
		try {
			Thread.currentThread().sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("STOP");
		a.stopCapture();
		
		SendDataToGoogle s = new SendDataToGoogle();
		System.out.println(s.sendFLACStream(a.getOS()));
	}

}
