package se.acoder.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 
 */
public class TodoActivity extends BaseActivity {

	public void addNewItem(View v){
		Intent addItemIntent = new Intent(this, AddActivity.class);
		startActivity(addItemIntent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		ListView ls = (ListView) findViewById(R.id.task_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, getTaskManager().getTaskList());
        ls.setAdapter(adapter);
        
        /*
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(AdapterView<?> parent, View view,
					int pos, long id) {
				String item = parent.getItemAtPosition(pos).toString();
				getCategoryManager().setActiveCategory(item);
			}
			public void onNothingSelected(AdapterView<?> arg0) {
			}
        	
        });
        */
	}
}
