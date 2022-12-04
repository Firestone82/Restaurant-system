package presentationLayer;

import dataLayer.connection.SQLDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import presentationLayer.controller.MainController;
import presentationLayer.enums.SceneType;

public class App extends Application {

	private MainController mainController;
	private Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;

		try {
			mainController = new MainController(this, primaryStage);
			mainController.changeScene(SceneType.LOGIN);

			primaryStage.resizableProperty().set(false);
//			primaryStage.getIcons().add(Source.ICON.getImage());
			primaryStage.setOnCloseRequest(this::exitProgram);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}

		SQLDatabase db = new SQLDatabase();
	}
	
	private void exitProgram(WindowEvent evt) {
		System.exit(0);
	}

	public MainController getMainController() {
		return mainController;
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public String getResourcePath() {
		return "src/main/resources";
	}
}