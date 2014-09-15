// http://developer.android.com/reference/android/util/JsonWriter.html Sept 14, 2014

package ca.ualberta.commande.android.commande_godo.data;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import android.util.JsonWriter;

public class TodoJsonWriter {
	public static void writeJsonStream(OutputStream out, List<TodoItem> todos) throws IOException {
	     JsonWriter writer = new JsonWriter(new OutputStreamWriter(out, "UTF-8"));
	     writer.setIndent("  ");
	     writeTodosArray(writer, todos);
	     writer.close();
	}
	
	public static void writeTodosArray(JsonWriter writer, List<TodoItem> todos) throws IOException {
		writer.beginArray();
		for (TodoItem todo : todos) {
			writeTodo(writer, todo);
		}
		writer.endArray();
	}

	public static void writeTodo(JsonWriter writer, TodoItem todo) throws IOException {
		writer.beginObject();
		writer.name("key").value(todo.getKey());
		writer.name("title").value(todo.getTitle());
		writer.name("completed").value(todo.isCompleted());
		writer.name("archived").value(todo.isArchived());
		writer.endObject();
	}
}
