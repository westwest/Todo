package se.acoder.todo.activities;

import se.acoder.todo.R;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author Johannes Westlund
 *
 */
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
		text1.setText("Total number of tasks: " + sk.getTotalTasks());
		text2.setText("Completed Tasks: " + sk.getDoneTasks());
	}
	
	public void onReset(View v){
		sk.resetStats();
		onResume();
	}

}
