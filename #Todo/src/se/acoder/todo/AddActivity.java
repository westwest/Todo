package se.acoder.todo;

import se.acoder.todo.file.TaskManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends Activity {
	private final static String TAG = AddActivity.class.getSimpleName();
	private TaskManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		tm = TaskManager.getInstance(getApplication());
	}
	
	public void save(View v){
		EditText description = (EditText) findViewById(R.id.task_description);
		if(tm.addTask(description.getText().toString()) == null)
			Log.w(TAG, "Task was not created successfully.");
		goBack(v);
	}

	public void goBack(View v){
		Intent goBack = new Intent(this, TodoActivity.class);
		startActivity(goBack);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add, menu);
		return true;
	}

}
