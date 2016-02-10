package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class NetworkStressWorker implements Runnable {

	private RequestsManager requestsManager;
	private CyclicBarrier barrier;
	private static Long longestResponseTime = 0L;
	public static volatile boolean isBroken;

	public NetworkStressWorker(RequestsManager requestsManager, CyclicBarrier barrier) {
		this.requestsManager = requestsManager;
		this.barrier = barrier;
	}

	public void sendRequest() {

		try {
			// the time right before the request is sent
			Long startTime = System.currentTimeMillis();

			requestsManager.sendRequest();

			// the time after the request is sent
			Long elapsedTime = System.currentTimeMillis();

			// synchronize on longestReponseTime so only one thread can change
			// it at a specific time
			synchronized (longestResponseTime) {
				Long responseTime = elapsedTime - startTime;
				if (responseTime > longestResponseTime) {
					longestResponseTime = responseTime;
				}
			}
		} catch (UnexpectedResponse | IOException ex) {
			System.out.println("The request could not be sent.");
			System.out.println(ex.getMessage());

			isBroken = true;
		}

	}

	@Override
	public void run() {
		try {
			barrier.await();
			this.sendRequest();
		} catch (BrokenBarrierException | InterruptedException ex) {
			ex.printStackTrace();
		}

	}

	public static Long getLongestResponseTime() {
		return longestResponseTime;
	}

}
