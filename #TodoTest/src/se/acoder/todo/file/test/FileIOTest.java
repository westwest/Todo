package se.acoder.todo.file.test;

import se.acoder.todo.file.FilePath;
import android.content.Context;
import android.test.AndroidTestCase;
import android.test.IsolatedContext;
import android.test.mock.MockContentResolver;

public class FileIOTest extends AndroidTestCase {
	private Context mockContext;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		Context mockContext = new IsolatedContext(new MockContentResolver(), getContext());
	}
	
	public void testCreate(){
		FilePath fp = new FilePath("FooBar", mockContext);
		
	}
}
