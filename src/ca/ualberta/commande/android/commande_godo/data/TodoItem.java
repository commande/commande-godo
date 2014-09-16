package ca.ualberta.commande.android.commande_godo.data;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TodoItem {
	private String key;
	private String title;
	private boolean completed = false;
	private boolean archived = false;
	private boolean selected = false;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public void toggleCompleted() {
		if (this.completed) {
			this.setCompleted(false);
		} else {
			this.setCompleted(true);
		}
	}
	public boolean isArchived() {
		return archived;
	}
	public void setArchived(boolean archived) {
		this.archived = archived;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public void toggleSelected() {
		if (this.selected) {
			this.setSelected(false);
		} else {
			this.setSelected(true);
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public static TodoItem getNew(String title) {
		
		// Set key of todo based on timestamp of creation
		Locale locale = new Locale("en_US");
		Locale.setDefault(locale);
		String pattern = "yyyy-MM-dd HH:mm:ss Z";
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		String key = formatter.format(new Date());
		
		// return a created todo with initial data
		TodoItem todo = new TodoItem();
		todo.setKey(key);
		todo.setTitle(title);
		return todo;
	}
	
	public static TodoItem getNew(String key, String title, boolean completed, boolean archived) {
		// return a created todo with initial data
		TodoItem todo = new TodoItem();
		todo.setKey(key);
		todo.setTitle(title);
		todo.setCompleted(completed);
		todo.setArchived(archived);
		return todo;
	}
}
