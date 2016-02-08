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

		NetworkStressTester nst = new NetworkStressTester(80, new File("recourses/request.txt"));
		
		while (true) {
			ExecutorService executor = Executors.newFixedThreadPool(requests);
			CyclicBarrier cb = new CyclicBarrier(requests);
			Worker w = new Worker(nst, cb);

			for (int i = 0; i < requests; i++) {
				executor.execute(w);
			}

			executor.shutdown();
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			if (Worker.isBroken) {
				break;
			}
			requests++;

			// for (int i = 0; i < 300; i++) {
			// Runnable worker = new Worker(nst, cb);
			// Thread t = new Thread(worker);
			// t.start();
			// }
		}

		System.out.println(requests);
	}

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
		start();
	}
}
