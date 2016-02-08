package bg.uni_sofia.fmi.corejava.stress_tester.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.*;

import bg.uni_sofia.fmi.corejava.stress_tester.app.RequestData;;

public class RequestDataTests {

	@Test
	public void testGetQueryMethod() throws FileNotFoundException, IOException {
		RequestData rd = new RequestData(new File("recourses/request.txt"));
		assertEquals("GET http://java.voidland.org/ HTTP/1.1", rd.getQueryMethod());
	}
	
	@Test
	public void testGetHostQueryMethod() throws FileNotFoundException, IOException {
		RequestData rd = new RequestData(new File("recourses/request.txt"));
		assertEquals("Host: java.voidland.org", rd.getQueryHost());	
	}
	
	@Test
	public void testGetHostNameMethod() throws FileNotFoundException, IOException {
		RequestData rd = new RequestData(new File("recourses/request.txt"));
		assertEquals("java.voidland.org", rd.getHostName());	
	}
	
	@Test
	public void testGetExcepectedResponseMethod() throws FileNotFoundException, IOException {
		RequestData rd = new RequestData(new File("recourses/request.txt"));
		assertEquals("HTTP/1.1 200 OK", rd.getExpectedResponse());	
	}
}
