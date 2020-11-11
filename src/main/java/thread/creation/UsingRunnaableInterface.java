package thread.creation;

import java.lang.Thread.UncaughtExceptionHandler;

public class UsingRunnaableInterface {

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Thread 1 : Runnable.run() : " + Thread.currentThread().getName()
						+ ", having priority " + Thread.currentThread().getPriority());
			}
		});

		System.out.println("main() : " + Thread.currentThread().getName()
				+ " before starting a new thread, having priority " + Thread.currentThread().getPriority());

		// This start() will instruct the JVM to create a new thread and pass it to the
		// operating system
		thread.start();

		System.out.println("main() : " + Thread.currentThread().getName()
				+ " after starting a new thread, having priority " + Thread.currentThread().getPriority());

		// Which hold the current executing thread for given no of time
		Thread.sleep(10000);

		Thread thread2 = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println("Thread 2 : Runnable.run() : " + Thread.currentThread().getName()
						+ ", having priority " + Thread.currentThread().getPriority());
			}
		});
		thread2.setName("RenamingThreadTwo");

		System.out.println("main() : " + Thread.currentThread().getName() + " before starting thread2, having priority "
				+ Thread.currentThread().getPriority());

		// Setting the static priority
		thread2.setPriority(Thread.MAX_PRIORITY);

		// This start() will instruct the JVM to create a new thread and pass it to the
		// operating system
		thread2.start();

		System.out.println("main() : " + Thread.currentThread().getName() + " after starting thread2, having priority "
				+ Thread.currentThread().getPriority());

		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				throw new RuntimeException("Intentional Exception");
			}
		});
		thread3.setName("MisbehavingThread");

		/*
		 * This handler will be called if an exception was thrown inside the Thread2 and
		 * was not caught anywhere
		 */
		thread3.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(
						"A critical error encountered in thread + " + t.getName() + " the error is " + e.getMessage());

			}
		});
		thread3.start();

	}

}
