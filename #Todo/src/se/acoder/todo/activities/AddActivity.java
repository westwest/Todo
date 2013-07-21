package se.acoder.todo.activities;
import se.acoder.todo.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 
 * @author Johannes Westlund
 *
 */
public class AddActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
	}
	
	public void save(View v){
		EditText description = (EditText) findViewById(R.id.task_description);
		if(description.getText().length() != 0){
			if(getTaskManager().addTask(description.getText().toString()) == null){
				Toast.makeText(this, "Error creating task", Toast.LENGTH_LONG).show();
			}
			else{
				sk.addNew();
				goBack(v);
			}
		} else{
			Toast.makeText(this, "Please input a description", Toast.LENGTH_LONG).show();
		}
	}

	public void goBack(View v){
		Intent goBack = new Intent(this, TodoActivity.class);
		startActivity(goBack);
	}
}
