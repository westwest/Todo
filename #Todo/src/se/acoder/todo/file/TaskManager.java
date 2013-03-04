package se.acoder.todo.file;

import java.util.ArrayList;

import android.content.Context;

/**
 * The class is basically a layer between the file-storage and the application
 * activities. It keeps track of all tasks.
 * @author Johannes Westlund
 *
 */
public class TaskManager {
	//private final static String TAG = TaskManager.class.getSimpleName();
	private final static String taskFileName = "taskList";
	private static TaskManager instance;
	private FilePath fp;
	private FileIO fio;
	
	/**
	 * TaskManager can only exist in one instance and is accessed through this method.
	 * @param context Application context.
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
	
	/**
	 * Default constructor.
	 * @param context Application context.
	 */
	private TaskManager(Context context){
		fp = new FilePath(taskFileName, context);
		fio = FileIO.getInstance();
		if(!fio.fileExists(fp))
			fio.create(fp);
	}
	
	/**
	 * Creates an array of strings on the format suitable
	 * for UI adaptors.
	 * @return The array of currently stored tasks.
	 */
	public String[] getTaskList(){
		ArrayList<Task> tasks = fio.load(fp);
		String[] taskList = new String[tasks.size()];
		for(int i = 0; i<tasks.size(); i++)
			taskList[i] = tasks.get(i).getDescription();
		return taskList;
	}
	
	/**
	 * Creates a new task and stores it.
	 * @param description The description of the task.
	 * @return A new task with the given description and a unique ID.
	 */
	public Task addTask(String description){
		int lastIndex = fio.load(fp).size()-1;
		int id;
		if(lastIndex < 0){
			id = 0;
		}else{
			id = fio.load(fp).get(lastIndex).getId();
		}
		Task task = new Task(id, description);
		ArrayList<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		if(fio.append(fp, tasks))
			return task;
		return null;
	}
	
	/**
	 * Removes given task.
	 * @param task The task to remove.
	 * @return True if removal was successful.
	 */
	public boolean removeTask(Task task){
		return fio.removeTask(fp, task);
	}
}
