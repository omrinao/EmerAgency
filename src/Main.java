import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println(getClass().getResource("fxml/mainView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("fxml/mainView.fxml"));
        primaryStage.setTitle("Emer-Agency");
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("css/ViewStyle.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
