package com.mosz.edrrest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParametersRefactored {

	int stringsToCreateCount;
	int minLength;
	int maxLength;
	List<String> charList;
	long uniqueStringsCount;
	
	public List<String> setTaskParametersFromCommand(String command, List<String> commandsList) {
		commandsList = extractCommandsList(command, commandsList);
		
		return commandsList;
	}
	
	public List<String> extractCommandsList(String command, List<String> commandsList) { 		
		List<Integer> commandSeparatorIndexes = new ArrayList<>();
		
		commandSeparatorIndexes = findCommandSeparators(command, commandSeparatorIndexes);
		
		int commandCount = commandSeparatorIndexes.size();
		if (commandCount == 0) {
			commandsList.add(command);
		}
		else if (commandCount>0) {
			String firstCommand = command.substring(0, commandSeparatorIndexes.get(0)-1);
			commandsList.add(firstCommand);
		}
		
		int i = 0;
		if (commandCount>1) {
			for (i = 0; i < commandCount-1; i++) {
				String tempCommand;
				tempCommand = command.substring(commandSeparatorIndexes.get(i)+2, commandSeparatorIndexes.get(i+1)-1);
				
				commandsList.add(tempCommand);
			}
		}
		if (commandCount>0) {
			String lastCommand = command.substring(commandSeparatorIndexes.get(i)+2);
			commandsList.add(lastCommand);
		}

		return commandsList;
	}
	
	public List<Integer> findCommandSeparators(String command, List<Integer> separatorIndexes) {
		char separator = '.';
		int index = command.indexOf(separator);
		while (index >= 0) {
			separatorIndexes.add(index);
		    index = command.indexOf(separator, index + 1);
		}
		return separatorIndexes;
	}
	
	public List<Object> handleCommand(String command) {
		charList = new ArrayList<>();

		String s_stringcount = findValue(command, "amount-");
		String s_minlength = findValue(command, "min-");
		String s_maxlength = findValue(command, "max-");
		String s_chars = findValue(command, "chars-");
		
		try {
			stringsToCreateCount = Integer.parseInt(s_stringcount);
            if (stringsToCreateCount < 0) {
            	System.out.println("ERR: Value < 0 exception.");
            	return null;
            }
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
		
		try {
			minLength = Integer.parseInt(s_minlength);
			if (minLength < 0) {
				System.out.println("ERR: Value < 0 exception.");
				return null;
			}
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
		
		try {
			maxLength = Integer.parseInt(s_maxlength);
			if (maxLength < 0) {
				System.out.println("ERR: Value < 0 exception.");
				return null;
			}
			if (maxLength < minLength) {
				System.out.println("ERROR!: Max needs to be greater or equal to min value.");
				return null;
			}
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
            return null;
        }
		
		try {
			char[] tempChars = new char[s_chars.length()+1];
			int index = 0;
			for (int i = 0; i < s_chars.length(); i++) {
				tempChars[i] = s_chars.charAt(i);
	            index++;
	        }
			tempChars[index] = 65535;
			Arrays.sort(tempChars);
			
			index = 0;
			for (int i = 0; i < tempChars.length-1;   i++) {
				if (tempChars[i] == tempChars[i+1]) {}
				else {
					charList.add(String.valueOf(tempChars[i]));
					index++;
				}
			}
        } catch (NumberFormatException ex) {  
            ex.printStackTrace();
            return null;  
        }
		
		//Unique strings = n^k
		uniqueStringsCount = 0;
		for(int i = minLength; i <= maxLength; i++) {
			long result = (long) Math.pow(charList.size(), i);
			uniqueStringsCount = uniqueStringsCount + result;
			result = 0;
		}
		
		//System.out.println(uniqueStringsCount);
		
		if (stringsToCreateCount > uniqueStringsCount) {
			System.out.println("ERROR!: Demanded task exceeded the number of unique possibilities.");
			return null;
		}
		
		return Arrays.asList(stringsToCreateCount, minLength, maxLength, charList, uniqueStringsCount);		
	}
	
	public String findValue(String stringToProcess, String stringToFind) {
		String value;
		char separator = '_';
		
		if (stringToProcess.contains(stringToFind) == false) {
			System.out.println("Wrong command. Failed to find " + stringToFind);
			value = "exit";
			return value;
		}
		
		int index = stringToProcess.lastIndexOf(stringToFind) + stringToFind.length();
		String nextSubstring = stringToProcess.substring(index);
		int checkIndex = nextSubstring.indexOf(separator);
		int separatorIndex;
		
		if (checkIndex == -1) {
			stringToProcess = stringToProcess + "_";
			nextSubstring = stringToProcess.substring(index);
			separatorIndex = nextSubstring.indexOf(separator) + index;
		}
		else {
			separatorIndex = nextSubstring.indexOf(separator) + index;
		}
		
		value = stringToProcess.substring(index, separatorIndex);
		
		return value;
	}
}
