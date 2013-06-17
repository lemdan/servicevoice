package ru.dial.mgapi;


public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AudioFromMicrophone a = new AudioFromMicrophone();
		a.captureFromMicrophone();
		try {
			Thread.currentThread().sleep(10*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("STOP");
		a.stopCapture();
		
		SendDataToGoogle s = new SendDataToGoogle();
		String[] str = s.parseResponse(s.sendFLACStream(a.getOS()));
		System.out.print(str[0] + "          " + str[1]);
	}

}
