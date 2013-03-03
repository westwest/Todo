package se.acoder.todo.file.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import se.acoder.todo.file.FileIO;
import se.acoder.todo.file.FilePath;
import se.acoder.todo.file.Task;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContentResolver;
import android.util.Log;

public class FileIOTest extends AndroidTestCase {
	private Context mockContext;
	private FileIO instance;
	private FilePath fp;
	private ArrayList<Task> tasks;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockContext = new IsolatedContext(new MockContentResolver(), getContext());
		instance = FileIO.getInstance();
		fp = new FilePath("Foo Bar", getContext());
		
		tasks = new ArrayList<Task>();
		tasks.add(new Task(0,"Test-task0"));
		tasks.add(new Task(1,"Test-task1"));
		tasks.add(new Task(2,"Test-task2"));
		tasks.add(new Task(3,"Test-task3"));
		tasks.add(new Task(4,"Test-task4"));
	}
	
	public void testFileExists(){
		File a = new File(fp.getURI());
		try {
			a.createNewFile();
			Log.d("TEST", "Created file");
		} catch (IOException e) {
			Log.d("TEST", "IOException when trying to create new file");
		}
		assertTrue("Did not find file", instance.fileExists(fp));
		a.delete();
		assertFalse("File is found despite the fact that it is already deleted",
				instance.fileExists(fp));
	}
	
	public void testCreateAndDelete(){
		assertTrue("Uncreated file cannot exist...", !instance.fileExists(fp));
		assertTrue("Error during creation", instance.create(fp));
		assertTrue("Created file must exist", instance.fileExists(fp));
		assertTrue("Error during deletion", instance.delete(fp));
		assertTrue("Deleted file cannot exist...", !instance.fileExists(fp));
	}
	
	public void testCreateEnforcement(){
		assertTrue("Uncreated file cannot exist...", !instance.fileExists(fp));
		assertTrue("Editing should not be permitted", !instance.append(fp, tasks));
		assertTrue("Editing should not create new file", !instance.fileExists(fp));
	}
	
	public void testSaveAndLoad(){
		assertTrue("Error during creation", instance.create(fp));
		assertTrue("Error during save", instance.append(fp, tasks));
		assertNotNull("Load should not return null", instance.load(fp));
		assertEquals("Loaded list is not the same as saved list",tasks,instance.load(fp));
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		instance.delete(fp);
	}
}
