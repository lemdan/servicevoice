package vad;

public class Thresholds {
	
	//Zero crossing comparison rate for threshold
	private static int IF = 20;
	//3% of peak energy
	private static float PEAK_ENERGY = 0.03F;
	//4x silent energy
	private static float SILENT_ENERGY = 4;
	
	/**
	 * Get Lower Energy threshold
	 * @param imx - peak energy
	 * @param imn - mean silence energy
	 * @return Lower Energy threshold
	 */
	public static float getITL(float imx, float imn){
		float i1 = PEAK_ENERGY*(imx - imn) + imn;
		
		float i2 = SILENT_ENERGY*imn; 
		
		if (i1 < i2) return i1;
		return i2;
				
	}
	
	
	/**
	 * Choose zero-crossing threshold for unvoiced speech
	 * @param meanZCR - mean zero-crossing rate
	 * @param normalZCR - normal zero-crossing rate
	 * @return Zero-crossing threshold
	 */
	public static float getIZCR(float meanZCR, float normalZCR){
		float std = meanZCR + 2*normalZCR; 
		if (IF < std)
			return IF;
		return std;
			
	}
}
