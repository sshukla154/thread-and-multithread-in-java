package thread.race.condition.non.atomic.operation;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		InventoryCounter ic = new InventoryCounter();

		IncrementingThread it = new IncrementingThread(ic);
		DecrementingThread dt = new DecrementingThread(ic);

		/*
		 * Starting IT and waiting for it to be completed and then starting DT -----
		 * CORRECT RESULT
		 */
		it.start();
		it.join();

		dt.start();
		dt.join();

		System.out.println("1st thread complets and then 2nd starts - We currently have " + ic.getItems() + " items");

		IncrementingThread it1 = new IncrementingThread(ic);
		DecrementingThread dt1 = new DecrementingThread(ic);

		/* Starting both the thread IT and DT simultaneously ----- INCORRECT RESULT */
		it1.start();
		dt1.start();

		it1.join();
		dt1.join();

		System.out.println(
				"1st and 2nd both thread started simultaneously - We currently have " + ic.getItems() + " items");

	}

}
