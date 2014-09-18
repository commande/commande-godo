package ca.ualberta.commande.android.commande_godo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SummaryActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		setTitle("GoDo - Summary");
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		int tic = extras.getInt("tic");
		int tiu = extras.getInt("tiu");
		int tia = extras.getInt("tia");
		int aic = extras.getInt("aic");
		int aiu = extras.getInt("aiu");
		
		TextView viewToSet = (TextView) findViewById(R.id.total_items_completed);
		viewToSet.setText("Total Items Completed: " + tic);
		
		viewToSet = (TextView) findViewById(R.id.total_items_uncompleted);
		viewToSet.setText("Total Items Uncompleted: " + tiu);
		
		viewToSet = (TextView) findViewById(R.id.total_items_archived);
		viewToSet.setText("Total Items Archived: " + tia);
		
		viewToSet = (TextView) findViewById(R.id.archived_items_completed);
		viewToSet.setText("Archived Items Completed: " + aic);
		
		viewToSet = (TextView) findViewById(R.id.archived_items_uncompleted);
		viewToSet.setText("Archived Items Uncompleted: " + aiu);
		
	}
	
	public void close(View v) {
		finish();
	}
}
