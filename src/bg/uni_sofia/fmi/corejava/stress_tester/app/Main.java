package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
	private static int requests = 1;

	public static void start() throws FileNotFoundException, IOException, InterruptedException {

		RequestsManager requestManager = new RequestsManager(80, new File("recourses/request.txt"));

		while (true) {
			ExecutorService executor = Executors.newFixedThreadPool(requests);
			CyclicBarrier barrier = new CyclicBarrier(requests);
			NetworkStressWorker worker = new NetworkStressWorker(requestManager, barrier);

			System.out.printf("Trying to send %d requests to the server ... ", requests);

			for (int i = 0; i < requests; i++) {
				executor.execute(worker);
			}

			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

			if (NetworkStressWorker.isBroken) {
				break;
			}

			System.out.println("The requests were sent successfuly! \n");
			requests++;

		}

		System.out.printf("Maximum number of requests : %d \n", (requests - 1));
		System.out.printf("Longest time for response : %d ms \n", NetworkStressWorker.getLongestResponseTime());
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		start();
	}
}
