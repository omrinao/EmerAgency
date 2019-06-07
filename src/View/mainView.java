package View;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class mainView extends AView {

    public Button btn_createEvent;
    public Button btn_postUpdate;
    public Button btn_viewEvent;
    public Button btn_logout;

    /**
     * method to set create event screen
     * @param mouseEvent - mouse click on 'create event' button on main screen
     */
    public void set_create(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_createEvent.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading create view
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/createEvent.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load create event interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }

    /**
     * method to set post update to event screen
     * @param mouseEvent - mouse click on 'post update' button on main screen
     */
    public void set_update(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_createEvent.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading post update view
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/postUpdate.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load post update interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }

    /**
     * method to set view event screen
     * @param mouseEvent - mouse click on 'view event' button on main screen
     */
    public void set_viewEvent(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_createEvent.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading event view screen
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/viewEvent.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load view event interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }

    /**
     * method to set login screen
     * @param mouseEvent - mouse click on 'logout' button on main screen
     */
    public void set_logout(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_createEvent.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading login screen
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load login interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }
}
