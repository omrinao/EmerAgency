package View;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;


public class createView extends AView {

    public TextField tf_eventTitle;
    public TextArea ta_description;
    public Button btn_create;
    public Button btn_back;
    public CheckBox cb_c1;
    public CheckBox cb_c2;
    public CheckBox cb_c3;
    public CheckBox cb_c4;
    public CheckBox cb_c5;
    public CheckBox cb_police;
    public CheckBox cb_mda;
    public CheckBox cb_firefighter;


    /**
     * method to set main screen
     * @param mouseEvent - mouse click on 'back' button on create event screen
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

            mainView viewController = fxmlLoader.getController();
            if (!loginView.userObject.get_organiztion().get_name().equals("Moked"))
                viewController.btn_createEvent.setDisable(true);

            createStage.setScene(scene);
            createStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load main screen interface\n" + e.getMessage());
        }
        mouseEvent.consume();

    }

    /**
     * method to send new event
     * @param mouseEvent - mouse click on 'back' button on create event screen
     */
    public void send_create(MouseEvent mouseEvent) {
        String title = tf_eventTitle.getText();
        String description = ta_description.getText();
        if (title.isEmpty() || description.isEmpty() || (!cb_c1.isSelected() && !cb_c2.isSelected() && !cb_c3.isSelected() && !cb_c4.isSelected() && !cb_c5.isSelected()) ||
                (!cb_firefighter.isSelected() && !cb_mda.isSelected() && !cb_police.isSelected())){
            popProblem("You need to fill in all fields\n");
            return;
        }

        ArrayList<String> categories = new ArrayList<>();
        ArrayList<String> org = new ArrayList<>();
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
        if (cb_police.isSelected()){
            org.add("Police");
        }
        if (cb_mda.isSelected()){
            org.add("MDA");
        }
        if (cb_firefighter.isSelected()){
            org.add("Fire Fighters");
        }

        String response = _controller.create_event(title, categories, description, org);
        if (!response.equalsIgnoreCase("success")) {
            popProblem("Create event failed!\n" +
                    "Make sure you typed in proper details");
            return;
        }

        else {
            popInfo("Event Created Successfully!");
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
}

