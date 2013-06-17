package ru.dial.mgapi;

public class GaussianNoise {
	private float average;
	private float normal;
	
	public GaussianNoise(){
		this.average = 0.0F;
		this.normal = 1.0F;
	}
	
	public GaussianNoise(float average, float normal){
		this.average = average;
		this.normal = normal;
	}
	
	//The average value of a random variable
	public  float averageValue(float[] amplitudeSilence){
		average = 0;
		for (float a: amplitudeSilence){
			average += a;
		}
		
		try{
			return average/amplitudeSilence.length;
			}catch(ArithmeticException e){
				throw new ArithmeticException("Not correct input array!");
			}
				
	}
	
	public float normalDistribution(float[] amplitudeSilence){
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
	
	private boolean getDistance(float x){

		if (Math.abs(x - average)/normal > 3)
			return true;
		return false;
	}
	
	public int getA(float[] amplitude){
		int countOne = 0;
		for (float a: amplitude){
			if (getDistance(a))
				countOne++;
		}
		return countOne;
	}

	public float getAverage() {
		return average;
	}

	public float getNormal() {
		return normal;
	}

}
