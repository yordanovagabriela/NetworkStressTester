package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class NetworkStressTester {

	private RequestData requestData;
	private int port;
	private static final File LOG_FILE = new File("recourses/results.txt");
	
	public NetworkStressTester(int port, File requestFile) throws FileNotFoundException, IOException {
		this.requestData = new RequestData(requestFile);
		this.port = port;
	}

	public void sendRequest() {

		//Long start = new Long(System.currentTimeMillis());

		try (Socket s = new Socket(this.requestData.getHostName(), this.port);
				PrintWriter pw = new PrintWriter(s.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()))) {

			//send request
			pw.println(requestData.getQueryMethod());
			pw.println(requestData.getQueryHost());
			pw.println("");
			pw.flush();

			String requestStatus = br.readLine();

			//write in the LOG_FILE if the response is the expected
			if (requestStatus.equals(requestData.getExpectedResponse())) {

				System.out.println(requestStatus);
				//synchronize writing in the file, so only one thread can write at the same moment
//				synchronized (LOG_FILE) {
//					logResults.print(requestStatus);
//					logResults.print(" Time: " + (float) (System.currentTimeMillis() - start) / (float) 1000 + "sec");
//					logResults.println();
//				}
			} 
		}catch (IOException ex) {
			
		}

	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

