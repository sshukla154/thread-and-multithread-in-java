package thread.interrupt;

public class UsingInterrupt {

	public static void main(String[] args) {

		Thread thread = new Thread(new BlockingTask());
		thread.start();
		
		// if have interrupt(), task will be executed as soon as encountered sleep() 
		thread.interrupt();

	}

	private static class BlockingTask implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				System.out.println("Exiting blocking thread");
			}
		}
	}

}
