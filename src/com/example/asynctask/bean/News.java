package com.example.asynctask.bean;

import java.io.Serializable;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	private String picurl;
	private String title;
	private String content;

	// public
	// protected

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
