package thread.race.condition.solution.block;

public class DecrementingThread extends Thread {

	private InventoryCounter inventoryCounter;

	public DecrementingThread(InventoryCounter ic) {
		this.inventoryCounter = ic;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			inventoryCounter.decrement();
		}
	}

}
