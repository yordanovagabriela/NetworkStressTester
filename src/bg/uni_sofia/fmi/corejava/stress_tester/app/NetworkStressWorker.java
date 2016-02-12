package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

public class NetworkStressWorker implements Runnable {

	private RequestsManager requestsManager;
	private CyclicBarrier barrier;
	private static AtomicLong longestResponseTime = new AtomicLong(0L);
	public static AtomicBoolean isBroken = new AtomicBoolean(false);;

	public NetworkStressWorker(RequestsManager requestsManager, CyclicBarrier barrier) {
		this.requestsManager = requestsManager;
		this.barrier = barrier;
	}

	public void sendRequest() {

		try {
			Long startTime = System.currentTimeMillis();

			requestsManager.sendRequest();

			Long responseTime = System.currentTimeMillis() - startTime;

			if (responseTime > longestResponseTime.longValue()) {
				longestResponseTime.set(responseTime);
			}

		} catch (UnexpectedResponse | IOException ex) {
			System.out.println("The request could not be sent.");
			System.out.println(ex.getMessage());
			isBroken.compareAndSet(false, true);
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
		return longestResponseTime.longValue();
	}

}
