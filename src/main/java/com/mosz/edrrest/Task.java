package com.mosz.edrrest;

import javax.persistence.*;

@Entity
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private int stringsToCreateCount;
	
	@Column
	private int minLength;
	
	@Column
	private int maxLength;
	
	@Column
	private String charList;
	
	@Column
	private String path;
	
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getStringsToCreateCount() {
		return stringsToCreateCount;
	}
	public void setStringsToCreateCount(int stringsToCreateCount) {
		this.stringsToCreateCount = stringsToCreateCount;
	}
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMaxLength() {
		return maxLength;
	}
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	public String getCharList() {
		return charList;
	}
	public void setCharList(String charList) {
		this.charList = charList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
