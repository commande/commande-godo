package ca.ualberta.commande.android.commande_godo;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;
import ca.ualberta.commande.android.commande_godo.data.TodosDataSource;


public class MainActivity extends Activity {
	
	private TodosDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datasource = new TodosDataSource(this);
        List<TodoItem> todos = datasource.findAll();
        
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
}
