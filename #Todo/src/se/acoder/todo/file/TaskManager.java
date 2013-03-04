package se.acoder.todo.file;

import java.util.ArrayList;

import android.content.Context;

public class TaskManager {
	//private final static String TAG = TaskManager.class.getSimpleName();
	private final static String taskFileName = "taskList";
	private static TaskManager instance;
	private FilePath fp;
	private FileIO fio;
	
	/**
	 * TaskManager can only exist in one instance and is accessed through this method.
	 * @return reference to singleton instance of TaskManager.
	 */
	public static TaskManager getInstance(Context context) {
		if(instance == null){
			synchronized ( FileIO .class){
				if(instance == null){
					instance = new TaskManager(context);
				}
			}
		}
		return instance;
	}
	
	private TaskManager(Context context){
		fp = new FilePath(taskFileName, context);
		fio = FileIO.getInstance();
	}
	
	public String[] getTaskList(){
		ArrayList<Task> tasks = fio.load(fp);
		String[] taskList = new String[tasks.size()];
		for(int i = 0; i<tasks.size(); i++)
			taskList[i] = tasks.get(i).getDescription();
		return taskList;
	}
	
}
