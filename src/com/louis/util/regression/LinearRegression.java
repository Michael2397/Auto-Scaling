package com.louis.util.regression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.poi.ss.formula.functions.Count;

public class LinearRegression {  
  
  
    /** 
     * Main program. 
     *  
     * @param args 
     *            the array of runtime arguments 
     */  
    public static void main(String args[]) {  
    	String dataSource="792 819 627 684 705 861 513 627 774 672 528 471 540 465 474 684 576 828 861 807 846 645 693 498 507 477 609 405 660 465 552 753 396 549 630 564 375 501 465 390 408 402 240 546 537 336 339 231 279 408 384 423 351 363 342 411 441 210 195 276 180 243 228 282 252 408 288 303 189 237 114 153 282 180 393 348 258 312 345 306 288 318 285 189 234 342 318 324 504 306 714 612 711 639 906 1350 1035 1128 891 1221 1047 1071 1104 1344 1620 1347 1545 1176 1371 1206 1407 1761 1272 1434 1032 1479 1341 1461 1062 1392 1617 1623 1503 1593 1731 1692 1854 1575 252 1638 1440 1314 1455 1527 1659 1611 1572 1473 1686 1569 1827 1869 1692 1668 1602 1476 1386 2004 1551 1806 1710 1662 1668 1905 1770 1812 852 1950 1677 1734 1386 1611 1350 1479 1353 1425 1311 1758 1476 759 717 1329 1776 1188 1176 891 1401 1239 1059 1131 1437 1251 1578 1413 1380 1464 1518 1434 1308 1515 1542 1416 1569 1614 1647 1374 1278 1386 1209 1107 1389 1260 1182 1332 1233 1455 1113 1329 909 1251 1485 1305 1209 1536 1212 960 843 840 1110 1023 987 1062 867 756 645 648 756 462 579 696 675 699 645 561 555 417 558 678 690 909 819 615 603 639 510 666 777 708 615 714 765 609 324 504 747 507 528 627 624 858 462 786 678 705 591 681 654 570 669 693 687 759 411 567 657 450 381 423 567 429 678 696 594 687 798 729 723 636";
        String strArray[] = dataSource.split(" ");
      
        int intArray[] = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
			intArray[i] = Integer.parseInt(strArray[i]);
		}
        
        //保存到文件中，使用python预测
//        ArrayList<String> list = new ArrayList<>();
//        for (int i = 0; i < intArray.length; i++) {
//			String temp = i+","+intArray[i]+"\n";
//			list.add(temp);
//		}
        
