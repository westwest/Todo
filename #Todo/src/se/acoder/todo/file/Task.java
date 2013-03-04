package se.acoder.todo.file;

/**
 * Simple datatype for accomodating tasks.
 * @author Johannes Westlund
 *
 */
public class Task {
	private String description;
	private int id;
	
	protected Task(int id, String description){
		this.id = id;
		this.description = description;
	}
	
	public int getId(){
		return id;
	}
	public String getDescription(){
		return description;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o != null && o.getClass() == getClass()){
			Task other = (Task) o;
			if(id == other.id && description.equals(other.description))
				return true;
		}
		return false;
		
	}
}
