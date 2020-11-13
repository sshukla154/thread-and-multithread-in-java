package thread.race.condition.atomic.operation;

public class Metrics {

	private long count = 0;
	private volatile double average = 0.0;

	/*
	 * Many threads can add sample to the object of a matrix because average and
	 * count are going to be shared by multiple metrics objects. So we can say that
	 * addSample is not an atomic operation. So we have to protect this method from
	 * multiple thread execution simultaneously
	 */
	public synchronized void addSample(long sample) {
		double currentSum = average * count;
		count++;
		average = (currentSum + sample) / count;
	}

	/*
	 * Primitive types are atomic except double and long. So its not safe, so we can
	 * either use synchronized or volatile keyword
	 */
	public double getAverage() {
		return average;
	}

}
