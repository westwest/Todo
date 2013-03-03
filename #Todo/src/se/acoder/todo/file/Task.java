package se.acoder.todo.file;

/**
 * Simple datatype for accomodating tasks.
 * @author Johannes Westlund
 *
 */
public class Task {
	private String description;
	private int id;
	
	public Task(int id, String description){
		this.id = id;
		this.description = description;
	}
	
	public int getId(){
		return id;
	}
	public String getDescription(){
		return description;
	}
}
