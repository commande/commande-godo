package ca.ualberta.commande.android.commande_godo;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;
import ca.ualberta.commande.android.commande_godo.data.TodosDataSource;


public class MainActivity extends ListActivity {
	
	private static final int REQUEST_CODE = 100;
	private TodosDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // get the todos from the datasource
        datasource = new TodosDataSource(this);
        List<TodoItem> todos = datasource.findAll();
        
		TodoAdapter adapter = new TodoAdapter(
				this, R.layout.item_todo, todos);
		setListAdapter(adapter);
        
        // write the todos to the datasource
        try {
        	datasource.writeTodos(todos);
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        TodoItem todo = todos.get(0);
        Log.i("TODOS", todo.getKey());
        
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
		startActivityForResult(intent, REQUEST_CODE);
	}
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		// Respond to return from new todo creation
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			
			// Get new todo item information and add to todolist.
		}
	}
}
