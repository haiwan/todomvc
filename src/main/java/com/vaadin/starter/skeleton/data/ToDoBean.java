package com.vaadin.starter.skeleton.data;

import java.io.Serializable;
import java.util.UUID;

public class ToDoBean implements Serializable{
	private String uuid;
	private boolean completed;
	private String text;

	public ToDoBean(){
		uuid = UUID.randomUUID().toString();
	}
	public ToDoBean(boolean completed, String text) {
		uuid = UUID.randomUUID().toString();
		this.completed = completed;
		this.text = text;
	}

	public ToDoBean(String text) {
		this(false, text);
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) {
			return true;
		}
		if(o == null || getClass() != o.getClass()) {
			return false;
		}

		ToDoBean toDoBean = (ToDoBean)o;

		return uuid != null ? uuid.equals(toDoBean.uuid) : toDoBean.uuid == null;
	}

	@Override
	public int hashCode() {
		return uuid != null ? uuid.hashCode() : 0;
	}

	public void toggleComplete() {
		setCompleted(!isCompleted());
	}
}
