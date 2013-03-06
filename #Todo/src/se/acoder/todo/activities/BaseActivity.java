package se.acoder.todo.activities;

import se.acoder.todo.R;
import se.acoder.todo.file.TaskManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public abstract class BaseActivity extends Activity {
	private final static String TAG = BaseActivity.class.getSimpleName();
	private TaskManager tm;
	protected SharedPreferences sp;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tm = TaskManager.getInstance(getApplicationContext());
		sp = getSharedPreferences("statistics", MODE_PRIVATE);
	}
	
	public TaskManager getTaskManager(){
		return tm;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		onNavigation(item);
		return true;
	}
	
	private void onNavigation(MenuItem item) {
		Log.d(TAG, "ActionBar Navigation");
		Class<?> c = this.getClass();
		switch (item.getItemId()) {
		case R.id.action_home:
			c = TodoActivity.class;
			break;
		case R.id.action_statistics:
			c = StatisticsActivity.class;
			break;
		}
		if(c != this.getClass()){
			Intent intent = new Intent(this, c);
			this.startActivity(intent);
		}
    }
}
