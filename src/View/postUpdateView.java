package View;

import Controller.Controller;
import Models.Category;
import Models.Event;
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
import java.util.List;
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

    private List<Event> _events;

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
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/mainView.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);

            mainView viewController = fxmlLoader.getController();
            if (!loginView.userObject.get_organiztion().get_name().equals("Moked"))
                viewController.btn_createEvent.setDisable(true);

            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load main screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }


    /**
     * this method prepare the view for the current screen
     * @param mouseEvent
     */
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


    /**
     * this method sets the events according to the user's categories selection
     * @param mouseEvent
     */
    public void set_events(MouseEvent mouseEvent){
        ArrayList<Category> cat = new ArrayList<Category>();

        if (cb_c1.isSelected()){
            cat.add(Controller._categories.get(0));
        }
        if (cb_c2.isSelected()){
            cat.add(Controller._categories.get(1));
        }
        if (cb_c3.isSelected()){
            cat.add(Controller._categories.get(2));
        }
        if (cb_c4.isSelected()){
            cat.add(Controller._categories.get(3));
        }
        if (cb_c5.isSelected()){
            cat.add(Controller._categories.get(4));
        }

        _events = new ArrayList<>(_controller.getEvents(cat, loginView.userObject));
        cb_event.getItems().clear();

        if (_events.size() > 0){
            for (Event event : _events) {
                cb_event.getItems().add("(" + event.get_id() + ")    " + event.get_title());
            }
            cb_event.setDisable(false);
        }
        else{
            cb_event.setDisable(true);
        }

        mouseEvent.consume();
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
        Event eventChosen = new Event();
        if (title.isEmpty() || description.isEmpty()){
            popProblem("You need to fill in all fields\n");
            return;
        }
        else{
            for (Event e: _events) {
                if (e.get_title().equals(title))
                    eventChosen = e;
            }
        }

        String response = _controller.post_update(eventChosen, description);
        if (!response.equals("success")) {
            popProblem("Create event failed!\n" +
                    "Make sure you typed in proper details");
            return;
        }
        else {
            popInfo("Update Posted Successfully!");
            cb_event.getItems().clear();
            cb_event.setDisable(true);
            cb_c1.setSelected(false);
            cb_c2.setSelected(false);
            cb_c3.setSelected(false);
            cb_c4.setSelected(false);
            cb_c5.setSelected(false);
            ta_description.setText("");
        }
        mouseEvent.consume();

    }


    private Event getEventById(int id) {
        for (Event e : _events){
            if (e.get_id() == id)
                return e;
        }
        return null;
    }
}