        int resultArray[] = new int[intArray.length];
        for (int i = 0; i < 5; i++) {
			resultArray[i] = (int)intArray[i];
		}
      
        
        for (int i = 1; i <= intArray.length; i++) {
        	if(i>=284) {
        		break;
        	}
        	RegressionLine line = new RegressionLine();
        	line.addDataPoint(new DataPoint(i, intArray[i-1])); 
        	line.addDataPoint(new DataPoint(i+1, intArray[i])); 
        	line.addDataPoint(new DataPoint(i+2, intArray[i+1])); 
        	line.addDataPoint(new DataPoint(i+3, intArray[i+2])); 
        	line.addDataPoint(new DataPoint(i+4, intArray[i+3])); 
        	int time = i+5;
            float resultPredict = printLine(line,time);
            resultArray[i+4] = (int)resultPredict;
		}
    	int count = 0;
        for (int i = 0; i < resultArray.length; i++) {
        	if(resultArray[i]>0) {
        		count++;
        	}
			System.out.print(resultArray[i]+" ");
		}
       System.out.println("\n");
    }  
    
    public static int[] getLinearPredict(){
    	String dataSource="792 819 627 684 705 861 513 627 774 672 528 471 540 465 474 684 576 828 861 807 846 645 693 498 507 477 609 405 660 465 552 753 396 549 630 564 375 501 465 390 408 402 240 546 537 336 339 231 279 408 384 423 351 363 342 411 441 210 195 276 180 243 228 282 252 408 288 303 189 237 114 153 282 180 393 348 258 312 345 306 288 318 285 189 234 342 318 324 504 306 714 612 711 639 906 1350 1035 1128 891 1221 1047 1071 1104 1344 1620 1347 1545 1176 1371 1206 1407 1761 1272 1434 1032 1479 1341 1461 1062 1392 1617 1623 1503 1593 1731 1692 1854 1575 252 1638 1440 1314 1455 1527 1659 1611 1572 1473 1686 1569 1827 1869 1692 1668 1602 1476 1386 2004 1551 1806 1710 1662 1668 1905 1770 1812 852 1950 1677 1734 1386 1611 1350 1479 1353 1425 1311 1758 1476 759 717 1329 1776 1188 1176 891 1401 1239 1059 1131 1437 1251 1578 1413 1380 1464 1518 1434 1308 1515 1542 1416 1569 1614 1647 1374 1278 1386 1209 1107 1389 1260 1182 1332 1233 1455 1113 1329 909 1251 1485 1305 1209 1536 1212 960 843 840 1110 1023 987 1062 867 756 645 648 756 462 579 696 675 699 645 561 555 417 558 678 690 909 819 615 603 639 510 666 777 708 615 714 765 609 324 504 747 507 528 627 624 858 462 786 678 705 591 681 654 570 669 693 687 759 411 567 657 450 381 423 567 429 678 696 594 687 798 729 723 636";
        String strArray[] = dataSource.split(" ");
      
        int intArray[] = new int[strArray.length];
        for (int i = 0; i < strArray.length; i++) {
			intArray[i] = Integer.parseInt(strArray[i]);
		}
        int resultArray[] = new int[intArray.length];
        for (int i = 0; i < 5; i++) {
			resultArray[i] = (int)intArray[i];
		}
      
        
        for (int i = 1; i <= intArray.length; i++) {
        	if(i>=284) {
        		break;
        	}
        	RegressionLine line = new RegressionLine();
        	line.addDataPoint(new DataPoint(i, intArray[i-1])); 
        	line.addDataPoint(new DataPoint(i+1, intArray[i])); 
        	line.addDataPoint(new DataPoint(i+2, intArray[i+1])); 
        	line.addDataPoint(new DataPoint(i+3, intArray[i+2])); 
        	line.addDataPoint(new DataPoint(i+4, intArray[i+3])); 
        	int time = i+5;
            float resultPredict = printLine(line,time);
            resultArray[i+4] = (int)resultPredict;
		}
        return resultArray;
    }
    /*
     * 得到下一阶段的预测值
     * */
    public static float getPredictedValue(HashMap<Integer, Integer> hashMap,int nextKey) {
    	RegressionLine line = new RegressionLine();  
    	for(Entry<Integer, Integer> entry : hashMap.entrySet()) {
    		line.addDataPoint(new DataPoint(entry.getKey(), entry.getValue()));  
    	}
    	return printLine(line, nextKey);
    	
    }
    
    
    public static  float printLine(RegressionLine line,int nextKey) {  
//        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "  
//                + line.getA0());  
        return line.getA1()*nextKey+line.getA0();
    }  
  
 
    
    public static void printSums(RegressionLine line) {  
        System.out.println("\n数据点个数 n = " + line.getDataPointCount());  
        System.out.println("\nSum x  = " + line.getSumX());  
        System.out.println("Sum y  = " + line.getSumY());  
        System.out.println("Sum xx = " + line.getSumXX());  
        System.out.println("Sum xy = " + line.getSumXY());  
        System.out.println("Sum yy = " + line.getSumYY());  
  
    }  
  
   
    public static void printLine(RegressionLine line) {  
        System.out.println("\n回归线公式:  y = " + line.getA1() + "x + "  
                + line.getA0());  
        System.out.println("误差：     R^2 = " + line.getR());  
    }  
      
    //y = 2.1x + 133.7   2.1 * 6 + 133.7 = 12.6 + 133.7 = 146.3  
    //y = 2.1x + 133.7   2.1 * 7 + 133.7 = 14.7 + 133.7 = 148.4  
  
}  