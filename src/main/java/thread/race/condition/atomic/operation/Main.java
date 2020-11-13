package thread.race.condition.atomic.operation;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Metrics metrics = new Metrics();
		BusinessLogic businessLogicThread1 = new BusinessLogic(metrics);
		BusinessLogic businessLogicThread2 = new BusinessLogic(metrics);
		MetricsPrinter metricsPrinter = new MetricsPrinter(metrics);
		businessLogicThread1.start();
		businessLogicThread2.start();
		metricsPrinter.start();
				
	}

}
