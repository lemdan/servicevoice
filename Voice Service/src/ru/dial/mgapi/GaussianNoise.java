package ru.dial.mgapi;

public class GaussianNoise {
	
	//The average value of a random variable
	public static float averageValue(byte[] amplitudeSilence){
		float average = 0;
		for (float a: amplitudeSilence){
			average += a;
		}
		
		try{
			return average/amplitudeSilence.length;
			}catch(ArithmeticException e){
				throw new ArithmeticException("Not correct input array!");
			}
				
	}
	
	public static float normalDistribution(byte[] amplitudeSilence, float average){
		float normal = 0;
		for (float a: amplitudeSilence){
			normal += Math.pow((a - average), 2);
		}
		try{
			normal = (float) Math.sqrt(normal/amplitudeSilence.length);
			return normal; 
		}catch(ArithmeticException e){
			throw new ArithmeticException("Not correct input array!");
		}
	}
	
	private static boolean getDistance(byte x, float average, float normal){

		if (Math.abs(x - average)/normal > 3)
			return true;
		return false;
	}
	
	public static int getA(byte[] amplitude, float average, float normal){
		int countOne = 0;
		for (byte a: amplitude){
			if (getDistance(a, average, normal))
				countOne++;
		}
		return countOne;
	}


}
