package com.vaadin.starter.skeleton.data;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ToDoService {
	private static List<ToDoBean> toDoList = Arrays.asList(new ToDoBean(true, UUID.randomUUID().toString()), new ToDoBean(UUID.randomUUID().toString()),
			new ToDoBean(UUID.randomUUID().toString()));

	public static List<ToDoBean> getToDoList(){
		return toDoList;
	}
}
