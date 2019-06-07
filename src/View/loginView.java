package View;

import Models.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class loginView extends AView{
    public Button btn_login;
    public Button btn_clear;
    public TextField tf_userName;
    public PasswordField pf_password;
    public static String username = "";

    /**
     * method to confirm login
     * @param mouseEvent - mouse click on 'login' button
     */
    public void login_send(MouseEvent mouseEvent) {
        String user = tf_userName.getText();
        String password = pf_password.getText();

        if (user.isEmpty() || password.isEmpty()) {
            popProblem("Please insert both Username and Password");
            return;
        }

        String response = _controller.login_send(username, password);
        if (!response.equals("success")) {
            popProblem("Login failed!\n" +
                    "Make sure you typed in a proper Username and Password");
            return;
        }

        username = user;

        Stage login = (Stage)btn_login.getScene().getWindow();
        login.close();
        try {
            Stage mainStage = new Stage();
            mainStage.setTitle("Emer-Agency");
            //loading main view
            ClassLoader classLoader = getClass().getClassLoader();
            Enumeration<URL> fxml = classLoader.getResources("fxml/mainView.fxml");
            String fx = fxml.nextElement().toExternalForm();
            Parent root = FXMLLoader.load(new URL (fx));
            Scene scene = new Scene(root, 900, 600);
            ClassLoader cssloader = getClass().getClassLoader();
            Enumeration<URL> css = cssloader.getResources("css/ViewStyle.css");
            String cs = css.nextElement().toExternalForm();
            scene.getStylesheets().add(cs);
            mainStage.setScene(scene);

            mainStage.show();

        } catch (IOException e) {
            popProblem("Error while trying to load create event interface\n" + e.getMessage());
        }
        mouseEvent.consume();
    }
    /**
     * method to clear login parameters
     * @param mouseEvent - mouse click on 'clear' button
     */
    public void clear_send(MouseEvent mouseEvent) {
        tf_userName.setText("");
        pf_password.setText("");
    }
}
