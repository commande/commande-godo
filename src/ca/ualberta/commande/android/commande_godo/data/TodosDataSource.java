package ca.ualberta.commande.android.commande_godo.data;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;

public class TodosDataSource {
	
	private Context context;
	public List<TodoItem> todos;
	
	public TodosDataSource(Context context) {
		this.context = context;
		this.todos = this.getTodos();
	}
	
	public List<TodoItem> getTodos() {
		List<TodoItem> todos;
		try {
        	todos = this.loadTodos();
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("TODOS", e.getMessage());
			todos = this.getEmptyList();
		}
		
		return todos;
	}
	
	private List<TodoItem> getEmptyList() {
		List<TodoItem> todos = new ArrayList<TodoItem>();
		return todos;
	}
	
	private List<TodoItem> loadTodos() throws IOException {
		
		FileInputStream fis = this.context.openFileInput("todos");
		List<TodoItem> todoList = TodoJsonReader.readJsonStream(fis);
		fis.close();
		return todoList;
	}
	
	public boolean update(TodoItem updatedTodo) {
		for (TodoItem originalTodo : this.todos) {
			if ( originalTodo.getKey().equals(updatedTodo.getKey()) ) {
				originalTodo = updatedTodo;
				this.saveTodos();
				return true;
			}
		}
		
		return false;
	}
	
	public boolean remove(TodoItem todo) {
		return true;
	}
	
	public boolean saveTodos() {
		try {
        	this.writeTodos(this.todos);
		} catch (Exception e) {
			// TODO: handle exception
			Log.i("TODOS", e.getMessage());
		}
		return true;
	}
	
	private boolean writeTodos(List<TodoItem> todos) throws IOException {
		
		// http://www.lynda.com/Android-tutorials/Creating-reading-JSON-data-files/112584/121170-4.html, Sept 14, 2014
		FileOutputStream fos = this.context.openFileOutput("todos", Context.MODE_PRIVATE);
		TodoJsonWriter.writeJsonStream(fos, todos);
		fos.close();
		return true;
	}
	
}
