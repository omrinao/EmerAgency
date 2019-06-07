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

    public String post_update(String category, String event, String description){
        return "success";
    }

    public ArrayList<String> getEvents(String category, String username){
        ArrayList<String> toReturn = new ArrayList<>();
        toReturn.add("Fire very hard fire");
        toReturn.add("Murder");
        toReturn.add("Fired");
        toReturn.add("Murdered");
        toReturn.add("Firedede");
        return toReturn;
    }


    //view event screen

    public ArrayList<String> getCategoriesToView(String username){
        return new ArrayList<String>();
    }


    public ArrayList<String> getEventToView(String category){
        return new ArrayList<>();
    }


    //view event details screen

    public ArrayList<String> getEventDetails(String category, String event){
        return new ArrayList<>();
    }

}
