package View;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class postUpdateView extends AView {
    public ChoiceBox cb_category;
    public ChoiceBox cb_event;
    public TextArea ta_description;
    public Button btn_postUpdate;
    public Button btn_back;

    /**
     * method to set main screen
     * @param mouseEvent - mouse click on 'back' button on post update screen
     */
    public void set_back(MouseEvent mouseEvent) {
        Stage mainView = (Stage)btn_back.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading main screen
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/mainView.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load main screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }


    public void prepareView(MouseEvent mouseEvent){
        if (cb_category.getItems().size() == 0) {
            ArrayList<String> cat = _controller.getCategories();
            cb_category.getItems().addAll(cat);
        }
    }


    public void set_events(MouseEvent mouseEvent){
        if (!cb_category.getSelectionModel().getSelectedItem().toString().isEmpty()){
            if (cb_event.getItems().size() == 0) {
                String cat = "";
                cat = cb_category.getValue().toString();
                ArrayList<String> events = _controller.getEvents(cat, loginView.username);
                cb_event.getItems().addAll(events);
                cb_event.setDisable(false);
            }
        }
    }

    /**
     * method to send new event
     * @param mouseEvent - mouse click on 'back' button on create event screen
     */
    public void send_update(MouseEvent mouseEvent) {
        String category = cb_category.getValue().toString();
        String title = cb_event.getValue().toString();
        String description = ta_description.getText();

        if (title.isEmpty() || description.isEmpty() || category.isEmpty()){
            popProblem("You need to fill in all fields\n");
            return;
        }
        String response = _controller.post_update(category, title, description);
        if (!response.equals("success")) {
            popProblem("Create event failed!\n" +
                    "Make sure you typed in proper details");
            return;
        }
        else {
            popInfo("Update Posted Successfully!");
        }

        Stage mainView = (Stage)btn_back.getScene().getWindow();
        mainView.close();
        try {
            Stage createStage = new Stage();
            createStage.setTitle("Emer-Agency");
            //loading main screen
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/mainView.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load post update screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }
}
