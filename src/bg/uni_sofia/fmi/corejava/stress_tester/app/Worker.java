package bg.uni_sofia.fmi.corejava.stress_tester.app;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Worker implements Runnable {

	private NetworkStressTester nst;
	private CyclicBarrier cb;
	public static volatile boolean isBroken;

	public Worker(NetworkStressTester nst, CyclicBarrier cb) {
		this.nst = nst;
		this.cb = cb;
	}

	@Override
	public void run() {
		try {
			cb.await();
			nst.sendRequest();
		} catch (UnexpectedResponse | BrokenBarrierException  | IOException | InterruptedException e) {
			System.out.println("the response failed");
			isBroken = true;
		}

	}

}
