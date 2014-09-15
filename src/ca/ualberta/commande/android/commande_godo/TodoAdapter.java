/*** 
 * Constructor method, objects declaration and getView method structure modified from:
 * http://devtut.wordpress.com/2011/06/09/custom-arrayadapter-for-a-listview-android/
 * Sept 13, 2014
 */

package ca.ualberta.commande.android.commande_godo;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import ca.ualberta.commande.android.commande_godo.data.TodoItem;

public class TodoAdapter extends ArrayAdapter<TodoItem> {

	private List<TodoItem> objects;
	
	public TodoAdapter(Context context, int textViewResourceId, List<TodoItem> todos) {
		super(context, textViewResourceId, todos);
		this.objects = todos;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){

		// assign the view we are converting to a local variable
		View v = convertView;

		// first check to see if the view is null. if so, we have to inflate it.
		// to inflate it basically means to render, or show, the view.
		if (v == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = inflater.inflate(R.layout.item_todo, null);
		}

		/*
		 * Recall that the variable position is sent in as an argument to this method.
		 * The variable simply refers to the position of the current object in the list. (The ArrayAdapter
		 * iterates through the list we sent it)
		 * 
		 * Therefore, todo refers to the current Item object.
		 */
		TodoItem todo = objects.get(position);

		if (todo != null) {

			// Obtain a reference to the views to manipulate
			CheckedTextView ctv = (CheckedTextView) v;
			
			// check to see if each individual textview is null.
			// if not, set its attributes
			if (ctv != null) {
				ctv.setText(todo.getTitle());
				ctv.setChecked(todo.isCompleted());
				
				if (todo.isCompleted()) {
					ctv.setTextColor(Color.LTGRAY);
					// http://stackoverflow.com/questions/9786544/creating-a-strikethrough-text-in-android, Sept 13, 2014
					ctv.setPaintFlags(ctv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				} else {
					ctv.setTextColor(Color.BLACK);
					ctv.setPaintFlags(ctv.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
				}
			}
		}

		// the view must be returned to our activity
		return v;

	}
}
