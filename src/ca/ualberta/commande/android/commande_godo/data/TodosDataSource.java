package ca.ualberta.commande.android.commande_godo.data;

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
	
	public List<TodoItem> findAll() {
		List <TodoItem> todoList = new ArrayList<TodoItem>();
		TodoItem todo = TodoItem.getNew("Initial Todo");
		todoList.add(todo);
		return todoList;
	}
	
	public boolean update(TodoItem todo) {
		return true;
	}
	
	public boolean remove(TodoItem todo) {
		return true;
	}
	
	public boolean writeTodos(List<TodoItem> todos) throws IOException, FileNotFoundException {
		
		// http://www.lynda.com/Android-tutorials/Creating-reading-JSON-data-files/112584/121170-4.html, Sept 14, 2014
		FileOutputStream fos = this.context.openFileOutput("todos", Context.MODE_PRIVATE);
		TodoJsonWriter.writeJsonStream(fos, todos);
		return true;
	}
	
}
