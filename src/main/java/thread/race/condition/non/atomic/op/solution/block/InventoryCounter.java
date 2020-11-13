package thread.race.condition.non.atomic.op.solution.block;

public class InventoryCounter {

	private int items = 0;

	Object lock = new Object();

	public int getItems() {
		synchronized (lock) {
			return items;
		}
	}

	public synchronized void increment() {
		synchronized (lock) {
			items++;
		}
	}

	public synchronized void decrement() {
		synchronized (lock) {
			items--;
		}
	}

}
