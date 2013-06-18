package ru.dial.mgapi;

public class GaussianNoise {
	
	//The average value of a random variable
	public  static float averageValue(byte[] audioData){
		float average = 0.0F;
		for (float a: audioData){
			average += a;
		}
		
		try{
			return average/audioData.length;
			}catch(ArithmeticException e){
				throw new ArithmeticException("Not correct input array!");
			}
				
	}
	
	public static float normalDistribution(byte[] audioData, float average){
		float normal = 1.0F;
		for (float a: audioData){
			normal += Math.pow((a - average), 2);
		}
		try{
			normal = (float) Math.sqrt(normal/audioData.length);
			return normal; 
		}catch(ArithmeticException e){
			throw new ArithmeticException("Not correct input array!");
		}
	}
	
	private static boolean getDistance(float x, float average, float normal){

		if (Math.abs(x - average)/normal > 3)
			return true;
		return false;
	}
	
	public static int getA(byte[] audioData,float average, float normal){
		int countOne = 0;
		for (float a: audioData){
			if (getDistance(a, average, normal))
				countOne++;
		}
		
		return countOne;
	}

}
