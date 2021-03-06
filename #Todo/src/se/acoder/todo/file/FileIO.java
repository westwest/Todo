package se.acoder.todo.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class FileIO {
	private final static String TAG = FileIO.class.getSimpleName();
	private static FileIO instance;
	
	/**
	 * FileIO can only exist in one instance and is accessed through this method.
	 * @return reference to singleton instance of FileIO.
	 */
	public static FileIO getInstance() {
		if(instance == null){
			synchronized ( FileIO .class){
				if(instance == null){
					instance = new FileIO();
				}
			}
		}
		return instance;
	}
	public boolean create(FilePath path){
		Context c = path.getContext();
		if(fileExists(path)){
			delete(path);
		}
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					c.openFileOutput(path.getName(), Context.MODE_APPEND)));
			writer.append("Id;Task;");
			writer.newLine();
			writer.close();
			return true;
			
		} catch (FileNotFoundException e) {
			Log.d(TAG, "File not found");
		} catch (IOException e) {
			Log.d(TAG, "IO-Exception");
		}
		return false;
	}
	/**
	 * Saves at CSV format, using ";" as delimiter.
	 * @param savePath Name of save-file.
	 * @param rows Array of strings to be saved.
	 * @param c The current context.
	 * @return true if save successful.
	 */
	public boolean append(FilePath savePath, ArrayList<Task> rows) {
		if(fileExists(savePath)){
			Context c = savePath.getContext();
			try {
				BufferedWriter writer = new BufferedWriter( new OutputStreamWriter(
						c.openFileOutput(savePath.getName(), Context.MODE_APPEND)));
				for(Task t : rows){
					writer.append(t.getId()+";"+t.getDescription()+";");
					writer.newLine();
					writer.flush();
				}
				writer.close();
				return true;
			} catch (FileNotFoundException e) {
				Log.d(TAG, "Exception: File not found.");
			} catch (IOException e) {
				Log.d("TAG", "Exception: IO");
			}
		}
		return false;
	}

	public boolean removeTask(FilePath path, Task task){
		Log.i(TAG, "Attempting removeTask");
		if(fileExists(path)){
			try {
				File f = new File(path.getURI());
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				boolean found = false;
				String id = task.getId()+"";
				String line;
				long lastPos = 0;
				Log.i(TAG, "Tries to find task '"+id+"'");
				while(!found && (line = raf.readLine()) != null){
					Log.d(TAG, "Current row: '"+line+"'");
					if(line.split(";")[0].equals(id)){
						Log.d(TAG, "Matching row was found");
						found = true;
						String restOfFile = "";
						String tempStr;
						while((tempStr = raf.readLine()) != null){
							restOfFile+=tempStr;
							restOfFile+="\n";
						}
						raf.seek(lastPos);
						raf.setLength(lastPos);
						raf.writeBytes(restOfFile);
						raf.writeBytes("");
						raf.close();
						return true;
					}
					lastPos = raf.getFilePointer(); 
				}
				raf.close();
			} catch (FileNotFoundException e) {
				Log.d(TAG,"File not found.");
			} catch (IOException e) {
				Log.d(TAG, "IO Exception");
			}
		}
		return false;
	}
	/**
	 * Deletes file at given file-path.
	 * @param path A path-object representing the file-path to file that is to be deleted.
	 */
	public boolean delete(FilePath path) {
		return path.getContext().deleteFile(path.getName());
	}
	public boolean fileExists(FilePath path){
		File f = new File(path.getURI());
		return f.isFile();
	}
	public ArrayList<Task> load(FilePath path) {
		Context c = path.getContext();
		ArrayList<Task> lines = new ArrayList<Task>();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(c.openFileInput(path.getName())));
			String rawline;
			//Skip first line
			Log.i(TAG, "Skipping: "+ br.readLine());
			while( (rawline = br.readLine()) != null){
				Log.d(TAG, "Raw line: "+ rawline);
				String[] line = rawline.split(";");
				Log.d(TAG, "Formatted line: "+ line[0]+ ", "+line[1] );
				Task task = new Task(Integer.parseInt(line[0]), line[1]);
				lines.add(task);
			}
			return lines;
			
		} catch (FileNotFoundException e) {
			Log.d(TAG, "[load()] File Not Found, " + path.getName());
			e.printStackTrace();
		} catch (IOException e) {
			Log.d(TAG, "[load()] IOException");
			e.printStackTrace();
		}
		return null;
	}
}
