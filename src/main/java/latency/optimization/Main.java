package latency.optimization;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class Main {

	public static final String SOURCE_FILE = "src/main/resources/latency-optimization/input-images/many-flowers.jpg";
	public static final String SINGLE_THREAD_DESTINATION_FILE = "src/main/resources/latency-optimization/output-images/single/many-flowers.jpg";
	public static final String MULTI_THREAD_DESTINATION_FILE = "src/main/resources/latency-optimization/output-images/multi/many-flowers.jpg";

	public static void main(String[] args) throws IOException, InterruptedException {

		BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
		BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(),
				BufferedImage.TYPE_INT_RGB);
		long singleStartTime = System.currentTimeMillis();
		recolorSingleThreaded(originalImage, resultImage);
		long singleEndTime = System.currentTimeMillis();
		long singleDuration = singleEndTime - singleStartTime;
		File singleOutputFile = new File(SINGLE_THREAD_DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", singleOutputFile);
		System.out.println("Single Threaded Duration : " + String.valueOf(singleDuration));

		long multiStartTime = System.currentTimeMillis();
		int noOfThreads = 2;
		recolorMultiThreaded(originalImage, resultImage, noOfThreads);
		long multiEndTime = System.currentTimeMillis();
		long multiDuration = multiEndTime - multiStartTime;
		File multiOutputFile = new File(MULTI_THREAD_DESTINATION_FILE);
		ImageIO.write(resultImage, "jpg", multiOutputFile);
		System.out.println("Multi Threaded Duration : " + String.valueOf(multiDuration));

	}

	private static void recolorMultiThreaded(BufferedImage originalImage, BufferedImage resultImage,
			int numberOfThreads) throws InterruptedException {

		List<Thread> threads = new ArrayList<Thread>();
		int width = originalImage.getWidth();
		int height = originalImage.getHeight() / numberOfThreads;

		for (int i = 0; i < numberOfThreads; i++) {
			final int threadMultiplier = i;

			Thread thread = new Thread(new Runnable() {
				public void run() {
					int leftCorner = 0;
					int topCorner = height * threadMultiplier;
					recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
				}
			});
			threads.add(thread);
		}

		for (Thread thread : threads) {
			thread.start();
		}

		for (Thread thread : threads) {
			thread.join();
		}

	}

	public static void recolorSingleThreaded(BufferedImage originalImage, BufferedImage resultImage) {
		recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(), originalImage.getHeight());
	}

	public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner,
			int topCorner, int width, int height) {

		for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
			for (int y = topCorner; y < topCorner + height && x < originalImage.getHeight(); y++) {
				recolorPixel(originalImage, resultImage, x, y);
			}
		}

	}

	public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
		int rgb = originalImage.getRGB(x, y);
		int red = getRed(rgb);
		int green = getGreen(rgb);
		int blue = getBlue(rgb);
		int newRed, newGreen, newBlue;

		if (isShadeOfGrey(red, green, blue)) {
			newRed = Math.min(255, red + 10);
			newGreen = Math.max(0, green - 80);
			newBlue = Math.max(0, blue - 20);
		} else {
			newRed = red;
			newGreen = green;
			newBlue = blue;
		}

		int newRGB = createRGBFromColors(newRed, newGreen, newBlue);
		setRGB(resultImage, x, y, newRGB);

	}

	public static void setRGB(BufferedImage image, int x, int y, int rgb) {
		image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
	}

	public static boolean isShadeOfGrey(int red, int green, int blue) {
		return Math.abs(red - green) < 30 && Math.abs(red - blue) < 30 && Math.abs(green - blue) < 30;
	}

	/* Takes individual RGB value and combines */
	public static int createRGBFromColors(int red, int green, int blue) {
		int rgb = 0;

		rgb |= blue;
		rgb |= green << 8;
		rgb |= red << 16;
		rgb |= 0xFF000000;

		return rgb;
	}

	/* Takes combined RGB value and extracts only red value */
	public static int getRed(int rgb) {
		/*
		 * Which will mask out Alpha, green and blue component and shifting the red
		 * value 2 bytes (16 Bits) to the right
		 */
		return (rgb & 0x00FF0000) >> 16;
	}

	/* Takes combined RGB value and extracts only green value */
	public static int getGreen(int rgb) {
		/*
		 * Which will mask out Alpha, red and blue component. As green is the second
		 * bite from the right we need to shift the value 8 bites to the right
		 */
		return (rgb & 0x0000FF00) >> 8;
	}

	/* Takes combined RGB value and extracts only blue value out of pixel */
	public static int getBlue(int rgb) {
		/*
		 * Applying bit mask on the pixel, making all the component ZERO except right
		 * most bite which is exactly the blue component
		 */
		return rgb & 0x000000FF;
	}

}
