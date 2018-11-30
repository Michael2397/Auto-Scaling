package com.louis.util.qlearning;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.louis.Markov.StateDependOnUtilization;



/**
 * 
 * QLearning.java Create on 2017年9月4日 下午10:08:49    
 *    
 * 类功能说明:   QLearning简明例子实现
 *
 * Copyright: Copyright(c) 2013 
 * Company: COSHAHO
 * @Version 1.0
 * @Author coshaho
 */
public class QLearning 
{
	private static float cpuUtilization[] = new float[]{0.15f,0.3f,0.8f,1.0f,1.0f,0.5f,0.15f,0.45f,0.95f,0.25f,0.3f,0.35f,0.45f,0.85f,0.5f,0.6f,0.4f,0.8f,1.0f,0.3f,0.45f,0.75f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.85f,0.45f,0.95f,0.55f,0.15f,0.75f,1.0f,1.0f,0.8f,1.0f,1.0f,0.6f,0.75f,0.8f,0.7f,0.7f,0.85f,1.0f,1.0f,0.65f,0.85f,0.95f,0.75f,0.95f,1.0f,1.0f,1.0f,1.0f,0.9f,0.9f,0.15f,0.6f,0.55f,0.8f,1.0f,0.8f,0.45f,0.75f,0.85f,0.5f,0.4f,0.75f,1.0f,1.0f,0.75f,0.65f,0.25f,0.8f,0.85f,0.95f,0.85f,0.7f,0.2f,0.3f,0.8f,0.25f,0.4f,0.5f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.85f,0.75f,0.75f,0.0f,0.45f,0.95f,1.0f,0.6f,0.45f,0.35f,0.85f,0.3f,1.0f,0.65f,0.0f,0.8f,0.55f,0.45f,0.6f,0.8f,0.4f,0.45f,0.6f,0.6f,0.6f,0.6f,0.45f,0.45f,0.55f,0.45f,1.0f,1.0f,0.7f,0.5f,0.85f,0.5f,0.75f,0.3f,0.6f,0.95f,0.6f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.9f,0.6f,0.9f,0.6f,1.0f,0.95f,1.0f,0.5f,0.9f,0.3f,0.55f,0.65f,0.3f,1.0f,0.35f,0.0f,0.6f,0.9f,0.95f,1.0f,0.45f,0.6f,0.45f,0.0f,0.0f,0.0f,0.15f,0.45f,1.0f,0.35f,0.6f,0.45f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.0f,0.15f,0.55f,0.65f,1.0f,1.0f,1.0f,1.0f,0.65f,0.6f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.75f,1.0f,0.85f,1.0f,0.85f,0.15f,0.45f,0.65f,0.55f,0.6f,0.6f,1.0f,0.65f,0.6f,0.85f,0.9f,0.85f,1.0f,1.0f,0.65f,0.5f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.7f,1.0f,0.8f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.95f,0.8f,0.4f,0.6f,0.15f,0.3f,0.75f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,1.0f,0.35f,0.45f,0.5f,1.0f,1.0f,1.0f,1.0f,1.0f,0.65f,0.8f,1.0f,0.55f,0.35f,0.45f,0.6f,0.5f,1.0f,0.85f,1.0f,0.85f,0.3f,0.7f,0.65f,0.15f,1.0f,0.35f,0.5f};
	
    FeedbackMatrix R = new FeedbackMatrix();
    
    ExperienceMatrix Q = new ExperienceMatrix();
    
    public static void main(String[] args)
    {
        QLearning ql = new QLearning();
        
        for(int i = 0; i < 300; i++) {
        	 int x = 0; 
        	 System.out.println("第" + i + "次学习, cpu利用率是" + cpuUtilization[i]);
        	 String state = StateDependOnUtilization.stateDependOnUtilization(cpuUtilization[i]);
        	 System.out.println("第" + i + "次学习, 初始化状态是" + state);
        	 if(state.equals("over")) {
        		 x=0;
        	 }else if(state.equals("normal")) {
        		 x=1;
        	 }else if(state.equals("under")){
        		 x=2;
        	 }
        	 ql.learn(x);
        	 System.out.println();
        	 
        }
        
    }
    
    public static void train() {
    	
    }
    
    
    public void learn(int x) {
    	int y = chooseRandom();   //下个状态是随机选择
    	System.out.println("选择的y："+y);
    	int qy = getMaxQY(y);  //获取下个状态历史最佳得分
    	System.out.println("选择的qy："+qy);
    	float value = calculateNewQ(x, y, qy);//计算此次移动的得分
    	System.out.println("value:"+value);
    	value = ((float)Math.round(value*1000)/1000);//保留三位有效数字
    	System.out.println("更新后的值"+"x:"+x+",y:"+y+",value:"+value);
    	Q.set(x, y, value);
    	Normalization(x);//更新好值后需要对这一行进行归一化处理，（0，1）之间
    	Q.print();
    }
    
    
    public int chooseRandom() {
	    int y = 0;
	    if(0<=Math.random()&&0.333>Math.random()) {
	    	y = 0;
	    } else if(0.333<=Math.random()&&0.666>Math.random()) {
	    	y = 1;
	    }else if(0.666<=Math.random()&&1>Math.random()) {
	    	y = 2;
	    }
	    return y;
	    
    }
    
    public int getMaxQY(int x)
    {
        float[] qRow = Q.getRow(x);
        float max = qRow[0];
        for (int i = 1; i < qRow.length; i++) {
			if(qRow[i]>max) {
				max = qRow[i];
			}
		}
        for (int i = 0; i < qRow.length; i++) {
			if(qRow[i]==max) {
				return i;
			}
		}
        return 0;
    }
    
    // Q(x,y) = R(x,y) + 0.8 * max(Q(y,i))
    public float calculateNewQ(int x, int y, int qy)
    {
        return (float) (R.get(x, y) + 0.9 * Q.get(y, qy));
    }
    /*
     * 归一化处理
     * x:针对第x行
     * */
    public void Normalization(int x) {
    	float[] qRow = Q.getRow(x);
    	float sum = 0 ;
    	for (int i = 0; i < qRow.length; i++) {
			sum = sum+qRow[i];
		}
    	float a = qRow[0];
    	float b = qRow[1];
    	float c = qRow[2];
    	
    	Q.set(x, 0, setDecimalPoint(a/sum));
    	Q.set(x, 1, setDecimalPoint(b/sum));
    	Q.set(x, 2, setDecimalPoint(c/sum));
    }
    /*
     * 设置小数的位数
     * */
    public float setDecimalPoint(float f) {
    	return ((float)Math.round(f*1000)/1000);//保留三位有效数字
    }
}
