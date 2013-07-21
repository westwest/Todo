package se.acoder.todo.activities;

import se.acoder.todo.R;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 * @author Johannes Westlund
 *
 */
public class TodoActivity extends BaseActivity {
	private final static String TAG = TodoActivity.class.getSimpleName();

	public void addNewItem(View v){
		Intent addItemIntent = new Intent(this, AddActivity.class);
		startActivity(addItemIntent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		Log.i(TAG,"Created Successfully");
	}
	
	@Override
	protected void onResume() {
		super.onStart();
		ListView ls = (ListView) findViewById(R.id.task_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, getTaskManager().getTaskList());
        ls.setAdapter(adapter);
        
        //Register click-listener
        ls.setOnItemClickListener(new OnItemClickListener() {
        	protected int taskId;
        	protected String description;

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				taskId = Integer.parseInt(""+id);
				description = getTaskManager().getTaskList()[taskId];
				AlertDialog.Builder builder = new AlertDialog.Builder(TodoActivity.this);
				builder.setTitle("Complete task");
				builder.setMessage(description);
				builder.setPositiveButton("Mark done", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Log.d(TAG, "Id of clicked task: " + taskId);
						if(getTaskManager().removeTask(taskId, description));
							sk.markDone();
						onResume();
					}
				});
				builder.setNegativeButton("Cancel", new OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
				builder.show();
				
			}
        	
		});
	}
}
