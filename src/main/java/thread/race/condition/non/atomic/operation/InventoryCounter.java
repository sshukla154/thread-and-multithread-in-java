package thread.race.condition.non.atomic.operation;

public class InventoryCounter {

	private int items = 0;

	public int getItems() {
		return items;
	}

	public void increment() {
		items++;
	}

	public void decrement() {
		items--;
	}

}
