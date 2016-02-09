package bg.uni_sofia.fmi.corejava.stress_tester.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import bg.uni_sofia.fmi.corejava.stress_tester.app.RequestData;
import bg.uni_sofia.fmi.corejava.stress_tester.app.RequestsManager;

public class RequestsManagerTests {
	
	private int port;
	private RequestsManager requestsManager;
	private RequestData requestData;
	
	@Before
	public void setUp() throws FileNotFoundException, IOException {
		port = 80;
		requestsManager = new RequestsManager(port, new File("recourses/request.txt"));
		requestData = requestsManager.getRequestData();
	}
	
	@Test
	public void testGetPortMethod() {
		assertEquals(80, requestsManager.getPort());
	}
	
	@Test
	public void testGetRequestDataMethod() {
		assertEquals(requestData, requestsManager.getRequestData());
	}

}
