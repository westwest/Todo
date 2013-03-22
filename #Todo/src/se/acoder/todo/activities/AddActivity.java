package se.acoder.todo.activities;
import se.acoder.todo.R;
import se.acoder.todo.R.id;
import se.acoder.todo.R.layout;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddActivity extends BaseActivity {
	private final static String TAG = AddActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
	}
	
	public void save(View v){
		EditText description = (EditText) findViewById(R.id.task_description);
		if(getTaskManager().addTask(description.getText().toString()) == null)
			Log.w(TAG, "Task was not created successfully.");
		else
			totalTasks++;
		goBack(v);
	}

	public void goBack(View v){
		Intent goBack = new Intent(this, TodoActivity.class);
		startActivity(goBack);
	}
}
