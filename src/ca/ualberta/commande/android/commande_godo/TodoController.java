package ca.ualberta.commande.android.commande_godo;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;
import ca.ualberta.commande.android.commande_godo.data.TodoList;
import ca.ualberta.commande.android.commande_godo.data.TodosDataSource;

public class TodoController {
	
	private static final int SELECT_MODE_ON = 1;
	private static final int SELECT_MODE_OFF = 0;
	private static final int DISPLAY_INBOX = 1;
	private static final int DISPLAY_ARCHIVED = 2;
	
	private ListActivity activity;
	private Context context;
	private TodosDataSource datasource;
	private List<TodoItem> displayList;
	private int selectMode = SELECT_MODE_OFF;
	
	public TodoController(ListActivity activity, Context context, TodosDataSource datasource) {
		this.activity = activity;
		this.context = context;
		this.datasource = datasource;
	}
    
    public void displayActiveTodos() {
    	displayList = getDisplayTodos(DISPLAY_INBOX);
		TodoAdapter adapter = new TodoAdapter(context, R.layout.item_todo, displayList);
		activity.setListAdapter(adapter);
		activity.setTitle("GoDo - Inbox");
    }
    
    public void displayArchivedTodos() {
    	displayList = getDisplayTodos(DISPLAY_ARCHIVED);
		TodoAdapter adapter = new TodoAdapter(context, R.layout.item_todo, displayList);
		activity.setListAdapter(adapter);
		activity.setTitle("GoDo - Archive");
    }
    
    private List<TodoItem> getDisplayTodos(int displayMode) {
    	
    	List<TodoItem> newDisplayTodos = new ArrayList<TodoItem>();
    	
    	switch (displayMode) {
		case DISPLAY_INBOX:
			for (TodoItem todo : datasource.todos) {
				if (!todo.isArchived()) {
					newDisplayTodos.add(todo);
				}
			}
			return newDisplayTodos;
			
		case DISPLAY_ARCHIVED:
			for (TodoItem todo : datasource.todos) {
				if (todo.isArchived()) {
					newDisplayTodos.add(todo);
				}
			}
			return newDisplayTodos;

		default:
			return datasource.todos;
		}
    }
	
}
