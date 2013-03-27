package se.acoder.todo.activities;

import se.acoder.todo.R;
import se.acoder.todo.file.TaskManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StatisticsActivity extends BaseActivity {
	private TextView text1, text2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statistics);
		
		text1 = (TextView) findViewById(R.id.statistics_1);
		text2 = (TextView) findViewById(R.id.statistics_2);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		text1.setText("Total number of tasks: " + totalTasks);
		text2.setText("Completed Tasks: " + doneTasks);
	}
	
	public void onReset(View v){
		totalTasks = TaskManager.getInstance(this).getTaskList().length;
		doneTasks = 0;
		onResume();
	}

}
