package com.mosz.edrrest;

public class CounterRefactored {
	int counter = 0;
	
	void resetCounter() {
		counter = 0;
	}
	
	public void setCount(int addme) {
		counter = counter + addme;
	}
	
	public int getCount() {
		return counter;
	}
	
	public void wait(int ms){
	    try {
	        Thread.sleep(ms);
	    } catch(InterruptedException ex) {
	        Thread.currentThread().interrupt();
	    }
	}
}
