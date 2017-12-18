package com.vaadin.starter.skeleton;

public enum ViewFilter {

	ALL(""), COMPLETED("completed"), ACTIVE("active");

	private String path;
	private ViewFilter(String path){
		this.path = path;
	}

	public static ViewFilter findByPath(String path){
		if(path==null || path.isEmpty()){
			return ALL;
		}
		for(ViewFilter viewFilter : ViewFilter.values()){
			if(path.equals(viewFilter.path)){
				return viewFilter;
			}
		}
		return ALL;
	}
}
