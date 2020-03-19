

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Main extends Application{

	private Scene scene;
	private Pane root;


	/*
	 * Launch the fxml document, done with JavaFX
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			URL url = new File("src/View/view.fxml").toURI().toURL();
			loader.setLocation(url);
			System.out.println(loader.getLocation());
			Pane root = new Pane();
			root = loader.load();
			this.scene = new Scene(root,1200,830);
			primaryStage.setScene(scene);
			primaryStage.show();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}		

	public static void main(String[] args) {
		launch(args);
	}

}