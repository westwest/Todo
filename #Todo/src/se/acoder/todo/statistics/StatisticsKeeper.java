package se.acoder.todo.statistics;
import se.acoder.todo.file.TaskManager;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Keeps track of statistics during runtime.
 * @author Johannes Westlund
 *
 */
public class StatisticsKeeper {
	private static StatisticsKeeper instance;
	private Context context;
	private SharedPreferences sp;
	protected static int totalTasks, doneTasks;
	
	/**
	 * StatisticsKeeper can only exist in one instance and is accessed through this method.
	 * @param context Application context.
	 * @return reference to singleton instance of TaskManager.
	 */
	public static StatisticsKeeper getInstance(Context context) {
		if(instance == null){
			synchronized ( StatisticsKeeper .class){
				if(instance == null){
					instance = new StatisticsKeeper(context);
				}
			}
		}
		return instance;
	}
	private StatisticsKeeper(Context context){
		this.context = context;
		sp = context.getSharedPreferences("statistics", Context.MODE_PRIVATE);
		totalTasks = sp.getInt("totalTasks", 0);
		doneTasks = sp.getInt("doneTasks", 0);
	}
	
	public int markDone(){
		doneTasks++;
		onChange();
		return doneTasks;
	}
	public int addNew(){
		totalTasks++;
		onChange();
		return totalTasks;
	}
	public boolean resetStats(){
		totalTasks = TaskManager.getInstance(context).getTaskList().length;;
		doneTasks = 0;
		onChange();
		return true;
	}
	
	private void onChange(){
		SharedPreferences.Editor editor = sp.edit();
		editor.putInt("totalTasks", totalTasks);
		editor.putInt("doneTasks", doneTasks);
		editor.commit();
		
	}
	
	public int getTotalTasks(){
		return totalTasks;
	}
	public int getDoneTasks(){
		return doneTasks;
	}
	
}
