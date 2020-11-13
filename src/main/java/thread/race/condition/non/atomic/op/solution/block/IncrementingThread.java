package thread.race.condition.non.atomic.op.solution.block;

public class IncrementingThread extends Thread {

	private InventoryCounter inventoryCounter;

	public IncrementingThread(InventoryCounter ic) {
		this.inventoryCounter = ic;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10000; i++) {
			inventoryCounter.increment();
		}
	}

}
