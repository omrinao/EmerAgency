package Controller;

import Models.Category;
import Models.Organization;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Controller.Controller;
import Models.Model;
import View.AView;

import java.util.List;

public class Main extends Application {
    public static Stage pStage;
    public static List<Organization> _organizations;
    public static List<Category> _categories;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Model m = new Model();
        _organizations = m.getOrganizations();
        _categories = m.getCategories();
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
