package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class eventDetailsView extends AView {
    public TextField tf_title;
    public TextField tf_categories;
    public TextArea ta_description;
    public TextArea ta_org;
    public Button btn_back;

    /**
     * method to set event view screen
     * @param mouseEvent - mouse click on 'back' button on event details screen
     */
    public void set_back(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_back.getScene().getWindow();
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
            popProblem("Error while trying to load event view screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }
}
