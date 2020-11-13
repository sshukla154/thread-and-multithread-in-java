package memory.management.stack;

public class Main {

	public static void main(String[] args) {
		int x = 1;
		int y = 2;
		int result = sum(x, y);
		System.out.println(result);
	}

	private static int sum(int a, int b) {
		int sum = a + b;
		return sum;

	}
}
