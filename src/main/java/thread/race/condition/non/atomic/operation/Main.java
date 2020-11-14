package thread.race.condition.non.atomic.operation;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		
		SharedClass sharedClass = new SharedClass();

		Thread threadsOne = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					sharedClass.checkForDataRace();
				}
			}
		});

		Thread threadsTwo = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100000; i++) {
					sharedClass.checkForDataRace();
				}
			}
		});

		threadsOne.start();
		threadsTwo.start();

	}

}
