/**
 * 
 */
package thread.race.condition.atomic.operation;

public class MetricsPrinter extends Thread {

	private Metrics metrics;

	public MetricsPrinter(Metrics metrics) {
		this.metrics = metrics;// TODO Auto-generated constructor stub
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			double currentAverage = metrics.getAverage();
			System.out.println("Current Average is : " + currentAverage);
		}
	}

}
