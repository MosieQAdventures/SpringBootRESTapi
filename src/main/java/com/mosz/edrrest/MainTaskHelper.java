package com.mosz.edrrest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainTaskHelper {
	ParametersRefactored parameters = new ParametersRefactored();
	BasicFunctionalityRefactored basicFunctionality = new BasicFunctionalityRefactored();
	
	public List<String> getCommandList(String command) {
		List<String> list = new ArrayList<>();
		list = parameters.setTaskParametersFromCommand(command, list);
		return list;
	}
	
	public void executeTask(String command, int taskNumber) {
		ExecutorService executor= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		try {
			executor.execute(new MultithreadingRefactored(command, taskNumber));
		} catch (Exception err) { err.printStackTrace(); }
		
		executor.shutdown();
	}
	
	public Task setTaskParameters(Task task, String command, int i) {
		List<Object> parametersList = parameters.handleCommand(command);
		
		@SuppressWarnings("unchecked")
		List<String> tempCharList = (List<String>) parametersList.get(3);
		String charList = "";
		for (String item : tempCharList) { charList = charList + item; }
		
		task.setStringsToCreateCount((int) parametersList.get(0));
		task.setMinLength((int) parametersList.get(1));
		task.setMaxLength((int) parametersList.get(2));
		task.setCharList(charList);
		task.setPath(basicFunctionality.getFileName(i));
		
		return task;
	}
	
	
}
