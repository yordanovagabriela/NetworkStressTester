package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RequestDataTests {
	private RequestData requestData;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		requestData = new RequestData(new File("recourses/request.txt"));	
	}
	
	@Test
	public void testGetQueryMethod() throws FileNotFoundException, IOException {
		assertEquals("GET http://java.voidland.org/ HTTP/1.1", requestData.getQueryMethod());
	}
	
	@Test
	public void testGetHostQueryMethod() throws FileNotFoundException, IOException {
		assertEquals("Host: java.voidland.org", requestData.getQueryHost());	
	}
	
	@Test
	public void testGetHostNameMethod() throws FileNotFoundException, IOException {
		assertEquals("java.voidland.org", requestData.getHostName());	
	}
	
	@Test
	public void testGetExcepectedResponseMethod() throws FileNotFoundException, IOException {
		assertEquals("HTTP/1.1 200 OK", requestData.getExpectedResponse());	
	}
}
