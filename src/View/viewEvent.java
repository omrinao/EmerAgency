package View;

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

public class viewEvent extends AView {
    public CheckBox cb_c1;
    public CheckBox cb_c2;
    public CheckBox cb_c3;
    public CheckBox cb_c4;
    public CheckBox cb_c5;
    public ChoiceBox cb_event;
    public Button btn_viewEvent;
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
            events.addAll(_controller.getEvents(cb_c1.getText(), loginView.username));
        }
        if (cb_c2.isSelected()){
            events.addAll(_controller.getEvents(cb_c2.getText(), loginView.username));
        }
        if (cb_c3.isSelected()){
            events.addAll(_controller.getEvents(cb_c3.getText(), loginView.username));
        }
        if (cb_c4.isSelected()){
            events.addAll(_controller.getEvents(cb_c4.getText(), loginView.username));
        }
        if (cb_c5.isSelected()){
            events.addAll(_controller.getEvents(cb_c5.getText(), loginView.username));
        }

        if (events.size() > 0){
            cb_event.getItems().addAll(events);
            cb_event.setDisable(false);
        }
    }


}
