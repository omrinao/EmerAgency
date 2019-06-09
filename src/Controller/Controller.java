package Controller;
import Models.*;
import View.*;


import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller {

    private Model _m;
    public static List<Organization> _organizations;
    public static List<Category> _categories;
    private static Controller _instance;

    public static Controller getInstance() throws Exception{
        if (_instance == null){
            _instance = new Controller();
        }
        return _instance;
    }

    private Controller() throws Exception{
        _m = new Model();
        initController();
    }

    public void initController() throws Exception{
        try {
            _organizations = _m.getOrganizations();
            _categories = _m.getCategories();
        }
        catch (SQLException ex){
            throw new Exception("System crashed, please restart the system");
        }

    }

    //login screen

    public AUser login_send(String username, String password){
        return _m.login(username, password);
    }


    //create event screen

    public String create_event(String title, ArrayList<String> categories, String description, ArrayList<String> org){
        List<Category> cat = new ArrayList<Category>();
        for (int i = 0; i < categories.size(); i++){
            for(int j = 0; j < _categories.size(); j++){
                if(_categories.get(j).get_name().equals(categories.get(i))){
                    cat.add(_categories.get(j));
                }
            }
        }

        List<Organization> organizations = new ArrayList<Organization>();
        for (int i = 0; i < org.size(); i++){
            for(int j = 0; j < _organizations.size(); j++){
                if(_organizations.get(j).get_name().equals(org.get(i))){
                    organizations.add(_organizations.get(j));
                }
            }
        }

        Event create = new Event(title, cat, loginView.userObject, description, organizations);
        boolean response = _m.addEvent(create);
        if (response)
            return "success";
        return "failure";
    }

    public ArrayList<String> getCategories(){
        List<Category> cat = _m.getCategories();
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < cat.size(); i++){
            toReturn.add(cat.get(i).get_name());
        }
        return toReturn;
    }

    public ArrayList<String> getOrg(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < _organizations.size(); i++){
            toReturn.add(_organizations.get(i).get_name());
        }
        return toReturn;
    }


    //post update screen

    public String post_update(Event event, String description){
        Update update = new Update(event, null, LocalDateTime.now(), description);
        return _m.addUpdate(update) ? "success" : "failed";
    }

    public List<Event> getEvents(ArrayList<Category> category, AUser user){
        List<Category> cat = new ArrayList<>();
        for (int i = 0; i < category.size(); i++){
            for(int j = 0; j < _categories.size(); j++){
                if(_categories.get(j).get_name().equals(category.get(i).get_name())){
                    cat.add(_categories.get(j));
                }
            }
        }
        List<Event> events = _m.watchEvents(cat);
        List<Event> toReturn = new ArrayList<>();
        for (int i = 0; i < events.size(); i++){
            Map<Organization, AUser> m = events.get(i).get_organizations();
            if (m.containsKey(user.get_organiztion()))
                toReturn.add(events.get(i));
        }

        return toReturn;
    }

    public static Organization getOrg(String name){
        for (Organization org : _organizations){
            if (org.get_name().equals(name))
                return org;
        }

        return _organizations.get(0);
    }

}
