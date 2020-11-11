package thread.interrupt;

import java.math.BigInteger;

public class UsingInterruptWithDemon {

	public static void main(String[] args) {

//		Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("10")));
		Thread thread = new Thread(new LongComputationTask(new BigInteger("2"), new BigInteger("5")));
		thread.start();
		/*
		 * In this interrupt() also wont work as interrupt() is send but we don't have
		 * any logic or method to handle it. For this we need to find the hot point(in
		 * our case its the for loop which is taking longer time )
		 */
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
				/* This if condition is use to work with interrupt() */
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("Prematurely interrupted computation.");
					return BigInteger.ZERO;
				}
				result = result.multiply(base);
			}
			return result;
		}
	}
}
