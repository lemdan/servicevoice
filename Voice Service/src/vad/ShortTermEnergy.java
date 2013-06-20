package vad;

public class ShortTermEnergy {
	private float ste = 0;
	
	public ShortTermEnergy(){}
	public ShortTermEnergy(byte[] frame){
		shotTermEnergy(frame);
	}
	
	//Short-term energy per frame is STE
	public float shotTermEnergy(byte[] frame){
		for(byte f: frame)
			ste += Math.abs(f);
		return ste;
	}
	
	//Mean deviation of STE
		public static float meanSTE(ShortTermEnergy[] energy){
			float avgste = 0;
			for(ShortTermEnergy e: energy){
				avgste += e.getSTE(); 
			}
			
			return avgste/energy.length;
		}
		
		//Standard deviation of STE
		public static float maxSTE(ShortTermEnergy[] energy){
			float maxSTE = 0;
			for(ShortTermEnergy e: energy)
				if (maxSTE < e.getSTE())
					maxSTE = e.getSTE();
			return maxSTE;
		}
		
		public float getSTE() {
			return ste;
		}
	
}
