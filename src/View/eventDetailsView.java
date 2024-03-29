package View;

import Models.Category;
import Models.Event;
import Models.Organization;
import Models.Update;
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
import java.util.ArrayList;

public class eventDetailsView extends AView {
    public TextField tf_title;
    public TextField tf_categories;
    public TextArea ta_description;
    public TextArea ta_org;
    public Button btn_back;

    private static ArrayList<String> details;

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

    /**
     * this method prepare the view for the current screen
     * @param mouseEvent
     */
    public void prepareView(MouseEvent mouseEvent){
        if(details != null && details.size() > 0 && tf_title != null && tf_title.getText().isEmpty()){
            tf_title.setText(details.get(0));
            tf_categories.setText(details.get(1));
            ta_description.setText(details.get(2));
            ta_org.setText(details.get(3));
        }
    }

    /**
     * this method sets the details of the user's chosen event to view them later
     * @param event
     */
    public static void setDetails(Event event){
        String categories = "";
        String description = "Updates: " + '\n';
        String org = "";
        for (Category c: event.get_categories()) {
            categories = categories + c.get_name() + ", ";
        }
        if (!categories.isEmpty())
            categories = categories.substring(0, categories.length() - 2);

        int updateNum = 1;
        for (Update u: event.get_updates()) {
            description = description + updateNum + ". " + u.get_description() + '\n';
            updateNum++;
        }
        for (Organization o: event.get_organizations().keySet()) {
            org = org + o.get_name() + ", ";
        }
        if (!org.isEmpty())
            org = org.substring(0, org.length() - 2);

        details = new ArrayList<>();
        details.add(event.get_title());
        details.add(categories);
        details.add(description);
        details.add(org);
    }
}
