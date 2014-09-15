package ca.ualberta.commande.android.commande_godo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewTodoActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newtodo);
		setTitle("GoDo - New Todo");
	}
	
	public void saveNewTodo(View v) {
		// Get todo item text and trim the whitespace, then make available to caller
		while (true) {
			
			// Get todo title and trim whitespace
			EditText todoTitleInput = (EditText) findViewById(R.id.editText1);
			String todoTitle = todoTitleInput.getText().toString().trim();
			
			// Make sure user gives a descriptive title and try again if they don't.
			if (todoTitle.equals("")) {
				Toast.makeText(this, "Please give your todo a descriptive title.", Toast.LENGTH_SHORT).show();
				return;
			}
			
			Intent intent = new Intent();
			intent.putExtra("todoTitle", todoTitle);
			setResult(RESULT_OK, intent);
			finish();
			return;
		}
		
	}
	public void cancelNewTodo(View v) {
		finish();
	}

}
