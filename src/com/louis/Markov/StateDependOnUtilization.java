package com.louis.Markov;

public class StateDependOnUtilization {
	public final static float upperThreshold = 0.7f;
	public final static float lowerThreshold = 0.3f;
	
	public static String stateDependOnUtilization(float utilization ) {
		if(utilization>upperThreshold) {
			return "over";
		}else if(utilization<lowerThreshold) {
			return "under";
		}else {
			return "normal";
		}
	}
}
