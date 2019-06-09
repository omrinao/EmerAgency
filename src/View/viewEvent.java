package View;

import Models.Category;
import Models.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class viewEvent extends AView {
    public CheckBox cb_c1;
    public CheckBox cb_c2;
    public CheckBox cb_c3;
    public CheckBox cb_c4;
    public CheckBox cb_c5;
    public ChoiceBox cb_event;
    public Button btn_viewEvent;
    public Button btn_back;

    //event details
    public TextField tf_title;
    public TextField tf_categories;
    public TextArea ta_description;
    public TextArea ta_org;
    public Button btn_backDetails;

    private ArrayList<String> details;


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
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<Category> cat = new ArrayList<Category>();

        if (cb_c1.isSelected()){
            cat.add(_controller._categories.get(0));
        }
        if (cb_c2.isSelected()){
            cat.add(_controller._categories.get(1));
        }
        if (cb_c3.isSelected()){
            cat.add(_controller._categories.get(2));
        }
        if (cb_c4.isSelected()){
            cat.add(_controller._categories.get(3));
        }
        if (cb_c5.isSelected()){
            cat.add(_controller._categories.get(4));
        }

        events.addAll(_controller.getEvents(cat, loginView.userObject));
        cb_event.getItems().clear();

        if (events.size() > 0){
            for (int i = 0; i < events.size(); i++){
                cb_event.getItems().add(events.get(i).get_title());
            }
            cb_event.setDisable(false);
        }
        else{
            cb_event.setDisable(true);
        }
    }


    /**
     * method to set view event details screen
     * @param mouseEvent - mouse click on 'view event details' button on view event screen
     */
    public void set_viewEvent(MouseEvent mouseEvent) {

        String event = cb_event.getValue().toString();
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
        ArrayList<String> eventDetails = new ArrayList<>();//_controller.getEventDetails(categories, event);

        Stage mainView = (Stage)btn_back.getScene().getWindow();
        try {
            Stage createStage = mainView;
            createStage.setTitle("Emer-Agency");
            //loading main screen
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/eventDetails.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("../css/ViewStyle.css").toExternalForm());
            createStage.setScene(scene);
            details = new ArrayList<>();
            for (int i = 0; i < eventDetails.size(); i++){
                details.add(eventDetails.get(i));
            }
            createStage.show();
            eventDetailsView.setDetails(details);

        } catch (IOException e) {
            popProblem("Error while trying to load main screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }

}
