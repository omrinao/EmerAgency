import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;
import Models.Model;
import View.AView;

public class Main extends Application {
    public static Stage pStage;

    @Override
    public void start(Stage primaryStage) throws Exception{


        pStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));


        pStage.setTitle("Emer-Agency");
        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("css/ViewStyle.css").toExternalForm());
        pStage.setScene(scene);
        pStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
