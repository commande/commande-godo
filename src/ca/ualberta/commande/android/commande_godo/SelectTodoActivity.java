package ca.ualberta.commande.android.commande_godo;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class SelectTodoActivity extends ListActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_selecttodo);
		setTitle("GoDo - Select Todos");
		
		//get displayTodos, this will be used to populate selected todo array
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int pos, long id) {
		super.onListItemClick(l, v, pos, id);
		// Respond to user tapping on displayed todo.
	}
	
	public void emailSelectedTodos(View v) {
		// launch new email activity
		
	}
	
	public void archiveSelectedTodos(View v) {
		// pass back selected array and let main activity handle the archive
	}

	public void deleteSelectedTodos(View v) {
		// pass back selected array and let main activity handle the delete
	}

	public void cancelSelect(View v) {
		// return to the calling activity
		finish();
	}

	public void selectAllTodos(View v) {
		// add all displayed todos to the selected array
	}

}
