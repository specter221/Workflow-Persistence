package com.distributedpipeline.persistence.domain;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;

public class Tasks {

	@NotNull
	private String type;
	private String status;
	private String[] depends_on;
	private String[] output;
	private String[] input;
	
	
	/*------------------------ Getters and Setters for the fields-----------------------------*/
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}	
	
	public String[] getDepends_on() {
		return depends_on;
	}

	public void setDepends_on(String[] depends_on) {
		this.depends_on = depends_on;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public String[] getOutput() {
		return output;
	}
	public void setOutput(String[] output) {
		this.output = output;
	}
	public String[] getInput() {
		return input;
	}
	public void setInput(String[] input) {
		this.input = input;
	}
	
    /*------------------------------- Constructors -----------------------------------------*/
	public Tasks() {
		super();
	}

	public Tasks(String type, String status, String[] depends_on, String[] output, String[] input) {
		super();
		this.type = type;
		this.status = status;
		this.depends_on = depends_on;
		this.output = output;
		this.input = input;
	}
}
