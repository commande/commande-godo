package ca.ualberta.commande.android.commande_godo;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;
import ca.ualberta.commande.android.commande_godo.data.TodosDataSource;


public class MainActivity extends ListActivity {
	
	private static final int NEW_TODO_REQUEST_CODE = 100;
	private static final int SELECT_TODO_REQUEST_CODE = 200;
	
	private static final int DISPLAY_ACTIVE = 1;
	private static final int DISPLAY_ARCHIVED = 2;
	private static final int DISPLAY_ALL = 3;
	
	private static final int SELECT_MODE_ON = 1;
	private static final int SELECT_MODE_OFF = 0;
	private static int SELECT_MODE = SELECT_MODE_OFF;
	
	private TodosDataSource datasource;
	private TodoAdapter adapter;
	private List<TodoItem> displayTodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // get the todos from the datasource.
        datasource = new TodosDataSource(this);
        
        //filter todos user is interested in seeing as selected from menu options
        displayTodos = getDisplayTodos(DISPLAY_ALL);
        
        // Display the todos to the user
		adapter = new TodoAdapter(this, R.layout.item_todo, displayTodos);
		setListAdapter(adapter);
        
    }
    
    public List<TodoItem> getDisplayTodos(int displayMode) {
    	List<TodoItem> displayTodos = null;
    	
    	switch (displayMode) {
		case DISPLAY_ACTIVE:
			for (TodoItem todo : datasource.todos) {
				if (!todo.isArchived()) {
					displayTodos.add(todo);
				}
			}
			return displayTodos;
			
		case DISPLAY_ARCHIVED:
			for (TodoItem todo : datasource.todos) {
				if (todo.isArchived()) {
					displayTodos.add(todo);
				}
			}
			return displayTodos;

		default:
			return datasource.todos;
		}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void showNewTodoActivity(View v) {
		Intent intent = new Intent(this, NewTodoActivity.class);
		startActivityForResult(intent, NEW_TODO_REQUEST_CODE);
	}
    
    public void showSelectTodoActivity(View v) {
    	// change bottom action bar contents to show select options
    	// http://stackoverflow.com/questions/3995215/add-and-remove-views-in-android-dynamically, Sept 15, 2014
    	// http://stackoverflow.com/questions/3142067/android-set-style-in-code, Sept 15, 2014
    	
    	// add select-mode bar on top of bottom action bar
    	RelativeLayout bottomActionBar = (RelativeLayout) findViewById(R.id.bottom_action_bar);
    	RelativeLayout parentView = (RelativeLayout)bottomActionBar.getParent();
    	getLayoutInflater().inflate(R.layout.item_selectactionbar, parentView);
    	
    	// Switch to select mode
    	SELECT_MODE = SELECT_MODE_ON;
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Respond to return from new todo creation
		if (requestCode == NEW_TODO_REQUEST_CODE && resultCode == RESULT_OK) {
			
			// Get new todo item information and add to todolist.
			String todoTitle = data.getStringExtra("todoTitle");
			TodoItem newTodo = TodoItem.getNew(todoTitle);
			
			// Save new todo to disk and update the view
			
			datasource.todos.add(newTodo);
			datasource.saveTodos();
			
			// Update the view
			displayTodos = getDisplayTodos(DISPLAY_ALL);
			adapter.notifyDataSetChanged();
		}
	}
    
    @Override
    protected void onListItemClick(ListView l, View v, int pos, long id) {
    	super.onListItemClick(l, v, pos, id);
    	
    	TodoItem updatedTodo = displayTodos.get(pos);
    	
    	switch (SELECT_MODE) {
		case SELECT_MODE_ON:
			updatedTodo.toggleSelected();
			adapter.notifyDataSetChanged();
			break;
			
		case SELECT_MODE_OFF:
			// When user clicks a todo, toggle its completed status
    		// first get the todo that was clicked and toggle the completed status
    		updatedTodo.toggleCompleted();
    		// save the changes on disk and memory
    		datasource.update(updatedTodo);
    		// update the view
    		adapter.notifyDataSetChanged();
			break;
			
		default:
			break;
		}
    		
    }
    
    public void cancelSelect(View v) {
    	RelativeLayout selectActionBar = (RelativeLayout) findViewById(R.id.select_action_bar);
    	RelativeLayout parentView = (RelativeLayout)selectActionBar.getParent();
    	parentView.removeView(selectActionBar);
    	SELECT_MODE = SELECT_MODE_OFF;
    	
    	// clear the selections
    	for (TodoItem todo : displayTodos) {
			todo.setSelected(false);
		}
    	adapter.notifyDataSetChanged();
    }
}
