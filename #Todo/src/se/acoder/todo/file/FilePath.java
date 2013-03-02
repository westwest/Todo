package se.acoder.todo.file;

import android.content.Context;

/**
 * 
 * @author Johannes Westlund
 *
 */
public class FilePath {
	private Context context;
	private String fileName;
	private final static String extention = ".txt";
	
	public FilePath(String fileName, Context context){
		this.context = context;
		this.fileName = fileName;
	}
	public String getName(){
		return fileName+extention;
	}
	public Context getContext(){
		return context;
	}
	public String getSimpleName(){
		return fileName;
	}
	public static String getExtention(){
		return extention;
	}
	public String getURI(){
		return context.getFilesDir().getAbsolutePath() +"/"+ fileName + extention;
	}
	public String getPath(){
		return context.getFilesDir().getAbsolutePath();
	}
	
}
