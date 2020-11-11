package thread.creation;

public class UsingThreadClass {

	public static void main(String[] args) throws InterruptedException {

		Thread thread = new NewThread();

		thread.start();

	}

	private static class NewThread extends Thread {
		@Override
		public void run() {
			System.out.println(
					"Thread 1 : Runnable.run() : " + this.getName() + ", having priority " + this.getPriority());
		}
	}

}
