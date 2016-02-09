package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkStressTester {

	private RequestData requestData;
	private int port;

	public NetworkStressTester(int port, File requestFile) throws FileNotFoundException, IOException {
		this.requestData = new RequestData(requestFile);
		this.port = port;
	}

	public void sendRequest() throws UnexpectedResponse, IOException {

		//long startTime = System.currentTimeMillis();

		try (Socket s = new Socket(this.requestData.getHostName(), this.port);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

			// send request
			pw.println(requestData.getQueryMethod());
			pw.println(requestData.getQueryHost());
			pw.println("");
			pw.flush();
			
			//long elapsedTime = System.currentTimeMillis() - startTime;
			
			String requestStatus = br.readLine();
			if (requestStatus.equals(requestData.getExpectedResponse())) {
				System.out.println(requestStatus);
			} else {
				throw new UnexpectedResponse("The response was unexpected!");
			}
		}

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}
