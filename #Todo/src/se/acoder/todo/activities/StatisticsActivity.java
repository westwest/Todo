package se.acoder.todo.activities;

import se.acoder.todo.R;
import android.os.Bundle;
import android.widget.TextView;

public class StatisticsActivity extends BaseActivity {
	TextView text1, text2;
	int totalTasks, doneTasks;

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
		totalTasks = sp.getInt("totalTasks", 0);
		doneTasks = sp.getInt("doneTasks", 0);
		text1.setText("Total number of tasks: " + totalTasks);
		text2.setText("Completed Tasks: " + doneTasks);
	}

}
