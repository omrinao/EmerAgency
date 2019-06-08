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

    public void prepareView(MouseEvent mouseEvent){
        if(details != null && details.size() > 0 && tf_title != null && tf_title.getText().isEmpty()){
            tf_title.setText(details.get(0));
            tf_categories.setText(details.get(1));
            ta_description.setText(details.get(2));
            ta_org.setText(details.get(3));
        }
    }

    public static void setDetails(ArrayList<String> info){
        details = new ArrayList<>();
        for (int i = 0; i < info.size(); i++){
            details.add(info.get(i));
        }
    }
}
