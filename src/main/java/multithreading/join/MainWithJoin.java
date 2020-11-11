package multithreading.join;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainWithJoin {

	public static void main(String[] args) throws InterruptedException {
		/* We need to calculate 0!, 3435!, 35435!, 2324!, 4645!, 23!, 2435!, 5566! */
		/*
		 * In Factorial lot of multiplication is involved and it is CPU incentive task,
		 * so for each factorial calculation we will start an explicit thread
		 * 
		 */
		List<Long> inputNumbers = Arrays.asList(0L, 3435L, 35435L, 2324L, 4645L, 23L, 5L, 5566L);

		List<FactorialThread> threads = new ArrayList<>();

		for (long inputNumber : inputNumbers) {
			threads.add(new FactorialThread(inputNumber));
		}

		for (FactorialThread thread : threads) {
			thread.start();
		}

		/*
		 * This makes the main thread to wait until all the factorial thread is finished
		 */
		for (FactorialThread thread : threads) {
			thread.join();
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
