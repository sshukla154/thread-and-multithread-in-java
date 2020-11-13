package thread.race.condition.non.atomic.op.solution.method;

public class InventoryCounter {

	private int items = 0;

	public int getItems() {
		return items;
	}

	public synchronized void increment() {
		items++;
	}

	public synchronized void decrement() {
		items--;
	}

}
