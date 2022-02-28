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
		List<Integer> command_separator_indexes = new ArrayList<>();
		
		command_separator_indexes = findCommandSeparators(command, command_separator_indexes);
		
		int command_count = command_separator_indexes.size();
		if (command_count == 0) {
			commandsList.add(command);
		}
		else if (command_count>0) {
			String first_command = command.substring(0, command_separator_indexes.get(0)-1);
			commandsList.add(first_command);
		}
		
		int i = 0;
		if (command_count>1) {
			for (i = 0; i < command_count-1; i++) {
				String temp_command;
				temp_command = command.substring(command_separator_indexes.get(i)+2, command_separator_indexes.get(i+1)-1);
				
				commandsList.add(temp_command);
			}
		}
		if (command_count>0) {
			String last_command = command.substring(command_separator_indexes.get(i)+2);
			commandsList.add(last_command);
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
			char[] temp_chars = new char[s_chars.length()+1];
			int index = 0;
			for (int i = 0; i < s_chars.length(); i++) {
	            temp_chars[i] = s_chars.charAt(i);
	            index++;
	        }
			temp_chars[index] = 65535;
			Arrays.sort(temp_chars);
			
			index = 0;
			for (int i = 0; i < temp_chars.length-1;   i++) {
				if (temp_chars[i] == temp_chars[i+1]) {}
				else {
					charList.add(String.valueOf(temp_chars[i]));
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
		
		System.out.println(uniqueStringsCount);
		
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
