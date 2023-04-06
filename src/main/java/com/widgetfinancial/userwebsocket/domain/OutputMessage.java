package com.widgetfinancial.userwebsocket.domain;

public class OutputMessage extends Message{
	private String time;

	public OutputMessage(String from, String to, String time) {
		setFrom(from);		
		setTo(to);
		setTime(time);
	}
	
	public String getTime() {
        return time;
    }
	
    public void setTime(String time) {
    	this.time = time; 
    }

}
