package ca.ualberta.commande.android.commande_godo;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;
import ca.ualberta.commande.android.commande_godo.data.TodosDataSource;

public class MainActivity extends ListActivity {

	private static final int NEW_TODO_REQUEST_CODE = 100;
	private static final int DISPLAY_ACTIVE = 1;
	private static final int DISPLAY_ARCHIVED = 2;
	private static final int SELECT_MODE_ON = 1;
	private static final int SELECT_MODE_OFF = 0;

	private TodosDataSource datasource;
	private TodoAdapter adapter;
	private List<TodoItem> displayTodos;
	private int displayMode = DISPLAY_ACTIVE;
	private int selectMode = SELECT_MODE_OFF;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new TodosDataSource(this);

		// set the adapter and display todos
		displayActiveTodos();

	}

	public void displayDisplayedTodos() {
		switch (displayMode) {
		case DISPLAY_ACTIVE:
			displayActiveTodos();
			break;

		case DISPLAY_ARCHIVED:
			displayArchivedTodos();
			break;

		default:
			break;
		}
	}

	public void displayActiveTodos() {
		displayMode = DISPLAY_ACTIVE;
		displayTodos = getDisplayTodos(displayMode);
		adapter = new TodoAdapter(this, R.layout.item_todo, displayTodos);
		setListAdapter(adapter);
		setTitle("GoDo - Inbox");
	}

	public void displayArchivedTodos() {
		displayMode = DISPLAY_ARCHIVED;
		displayTodos = getDisplayTodos(displayMode);
		adapter = new TodoAdapter(this, R.layout.item_todo, displayTodos);
		setListAdapter(adapter);
		setTitle("GoDo - Archive");
	}

	public List<TodoItem> getDisplayTodos(int displayMode) {
		List<TodoItem> newDisplayTodos = new ArrayList<TodoItem>();

		switch (displayMode) {
		case DISPLAY_ACTIVE:
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
		if (id == R.id.action_displayarchived) {
			displayMode = DISPLAY_ARCHIVED;
			displayDisplayedTodos();
			selectMode = SELECT_MODE_OFF;
		}
		if (id == R.id.action_displayactive) {
			displayMode = DISPLAY_ACTIVE;
			displayDisplayedTodos();
			selectMode = SELECT_MODE_OFF;
		}
		if (id == R.id.action_emailall) {
			// http://stackoverflow.com/questions/8701634/send-email-intent Sept 17, 2014
			selectMode = SELECT_MODE_OFF;
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
		            "mailto","", null));
			emailIntent.putExtra(Intent.EXTRA_SUBJECT, TodoEmailer.generateSubject(datasource.todos));
			emailIntent.putExtra(Intent.EXTRA_TEXT, TodoEmailer.generateBody(datasource.todos));
			startActivity(Intent.createChooser(emailIntent, "Email All Todos"));
		}
		if (id == R.id.action_summary) {
			Intent intent = new Intent(this, SummaryActivity.class);
			intent.putExtra("tic", datasource.getStat("tic"));
			intent.putExtra("tiu", datasource.getStat("tiu"));
			intent.putExtra("tia", datasource.getStat("tia"));
			intent.putExtra("aic", datasource.getStat("aic"));
			intent.putExtra("aiu", datasource.getStat("aiu"));
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	public void showNewTodoActivity(View v) {
		Intent intent = new Intent(this, NewTodoActivity.class);
		startActivityForResult(intent, NEW_TODO_REQUEST_CODE);
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
			displayActiveTodos();
			
		}
	}

	public void showSelectTodoActivity(View v) {
		// change bottom action bar contents to show select options
		// http://stackoverflow.com/questions/3995215/add-and-remove-views-in-android-dynamically,
		// Sept 15, 2014
		// http://stackoverflow.com/questions/3142067/android-set-style-in-code,
		// Sept 15, 2014

		// add select-mode bar on top of bottom action bar
		RelativeLayout bottomActionBar = (RelativeLayout) findViewById(R.id.bottom_action_bar);
		RelativeLayout parentView = (RelativeLayout) bottomActionBar
				.getParent();

		if (displayMode == DISPLAY_ACTIVE) {
			getLayoutInflater().inflate(R.layout.item_selectactionbar,
					parentView);
		} else if (displayMode == DISPLAY_ARCHIVED) {
			getLayoutInflater().inflate(R.layout.item_selectactionbararchived,
					parentView);
		}

		// Switch to select mode
		selectMode = SELECT_MODE_ON;
	}

	public void selectAllTodos(View v) {
		for (TodoItem todo : displayTodos) {
			todo.setSelected(true);
		}
		adapter.notifyDataSetChanged();
	}

	public void cancelSelect(View v) {

		// Remove the select action bar from the view and turn off select mode
		RelativeLayout selectActionBar = (RelativeLayout) findViewById(R.id.select_action_bar);
		RelativeLayout parentView = (RelativeLayout) selectActionBar.getParent();
		parentView.removeView(selectActionBar);
		selectMode = SELECT_MODE_OFF;

		// clear the selections and update the view
		for (TodoItem todo : displayTodos) {
			todo.setSelected(false);
		}
		displayDisplayedTodos();
	}

	public void archiveSelectedTodos(View v) {
		// for each todo shown, set its archived status then turn off select
		// mode.
		for (TodoItem todo : displayTodos) {
			if (todo.isSelected()) {
				todo.setArchived(true);
				datasource.update(todo);
			}
		}

		cancelSelect(v);
	}

	public void unarchiveSelectedTodos(View v) {
		// for each todo shown, set its archived status then turn off select
		// mode.
		for (TodoItem todo : displayTodos) {
			if (todo.isSelected()) {
				todo.setArchived(false);
				datasource.update(todo);
			}
		}
		cancelSelect(v);
	}

	public void deleteSelectedTodos(View v) {
		TodoItem todo;
		int s = displayTodos.size();
		for (int i = 0; i < s; i++) {
			todo = displayTodos.get(i);
			if (todo.isSelected()) {
				datasource.remove(todo);
			}
		}
		cancelSelect(v);
	}
	
	public void emailSelectedTodos(View v) {
		// http://stackoverflow.com/questions/8701634/send-email-intent Sept 17, 2014
		
		List<TodoItem> emailTodos = new ArrayList<TodoItem>();
		for (TodoItem todo : displayTodos) {
			if (todo.isSelected()) {
				emailTodos.add(todo);
			}
		}
		
		Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
	            "mailto","", null));
		emailIntent.putExtra(Intent.EXTRA_SUBJECT, TodoEmailer.generateSubject(emailTodos));
		emailIntent.putExtra(Intent.EXTRA_TEXT, TodoEmailer.generateBody(emailTodos));
		startActivity(Intent.createChooser(emailIntent, "Email Selected Todos"));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int pos, long id) {
		super.onListItemClick(l, v, pos, id);

		TodoItem updatedTodo = displayTodos.get(pos);

		switch (selectMode) {
		case SELECT_MODE_ON:
			updatedTodo.toggleSelected();
			adapter.notifyDataSetChanged();
			break;

		case SELECT_MODE_OFF:
			// When user clicks a todo, toggle its completed status and updatethe view
			updatedTodo.toggleCompleted();
			datasource.update(updatedTodo);
			adapter.notifyDataSetChanged();
			break;

		default:
			break;
		}

	}
}
