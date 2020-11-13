package thread.race.condition.atomic.operation;

import java.util.Random;

public class BusinessLogic extends Thread {

	private Metrics metrics;
	private Random random = new Random();

	public BusinessLogic(Metrics metrics) {
		this.metrics = metrics;
	}

	@Override
	public void run() {
		while (true) {
			long startTime = System.currentTimeMillis();
			try {
				Thread.sleep(random.nextInt(10));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			long endTime = System.currentTimeMillis();
			metrics.addSample(endTime - startTime);
		}
	}

}
