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

/**
 * Manages actual interaction with the Android file-system.
 * @author Johannes Westlund
 *
 */
public class FileIO {
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
		} catch (IOException e) {
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
			} catch (IOException e) {
			}
		}
		return false;
	}

	public boolean removeTask(FilePath path, Task task){
		if(fileExists(path)){
			try {
				File f = new File(path.getURI());
				RandomAccessFile raf = new RandomAccessFile(f, "rw");
				boolean found = false;
				String id = task.getId()+"";
				String line;
				long lastPos = 0;
				while(!found && (line = raf.readLine()) != null){
					if(line.split(";")[0].equals(id)){
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
			} catch (IOException e) {
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
			while( (rawline = br.readLine()) != null){
				String[] line = rawline.split(";");
				Task task = new Task(Integer.parseInt(line[0]), line[1]);
				lines.add(task);
			}
			return lines;
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		return null;
	}
}
