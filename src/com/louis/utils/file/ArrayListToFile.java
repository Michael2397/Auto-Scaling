package com.louis.utils.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ArrayListToFile {
	/*
	 * list：要保存的list
	 * pass:保存的文件名，如"d:/1.txt"
	 * */
	public static void arrayListToFile(ArrayList<Integer> list, String pass) {
		try {
			File f = new File(pass);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i)+" ");				
			}
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void arrayListToFile2(ArrayList<String> list, String pass) {
		try {
			File f = new File(pass);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i)+" ");				
			}
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static void arrayListToFile3(ArrayList<Float> list, String pass) {
		try {
			File f = new File(pass);
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			for (int i = 0; i < list.size(); i++) {
				bw.write(list.get(i)+" ");				
			}
			bw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
}
