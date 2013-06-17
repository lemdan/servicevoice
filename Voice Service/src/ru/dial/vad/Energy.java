package ru.dial.vad;

public class Energy {
	
	
	public static  int shortTimeEnergy(byte[] audioDate){
		int energy = 0;
		for(byte data: audioDate)
			energy += Math.pow(data,2);
		return energy;
	}
}
