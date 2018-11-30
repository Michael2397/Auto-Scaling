package com.louis.util.qlearning;

public class ExperienceMatrix {
	    public float get(int x, int y)
	    {
	        return Q[x][y];
	    }
	    
	    public float[] getRow(int x)
	    {
	        return Q[x];
	    }
	    
	    public void set(int x, int y, float value)
	    {
	        Q[x][y] = value;
	    }
	    
	    public void print()
	    {
	        for(int i = 0; i < 3; i++)
	        {
	            for(int j = 0; j < 3; j++)
	            {
	                String s = Q[i][j] + "  ";
	                if(Q[i][j] < 10)
	                {
	                    s = s + "  ";
	                }
	                else if(Q[i][j] < 100)
	                {
	                    s = s + " ";
	                }
	                System.out.print(s);
	            }
	            System.out.println();
	        }
	    }
	    
	    private static float[][] Q = new float[3][3];
	    static
	    {
	        Q[0][0] = 0;
	        Q[0][1] = 0;
	        Q[0][2] = 0;
	
	 
	        
	        Q[1][0] = 0;
	        Q[1][1] = 0;
	        Q[1][2] = 0;
	     
	        
	        Q[2][0] = 0;
	        Q[2][1] = 0;
	        Q[2][2] = 0;
	      
	    }
	    
	 
}
