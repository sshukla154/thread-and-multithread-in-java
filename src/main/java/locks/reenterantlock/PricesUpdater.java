package locks.reenterantlock;

import java.util.Random;

public class PricesUpdater extends Thread {

	private PricesContainer pricesContainer;

	private Random random = new Random();

	public PricesUpdater(PricesContainer pricesContainer) {
		this.pricesContainer = pricesContainer;
	}

	@Override
	public void run() {
		while (true) {
			pricesContainer.getLockObject().lock();
			// This sleep() is to stimulate the server api call response
			try {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pricesContainer.setBitcoinPrice(random.nextInt(20000));
				pricesContainer.setEtherPrice(random.nextInt(2000));
				pricesContainer.setLitecoinPrice(random.nextInt(500));
				pricesContainer.setBitcoinCashPrice(random.nextInt(5000));
				pricesContainer.setRipplePrice(random.nextDouble());

			} finally {
				pricesContainer.getLockObject().unlock();
			}
			// This sleep() is to stimulate the server api call response
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
