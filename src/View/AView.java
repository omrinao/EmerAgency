package View;
import Controller.Controller;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

public abstract class AView {

    //singleton facade controller

    protected Controller _controller;
    {

         try{
             _controller = Controller.getInstance();
         }
         catch (Exception e){
             popProblem(e.getMessage());
             System.exit(0);
         }
    }


    /**
     * method to pop problem easily
     * @param description - description of the problem to show
     */
    protected void popProblem(String description) {
        Alert prob = new Alert(Alert.AlertType.ERROR);

        prob.setContentText(description);
        prob.showAndWait();
    }

    /**
     * method to pop info easily
     * @param data - the data of which to show
     */
    protected void popInfo(String data){

        Alert prob = new Alert(Alert.AlertType.INFORMATION);
        prob.setContentText(data);
        prob.showAndWait();
    }
}
