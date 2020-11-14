package thread.deadlock.solution.same.order;

import java.util.Random;

public class TrainA extends Thread {

	private Intersection intersection;

	private Random random = new Random();

	public TrainA(Intersection intersection) {
		this.intersection = intersection;
	}

	@Override
	public void run() {
		while (true) {
			long sleepingTime = random.nextInt(5);
			try {
				Thread.sleep(sleepingTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			intersection.takeRoadA();
		}
	}

}
