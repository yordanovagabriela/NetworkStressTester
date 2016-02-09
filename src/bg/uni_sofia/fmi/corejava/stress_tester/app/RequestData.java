package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RequestData {
	private String queryMethod;
	private String queryHost;
	private String hostName;
	private static final String expectedResponse = "HTTP/1.1 200 OK";

	public RequestData(File requestFile) throws FileNotFoundException, IOException {

		try (BufferedReader requestReader = new BufferedReader(new FileReader(requestFile));) {

			this.queryMethod = requestReader.readLine();
			this.queryHost = requestReader.readLine();
			this.hostName = this.queryHost.substring((queryHost.indexOf(" ") + 1));

		}
	}

	public String getQueryMethod() {
		return queryMethod;
	}

	public String getQueryHost() {
		return queryHost;
	}

	public String getExpectedResponse() {
		return expectedResponse;
	}

	public String getHostName() {
		return hostName;
	}


}

