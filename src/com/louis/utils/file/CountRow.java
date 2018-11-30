package com.louis.utils.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CountRow {
	public static ArrayList<Integer> getRow() throws IOException {
		String string = System.getProperty("user.dir");
		String fileName = string + "\\data\\source\\access";
		ArrayList<Integer> list = new ArrayList<>();
		try {
			File file = new File(fileName);
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 0;
			int day = 1;
			while ((tempString = reader.readLine()) != null) {
				if(!tempString.contains(day+"/Jul/")) {
					day++;
					//System.out.println("line:"+line);
					list.add(line);
					line = 0;
				}
				line++;
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return list;
	}
	public static void main(String[] args) throws IOException {
		ArrayList<Integer> list = getRow();
		System.out.println(list.size());
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
}
