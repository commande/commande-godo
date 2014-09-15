// http://developer.android.com/reference/android/util/JsonReader.html, Sept 14, 2014

package ca.ualberta.commande.android.commande_godo.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;

public class TodoJsonReader {
	public static List<TodoItem> readJsonStream(InputStream in) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
		try {
			return readTodosArray(reader);
		} finally {
			reader.close();
		}
	}

	public static List<TodoItem> readTodosArray(JsonReader reader) throws IOException {
		List<TodoItem> todos = new ArrayList<TodoItem>();

		reader.beginArray();
		while (reader.hasNext()) {
			todos.add(readTodo(reader));
		}
		reader.endArray();
		return todos;
	}

	public static TodoItem readTodo(JsonReader reader) throws IOException {
		String key = null;
		String title = null;
		boolean completed = false;
		boolean archived = false;

		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals("key")) {
				key = reader.nextString();
			} else if (name.equals("title")) {
				title = reader.nextString();
			} else if (name.equals("completed")) {
				completed = reader.nextBoolean();
			} else if (name.equals("archived")) {
				completed = reader.nextBoolean();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return TodoItem.getNew(key, title, completed, archived);
	}
}
