package ru.dial.vad;

public class SFM {
	private static double[] powerSpectrum(Complex[] garmonic){
		double[] power = new double[garmonic.length];
		for(int i = 0; i < garmonic.length; i++)
			power[i] = Math.pow(garmonic[i].getRe(), 2) + Math.pow(garmonic[i].getIm(), 2);
		return power;
	}
	
	public static double arithmetic(Complex[] garmonic){
		double[] power = powerSpectrum(garmonic);
		double a = 0;
		for(double p: power)
			a += p;
		return a/garmonic.length;
	}
	
	public static double geometry(Complex[] garmonic){
		double[] power = powerSpectrum(garmonic);
		double a = 1;
		for(double p: power)
			a *= p;
		System.out.println(a);
		return a;
	}
	
	public static double spectralFlatnessMeasure(double a, double g){
		
		return 10*Math.log10(g/a);
	}
}
