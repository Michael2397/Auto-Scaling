package com.louis.util.qlearning;

public class FeedbackMatrix {
	   public float get(int x, int y)
	    {
	        return R[x][y];
	    }
	    
	    public float[] getRow(int x)
	    {
	        return R[x]; 
	    }
	    
	    private static float[][] R = new float[3][3];
	    static 
	    {
	        R[0][0] = 0.7f;
	        R[0][1] = 0.2f;
	        R[0][2] = 0.1f;
	    
	        
	        R[1][0] = 0.1f;
	        R[1][1] = 0.8f;
	        R[1][2] = 0.1f;
	     
	        
	        R[2][0] = 0.1f;
	        R[2][1] = 0.2f;
	        R[2][2] = 0.7f;
	       
	    }
	    
	    public void print()
	    {
	        for(int i = 0; i < 3; i++)
	        {
	            for(int j = 0; j < 3; j++)
	            {
	                String s = R[i][j] + "  ";
	                if(R[i][j] < 10)
	                {
	                    s = s + "  ";
	                }
	                else if(R[i][j] < 100)
	                {
	                    s = s + " ";
	                }
	                System.out.print(s);
	            }
	            System.out.println();
	        }
	    }
	    
	    public static void main(String[] args) {
	    	FeedbackMatrix experienceMatrix =  new FeedbackMatrix();
	 	    experienceMatrix.print();
	 	}
}
