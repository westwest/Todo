package se.acoder.todo.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
	/**
	 * Saves at CSV format, using ";" as delimiter.
	 * @param savePath Name of save-file.
	 * @param saveData Array of strings to be saved.
	 * @param c The current context.
	 * @return true if save successful.
	 */
	public boolean save(FilePath savePath, String[] saveData) {
		Context c = savePath.getContext();
		String writableData = "";
		for(int i = 0; i<saveData.length; i++){
			writableData += saveData[i]+";";
		}
		try {
			FileOutputStream fos = c.openFileOutput(savePath.getName(), Context.MODE_APPEND);
			fos.write(writableData.getBytes());
			fos.close();
			return true;
		} catch (FileNotFoundException e) {
			Log.d(TAG, "Exception: File not found.");
		} catch (IOException e) {
			Log.d("TAG", "Exception: IO");
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
	public String[] load(FilePath path) {
		Context c = path.getContext();
		try {
			BufferedReader br = new BufferedReader(
					new InputStreamReader(c.openFileInput(path.getName())));
			StringBuilder sb = new StringBuilder();
			String rawline;
			while( (rawline = br.readLine()) != null){
				sb.append(rawline);
			}
			String[] lines = sb.toString().trim().split(";");
			Log.d(TAG, sb.toString());
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
