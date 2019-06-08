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

    public ChoiceBox cb_event;
    public TextArea ta_description;
    public Button btn_postUpdate;
    public Button btn_back;
    public CheckBox cb_c1;
    public CheckBox cb_c2;
    public CheckBox cb_c3;
    public CheckBox cb_c4;
    public CheckBox cb_c5;

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
        if (cb_c1.getText().isEmpty()) {
            ArrayList<String> cat = _controller.getCategories();
            cb_c1.setText(cat.get(0));
            cb_c2.setText(cat.get(1));
            cb_c3.setText(cat.get(2));
            cb_c4.setText(cat.get(3));
            cb_c5.setText(cat.get(4));
        }
    }


    public void set_events(MouseEvent mouseEvent){
        ArrayList<String> events = new ArrayList<>();
        if (cb_c1.isSelected()){
            events.removeAll(_controller.getEvents(cb_c1.getText(), loginView.username));
            events.addAll(_controller.getEvents(cb_c1.getText(), loginView.username));
        }
        if (cb_c2.isSelected()){
            events.removeAll(_controller.getEvents(cb_c2.getText(), loginView.username));
            events.addAll(_controller.getEvents(cb_c2.getText(), loginView.username));
        }
        if (cb_c3.isSelected()){
            events.removeAll(_controller.getEvents(cb_c3.getText(), loginView.username));
            events.addAll(_controller.getEvents(cb_c3.getText(), loginView.username));
        }
        if (cb_c4.isSelected()){
            events.removeAll(_controller.getEvents(cb_c4.getText(), loginView.username));
            events.addAll(_controller.getEvents(cb_c4.getText(), loginView.username));
        }
        if (cb_c5.isSelected()){
            events.removeAll(_controller.getEvents(cb_c5.getText(), loginView.username));
            events.addAll(_controller.getEvents(cb_c5.getText(), loginView.username));
        }
        if (!cb_c1.isSelected()){
            events.removeAll(_controller.getEvents(cb_c1.getText(), loginView.username));
        }
        if (!cb_c2.isSelected()){
            events.removeAll(_controller.getEvents(cb_c2.getText(), loginView.username));
        }
        if (!cb_c3.isSelected()){
            events.removeAll(_controller.getEvents(cb_c3.getText(), loginView.username));
        }
        if (!cb_c4.isSelected()){
            events.removeAll(_controller.getEvents(cb_c4.getText(), loginView.username));
        }
        if (!cb_c5.isSelected()){
            events.removeAll(_controller.getEvents(cb_c5.getText(), loginView.username));
        }

        cb_event.getItems().clear();

        if (events.size() > 0){
            cb_event.getItems().addAll(events);
            cb_event.setDisable(false);
        }
        else{
            cb_event.setDisable(true);
        }
    }

    /**
     * method to send new event
     * @param mouseEvent - mouse click on 'back' button on create event screen
     */
    public void send_update(MouseEvent mouseEvent) {
        ArrayList<String> categories = new ArrayList<>();
        if (cb_c1.isSelected()){
            categories.add(cb_c1.getText());
        }
        if (cb_c2.isSelected()){
            categories.add(cb_c2.getText());
        }
        if (cb_c3.isSelected()){
            categories.add(cb_c3.getText());
        }
        if (cb_c4.isSelected()){
            categories.add(cb_c4.getText());
        }
        if (cb_c5.isSelected()){
            categories.add(cb_c5.getText());
        }
        String title = "";

        if (cb_event != null && cb_event.getValue() != null) {
            title = cb_event.getValue().toString();
        }
        String description = ta_description.getText();

        if (title.isEmpty() || description.isEmpty()){
            popProblem("You need to fill in all fields\n");
            return;
        }


        String response = _controller.post_update(categories, title, description);
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
