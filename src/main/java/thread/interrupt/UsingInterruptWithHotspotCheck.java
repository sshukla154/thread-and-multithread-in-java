package thread.interrupt;

import java.math.BigInteger;

public class UsingInterruptWithHotspotCheck {

	public static void main(String[] args) {

//		Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("10")));
		Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("5")));

		/*
		 * This daemon thread will terminate the main thread even if the calculation
		 * thread is not terminated
		 */
		thread.setDaemon(true);
		thread.start();
		thread.interrupt();

	}

	private static class LongComputationTask implements Runnable {

		private BigInteger base;
		private BigInteger power;

		public LongComputationTask(BigInteger base, BigInteger power) {
			this.base = base;
			this.power = power;

		}

		@Override
		public void run() {
			System.out.println(base + "^" + power + "=" + pow(base, power));
		}

		private BigInteger pow(BigInteger base, BigInteger power) {
			BigInteger result = BigInteger.ONE;
			for (BigInteger i = BigInteger.ZERO; i.compareTo(power) != 0; i = i.add(BigInteger.ONE)) {
				result = result.multiply(base);
			}
			return result;
		}
	}
}