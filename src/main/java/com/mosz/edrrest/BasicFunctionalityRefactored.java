package com.mosz.edrrest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BasicFunctionalityRefactored {

	CounterRefactored counterStringsCreated = new CounterRefactored();
	CounterRefactored counterCheckIfRnd = new CounterRefactored();
	
	public void doTask(int stringsToCreateCount, int minLength, int maxLength, List<String> charList, long uniqueStringsCount, int taskNumber) {
		List<String> taskOutputStringsList = new ArrayList<>();
		
		List<Long> newRndList = getRndValList(0, uniqueStringsCount-1, stringsToCreateCount);
		
		String constructEmpty = "";
		
		for (int i = minLength; i <= maxLength; i++) {
			constructStringsRecursive(charList, constructEmpty, charList.size(), i, stringsToCreateCount, newRndList, taskOutputStringsList);
		}
		
		String path = System.getProperty("user.home") + "/Downloads/HM_Edrone_2022/";
		String fileName = getFileName(taskNumber);
		
		writeToJson(fileName, path, taskOutputStringsList);
		writeToTxt(fileName, path, taskOutputStringsList);
		
		counterCheckIfRnd.resetCounter();
		counterStringsCreated.resetCounter();
		
		counterStringsCreated.wait(10);
	}
	
	
	public void constructStringsRecursive(List<String> uniqueSet, String construct, int n, int k, int stringsNeededCount, List<Long> rndValList, List<String> taskOutput) {
		// n - Unique chars count
		// k - string length
		// n ^ k
		int createdStringsCounter = counterStringsCreated.getCount();
		long checkIfRndCounter = counterCheckIfRnd.getCount();
		
		if (k == 0) {
			if (rndValList.contains(checkIfRndCounter)) {
				taskOutput.add(construct);
				counterStringsCreated.setCount(1);
			}
			counterCheckIfRnd.setCount(1);
			return;
		}
		
		for (int i = 0; i < n; ++i) {
			String newConstruct = construct + uniqueSet.get(i);
			
			if (createdStringsCounter >= stringsNeededCount) return;
			
			constructStringsRecursive(uniqueSet, newConstruct, n, k - 1, stringsNeededCount, rndValList, taskOutput);
		}
	}
	
	public long getRandomNumber(int minValue, long maxValue) {
		SecureRandom secureRandom = new SecureRandom(); 
		
		long value = minValue + secureRandom.nextLong((maxValue - minValue) + 1);
		
		return value;
	}
	
	public List<Long> getRndValList(int minValue, long maxValue, int listSize) {
		List<Long> rndValList = new ArrayList<Long>();
		
		int i = 0;
		while (i < listSize) {
			long temp = getRandomNumber(minValue, maxValue);
			if (!rndValList.contains(temp)) {
				rndValList.add(temp);
				i++;
			}
		}
		
		return rndValList;
	}
	
	public String getFileName(int taskNumber) {
		String path = System.getProperty("user.home") + "/Downloads/HM_Edrone_2022/";
		String taskNr = " - Task " + taskNumber;
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss.sss");
		String date = sdf.format(new Date()).replaceAll("-", "_").replaceAll(":", "_");
		
		String fileName = path + date + taskNr;
		return fileName;
	}
	
	public void writeToTxt(String fileName, String path, List<String> outputStrings) {
		try {
			File directory = new File(path);
			directory.mkdirs();
			
		    try (FileWriter fileWriter = new FileWriter(fileName+".txt")) {
				int i;		    
				for (i = 0; i < outputStrings.size()-1; i++) {
					fileWriter.write(outputStrings.get(i) + "\n");
				}
				fileWriter.write(outputStrings.get(i));
				
				fileWriter.close();
			}
		} catch (IOException e) {
		      System.out.println("An IO error occurred.");
		      e.printStackTrace();
		}
	}
	
	public void writeToJson(String fileName, String path, List<String> outputStrings) {
		try {
			File directory = new File(path);
			directory.mkdirs();
			
		    try (FileWriter fileWriter = new FileWriter(fileName+".json")) {
				//create json layout
		    	fileWriter.write("{\n");
				fileWriter.write("    " + "\"fileName\": \"" + fileName + ".json" + "\",\n");
				fileWriter.write("    " + "\"itemCount\": " + outputStrings.size() + ",\n");
				fileWriter.write("    " + "\"itemList\": " + "[\n");
				
				int i;		    
				for (i = 0; i < outputStrings.size()-1; i++) {
					fileWriter.write("    " + "    " + outputStrings.get(i) + ",\n");
				}
				
				fileWriter.write("    " + "    " + outputStrings.get(i) + "\n");
				fileWriter.write("    " + "],\n");
				fileWriter.write("    " + "\"readMe\": " + "\"Created additional JSON file for easier usability in future programs. 2022\"" + ",\n");
				fileWriter.write("    " + "\"createdBy\": " + "\"Hubert Mosz\"" + "\n");
				fileWriter.write("}");
				
				fileWriter.close();
			}
		} catch (IOException e) {
		      System.out.println("An IO error occurred.");
		      e.printStackTrace();
		}
	}
}
