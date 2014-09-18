package ca.ualberta.commande.android.commande_godo;

import java.util.List;

import ca.ualberta.commande.android.commande_godo.data.TodoItem;

public class TodoEmailer {
	
	public static String generateBody(List<TodoItem> todos) {
		String emailBody = "";
		for (TodoItem todo : todos) {
			emailBody = emailBody + getCheckbox(todo) + " " + todo.getTitle() + "\n";
		}
		return emailBody;
	}
	
	public static String generateSubject(List<TodoItem> todos) {
		return "Here are some todos!";
	}
	
	private static String getCheckbox(TodoItem todo) {	
		return (todo.isCompleted()) ? "[X]" : "[   ]";
	}
}

