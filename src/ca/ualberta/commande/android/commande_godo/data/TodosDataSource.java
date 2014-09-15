package ca.ualberta.commande.android.commande_godo.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class TodosDataSource {
	
	private Context context;
	
	public TodosDataSource(Context context) {
		this.context = context;
	}
	
	public List<TodoItem> getEmptyList() {
		List<TodoItem> todos = new ArrayList<TodoItem>();
		return todos;
	}
	
	public List<TodoItem> loadTodos() throws IOException {
		
		FileInputStream fis = this.context.openFileInput("todos");
		List<TodoItem> todoList = TodoJsonReader.readJsonStream(fis);
		fis.close();
		return todoList;
	}
	
	public boolean update(TodoItem todo) {
		return true;
	}
	
	public boolean remove(TodoItem todo) {
		return true;
	}
	
	public boolean writeTodos(List<TodoItem> todos) throws IOException {
		
		// http://www.lynda.com/Android-tutorials/Creating-reading-JSON-data-files/112584/121170-4.html, Sept 14, 2014
		FileOutputStream fos = this.context.openFileOutput("todos", Context.MODE_PRIVATE);
		TodoJsonWriter.writeJsonStream(fos, todos);
		fos.close();
		return true;
	}
	
}
