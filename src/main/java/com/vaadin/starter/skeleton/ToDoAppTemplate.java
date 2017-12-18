package com.vaadin.starter.skeleton;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.dom.Element;
import com.vaadin.flow.model.TemplateModel;
import com.vaadin.flow.router.LocationChangeEvent;
import com.vaadin.flow.router.View;
import com.vaadin.router.Route;
import com.vaadin.starter.skeleton.ToDoAppTemplate.ToDoAppModel;
import com.vaadin.starter.skeleton.data.ToDoBean;
import com.vaadin.starter.skeleton.data.ToDoService;
import com.vaadin.ui.Tag;
import com.vaadin.ui.common.HtmlImport;
import com.vaadin.ui.event.EventData;
import com.vaadin.ui.html.Input;
import com.vaadin.ui.polymertemplate.EventHandler;
import com.vaadin.ui.polymertemplate.Id;
import com.vaadin.ui.polymertemplate.PolymerTemplate;
import com.vaadin.ui.polymertemplate.RepeatIndex;

@Tag("todo-app")
@HtmlImport("frontend://todo-app.html")
@Route("")
public class ToDoAppTemplate extends PolymerTemplate<ToDoAppModel> implements View {
	@Id("new-todo")
	private Input newToDo;

	@Id("todoUl")
	private Element todoUl;

	@Id("all")
	private Element all;

	@Id("completed")
	private Element completed;

	@Id("active")
	private Element active;

	public ToDoAppTemplate() {
		setToDoList(ToDoService.getToDoList());
		newToDo.getElement().addPropertyChangeListener("value", e -> {
			if(e.isUserOriginated()){
				addToDo(newToDo.getValue());
				newToDo.setValue("");
			}
		});
	}

	void setToDoList(List<ToDoBean> toDoList) {
		getModel().setToDoList(new ArrayList<>(toDoList));
	}

	@EventHandler
	private void removeToDo(@RepeatIndex int itemIndex) {
		getModel().getToDoList().remove(itemIndex);
		refreshToDoList();
	}

	@EventHandler
	private void toggleComplete(@RepeatIndex int itemIndex){
		getModel().getToDoList().get(itemIndex).toggleComplete();
		if(getModel().getToDoList().get(itemIndex).isCompleted()) {
			//todoUl.getChild(itemIndex).getClassList().add("completed");
		}else{
			//todoUl.getChild(itemIndex).getClassList().remove("completed");
		}
	}

	@EventHandler
	private void toggleAll(@EventData("event.srcElement.checked") boolean completed){
		getModel().getToDoList().forEach(toDoBean -> toDoBean.setCompleted(completed));
	}

	@EventHandler
	private void clearCompleted(){
		getModel().getToDoList().removeIf(ToDoBean::isCompleted);
		refreshToDoList();
	}

	@EventHandler
	private void showActive(){
		getUI().get().navigateTo("active");
	}

	@EventHandler
	private void showCompleted(){
		getUI().get().navigateTo("completed");
	}

	@EventHandler
	private void showAll(){
		getUI().get().navigateTo("");
	}

	@EventHandler
	private void editToDo(@RepeatIndex int itemIndex){
		todoUl.getChild(itemIndex).getClassList().add("editing");
	}


	private void addToDo(String text) {
		getModel().getToDoList().add(0, new ToDoBean(text));
		refreshToDoList();
	}

	private void refreshToDoList(){
		getModel().setToDoList(getModel().getToDoList());
	}

	public interface ToDoAppModel extends TemplateModel {
		void setToDoList(List<ToDoBean> toDoList);

		List<ToDoBean> getToDoList();

		void setShowActive(boolean showActive);

		void setShowCompleted(boolean showCompleted);
	}

	public void setShowActive(boolean showActive){
		getModel().setShowActive(showActive);
	}

	public void setShowCompleted(boolean showCompleted){
		getModel().setShowCompleted(showCompleted);
	}

	@Override
	public void onLocationChange(LocationChangeEvent locationChangeEvent) {
		String viewFilterString = locationChangeEvent.getPathWildcard();
		ViewFilter viewFilter = ViewFilter.findByPath(viewFilterString);
		if(ViewFilter.ACTIVE == viewFilter){
			getModel().setShowActive(true);
			getModel().setShowCompleted(false);
			active.getClassList().remove("selected");
			all.getClassList().remove("selected");
			completed.getClassList().remove("selected");
			active.getClassList().add("selected");
		}else if(ViewFilter.COMPLETED == viewFilter){
			getModel().setShowActive(false);
			getModel().setShowCompleted(true);
			active.getClassList().remove("selected");
			all.getClassList().remove("selected");
			completed.getClassList().remove("selected");
			completed.getClassList().add("selected");
		}else{
			getModel().setShowActive(true);
			getModel().setShowCompleted(true);
			active.getClassList().remove("selected");
			all.getClassList().remove("selected");
			completed.getClassList().remove("selected");
			all.getClassList().add("selected");
		}
	}
}


