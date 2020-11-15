package locks.reenterantlock;

import java.util.HashMap;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.animation.FillTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Cryptocurrency Prices");

		GridPane grid = createGrid();

		Map<String, Label> cryptoLabel = createCryptoPricelabels();

		addLabelsToGrid(cryptoLabel, grid);

		double width = 300;
		double height = 250;

		StackPane root = new StackPane();

		Rectangle background = createBackgroundRectangleWithAnimation(width, height);

		root.getChildren().add(background);
		root.getChildren().add(grid);

		primaryStage.setScene(new Scene(root, width, height));

		PricesContainer pricesContainer = new PricesContainer();

		PricesUpdater pricesUpdater = new PricesUpdater(pricesContainer);

		AnimationTimer animationTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				/*
				 * Instead of using if (pricesContainer.getLockObject().tryLock()) check with
				 * conventional lock approach in AnimationTimer
				 */
				if (pricesContainer.getLockObject().tryLock()) {
					try {
						Label bitcoinLabel = cryptoLabel.get("BTC");
						bitcoinLabel.setText(String.valueOf(pricesContainer.getBitcoinPrice()));

						Label etherLabel = cryptoLabel.get("ETH");
						etherLabel.setText(String.valueOf(pricesContainer.getEtherPrice()));

						Label litecoingLabel = cryptoLabel.get("LTH");
						litecoingLabel.setText(String.valueOf(pricesContainer.getLitecoinPrice()));

						Label bitcoinCashLabel = cryptoLabel.get("BCH");
						bitcoinCashLabel.setText(String.valueOf(pricesContainer.getBitcoinCashPrice()));

						Label rippleLabel = cryptoLabel.get("XRP");
						rippleLabel.setText(String.valueOf(pricesContainer.getRipplePrice()));

					} finally {
						pricesContainer.getLockObject().unlock();
						;
					}
				}

			}
		};
		animationTimer.start();
		pricesUpdater.start();
		primaryStage.show();

	}

	private Rectangle createBackgroundRectangleWithAnimation(double width, double height) {
		Rectangle background = new Rectangle(width, height);

		FillTransition fillTransition = new FillTransition(Duration.millis(1000), background, Color.LIGHTGREEN,
				Color.LIGHTBLUE);
		fillTransition.setCycleCount(Timeline.INDEFINITE);
		fillTransition.setAutoReverse(true);
		fillTransition.play();
		return background;
	}

	private void addLabelsToGrid(Map<String, Label> cryptoLabel, GridPane grid) {
		int row = 0;

		for (Map.Entry<String, Label> entry : cryptoLabel.entrySet()) {
			String cryptoName = entry.getKey();
			Label labelName = new Label(cryptoName);
			labelName.setTextFill(Color.BLUE);
			labelName.setOnMousePressed(event -> labelName.setTextFill(Color.RED));
			labelName.setOnMouseReleased((EventHandler) event -> labelName.setTextFill(Color.BLUE));

			grid.add(labelName, 0, row);
			grid.add(entry.getValue(), 1, row);

			row++;
		}

	}

	private Map<String, Label> createCryptoPricelabels() {
		Label bitcoinPrice = new Label("0");
		bitcoinPrice.setId("BTC");

		Label etherPrice = new Label("0");
		etherPrice.setId("ETH");

		Label litecoinPrice = new Label("0");
		litecoinPrice.setId("LTC");

		Label bitcoinCashPrice = new Label("0");
		bitcoinCashPrice.setId("BCH");

		Label ripplePrice = new Label("0");
		ripplePrice.setId("XRP");

		Map<String, Label> cryptolabelMap = new HashMap<>();
		cryptolabelMap.put("BTC", bitcoinPrice);
		cryptolabelMap.put("ETH", etherPrice);
		cryptolabelMap.put("LTC", litecoinPrice);
		cryptolabelMap.put("BCH", bitcoinCashPrice);
		cryptolabelMap.put("XRP", ripplePrice);

		return cryptolabelMap;
	}

	private GridPane createGrid() {
		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setAlignment(Pos.CENTER);
		return gridPane;
	}

}
