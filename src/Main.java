import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.*;
public class Main extends Application {

	public static void main(String[] args){
		Application.launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		AnchorPane mainPane = (AnchorPane) FXMLLoader.load(Main.class.getResource("Datamining!!!.fxml"));
		primaryStage.setScene(new Scene(mainPane));
		primaryStage.show();
	}

}
