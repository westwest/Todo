package se.acoder.todo;

import se.acoder.todo.file.TaskManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class BaseActivity extends Activity {
	private TaskManager tm;

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		tm = TaskManager.getInstance(getApplicationContext());
	}
	
	public TaskManager getTaskManager(){
		return tm;
	}

}
