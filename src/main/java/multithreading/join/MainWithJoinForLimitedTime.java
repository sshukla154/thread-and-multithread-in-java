package multithreading.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWithJoinForLimitedTime {

	public static void main(String[] args) throws InterruptedException {
		/*
		 * Suppose within all these numbers in a input list if we have a number which is
		 * very large and is blocking all the other number. So we need to decide until
		 * what time we are going to block other thread i.e. wait for the response
		 * 
		 */

		List<Long> inputNumbers = Arrays.asList(10000000000L, 3435L, 35435L, 2324L, 4645L, 23L, 5L, 5566L);

		List<FactorialThread> threads = new ArrayList<>();

		for (long inputNumber : inputNumbers) {
			threads.add(new FactorialThread(inputNumber));
		}

		for (FactorialThread thread : threads) {
			thread.start();
		}

		/*
		 * This makes the main thread to wait for 2 seconds
		 */
		for (FactorialThread thread : threads) {
			thread.join(2000);
		}

		for (int i = 0; i < inputNumbers.size(); i++) {
			FactorialThread factorialThread = threads.get(i);
			if (factorialThread.isFinished()) {
				System.out.println("Factorial of " + inputNumbers.get(i) + " is " + factorialThread.getResult());
			} else {
				System.out.println("The calculation for " + inputNumbers.get(i) + " is still in process.");
			}
		}
	}

}
