package Controller;
import Models.Model;
import View.*;

import java.util.ArrayList;

public class Controller {

    //login screen

    public String login_send(String username, String password){
        return "success";
    }


    //create event screen

    public String create_event(String title, ArrayList<String> categories, String description, ArrayList<String> org){
        return "success";
    }

    public ArrayList<String> getCategories(){
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add("Fire");
        toReturn.add("Murder");
        toReturn.add("Fired");
        toReturn.add("Murdered");
        toReturn.add("Firedede");
        return toReturn;
    }

    public ArrayList<String> getOrg(){
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add("Police");
        toReturn.add("Fire Fighters");
        return toReturn;
    }


    //post update screen

    public String post_update(ArrayList<String> category, String event, String description){
        return "success";
    }

    public ArrayList<String> getEvents(String category, String username){
        ArrayList<String> toReturn = new ArrayList<>();
        if (category.equals("Murder")){
            toReturn.add("Fire very hard fire");
            toReturn.add("Murder");
            toReturn.add("Fired");
            toReturn.add("Murdered");
            toReturn.add("Firedede");
        }
        return toReturn;
    }


    //view event details screen

    public ArrayList<String> getEventDetails(ArrayList<String> category, String event){
        ArrayList<String> details = new ArrayList<>();
        details.add("Koteret");
        details.add("Categoryot");
        details.add("Pratim");
        details.add("Irgunim");
        return details;
    }

}
