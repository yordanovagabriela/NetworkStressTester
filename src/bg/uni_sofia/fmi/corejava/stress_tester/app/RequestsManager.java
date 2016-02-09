package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RequestsManager {

	private RequestData requestData;
	private int port;

	public RequestsManager(int port, File requestFile) throws FileNotFoundException, IOException {
		this.requestData = new RequestData(requestFile);
		this.port = port;
	}

	public void sendRequest() throws UnexpectedResponse, IOException {

		//create Socket to communicate with the server
		//PrintWriter - to send messages to the server
		//BufferedReader - to get messages/response from the server
		try (Socket s = new Socket(this.requestData.getHostName(), this.port);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

			// send request
			pw.println(requestData.getQueryMethod());
			pw.println(requestData.getQueryHost());
			pw.println("");
			pw.flush();
			
			String requestStatus = br.readLine();
			
			//verify the response message
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
	
	public RequestData getRequestData() {
		return this.requestData;
	}

}
