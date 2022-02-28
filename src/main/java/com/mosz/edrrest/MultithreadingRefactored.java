 package com.mosz.edrrest;

import java.util.ArrayList;
import java.util.List;

public class MultithreadingRefactored implements Runnable {

	ParametersRefactored parameters = new ParametersRefactored();
	BasicFunctionalityRefactored basicFunctionality = new BasicFunctionalityRefactored();
	List<Object> parametersValues = new ArrayList<>();
	String command;
	int taskNumber;
	
    public MultithreadingRefactored(String command, int taskNumber) {
        this.command = command;
    	this.taskNumber = taskNumber;
    }
    
	@Override
	public void run() {
        try {
        	doTaskInThread(parameters, basicFunctionality, parametersValues, command, taskNumber);
        } catch (Exception err) {
            err.printStackTrace();
        }		
	}
	
	@SuppressWarnings("unchecked")
	public void doTaskInThread(ParametersRefactored parameters, BasicFunctionalityRefactored basicFunctionality, List<Object> parametersValues, String command, int taskNumber) {
            
        parametersValues = parameters.handleCommand(command);
		if (parametersValues == null) return;
			
		basicFunctionality.doTask((int) parametersValues.get(0), 
						(int) parametersValues.get(1), 
						(int) parametersValues.get(2), 
						(List<String>) parametersValues.get(3), //suppress
						(long) parametersValues.get(4), 
						taskNumber);
			
		if (parametersValues.contains("exit")) return;
    }
}
