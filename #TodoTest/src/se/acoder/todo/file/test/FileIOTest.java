package se.acoder.todo.file.test;

import java.io.File;
import java.io.IOException;

import se.acoder.todo.file.FileIO;
import se.acoder.todo.file.FilePath;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContentResolver;
import android.util.Log;

public class FileIOTest extends AndroidTestCase {
	private Context mockContext;
	private FileIO instance;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mockContext = new IsolatedContext(new MockContentResolver(), getContext());
		instance = FileIO.getInstance();
	}
	
	public void testFileExists(){
		FilePath p = new FilePath("Foo", getContext());
		File a = new File(p.getURI());
		try {
			a.createNewFile();
			Log.d("TEST", "Created file");
		} catch (IOException e) {
			Log.d("TEST", "IOException when trying to create new file");
		}
		assertTrue("Did not find file", instance.fileExists(p));
		a.delete();
		assertFalse("File is found despite the fact that it is already deleted",
				instance.fileExists(p));
	}
	
	public void testCreateAndDelete(){
		FilePath fp = new FilePath("FooBar", getContext());
		assertTrue("Uncreated file cannot exist...", !instance.fileExists(fp));
		assertTrue("Error during creation", instance.create(fp));
		assertTrue("Created file must exist", instance.fileExists(fp));
		assertTrue("Error during deletion", instance.delete(fp));
		assertTrue("Deleted file cannot exist...", !instance.fileExists(fp));
	}
}
