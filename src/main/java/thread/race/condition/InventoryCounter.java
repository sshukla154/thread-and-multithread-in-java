package thread.race.condition;

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
