package vad;

public class ZeroCrossing {
	private float zcr = 0;
	
	public ZeroCrossing(){}
	
	public ZeroCrossing(byte[] frameData){
		zeroCrossingCount(frameData);
	}
	
	private int sign(float value){
		if (value < 0)
			return -1;
		return 1;
	}
	
	
	//Zero crossing count per frame is ZCR
	public float zeroCrossingCount(byte[] frameData){
		zcr = 0; 
		for(int i = 1; i < frameData.length; i++){
			zcr += Math.abs(sign(frameData[i]) - sign(frameData[i-1]))/2;
		}
		return zcr;
	}
	
	//Mean deviation of ZCR
	public static float meanZCR(ZeroCrossing[] zc){
		float avgzcr = 0;
		for(ZeroCrossing z: zc){
			avgzcr += z.getZCR(); 
		}
		
		return avgzcr/zc.length;
	}
	
	
	//Standard deviation of ZCR
	public static float standardZCR(ZeroCrossing[] zc){
		float stdzcr = 0;
		float std =0;
		for(ZeroCrossing z: zc)
			std += z.getZCR();
		std = std/zc.length;
		
		for(int i = 0; i < zc.length; i++){
			stdzcr += Math.pow(zc[i].getZCR() - std, 2);
		}
		
		return (float) Math.sqrt(stdzcr/zc.length);
	}
	
	
	public float getZCR() {
		return zcr;
	}
}
