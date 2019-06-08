package DBAdapters;

import Models.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class DBEvent{

    protected Event _base;


    public DBEvent(Event _base) {
        this._base = _base;
    }

    public ArrayList<String> extractQuery() {
        ArrayList<String> ans = new ArrayList<>();
//        String insertEvent = insertEvents(),
//                eventCat = insertEventsCategories(),
//                userPer = insertUserPermissions(),
//                update = this.insertUpdate(),
//                updateInEvent = updateInEvent(),
//                accountable = accountableUsers();
//        ans += insertAccountableUsers();

//        ans.add(insertEvent);
//        ans.add(eventCat);
//        ans.add(userPer);
//        ans.add(update);
//        ans.add(updateInEvent);
//        ans.add(accountable);


        return ans;
    }

    public String updateInEvent() {
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";

        String ans = INSERT.concat("'eventsUpdates'('eventId','updateId','order') VALUES ('").concat("" + _base.get_id());
        ans = ans.concat(PERIOD).concat("" + _base.get_updates().get(0).get_id()).concat(PERIOD).concat("1");
        ans = ans.concat("');");

        return ans;

    }

    public ArrayList<String> accountableUsers() {
        ArrayList<String> ret = new ArrayList<>();
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";
        String ans = "";

        for (Map.Entry<Organization, AUser> entry : _base.get_organizations().entrySet()){
            ans = ans.concat(INSERT).concat("'accountableUsers'('eventId','organization','username') VALUES ('")
                    .concat("" + _base.get_id()).concat(PERIOD).concat(entry.getKey().get_name())
                    .concat(PERIOD).concat(entry.getValue().get_username()).concat("');");
            ret.add(ans);
            ans = "";
        }

        return ret;
    }

    public void setResourceId(int id) {
        _base.set_id(id);
    }

    public String insertEvents(){
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";

        String ans = INSERT.concat("'events'('id','title','published','status','creator') VALUES (NULL,'").concat(_base.get_title());
        ans = ans.concat(PERIOD).concat(_base.get_published()).concat(PERIOD).concat(_base.get_status().toString());
        ans = ans.concat(PERIOD).concat(_base.get_creator().get_username()).concat("');");


        return ans;
    }

    public ArrayList<String> insertUserPermissions() {
        ArrayList<String> ret = new ArrayList<>();
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";
        String ans = "";

        for (Map.Entry<AUser, EventPermission> entry : _base.get_users().entrySet()){
            ans = ans.concat(INSERT).concat("'usersPermissions'('eventId','username','permission') VALUES ('")
                    .concat("" + _base.get_id()).concat(PERIOD).concat(entry.getKey().get_username())
                    .concat(PERIOD).concat(entry.getValue().toString()).concat("');");
            ret.add(ans);
            ans = "";
        }

        return ret;
    }

    public ArrayList<String> insertEventsCategories(){
        ArrayList<String> ret = new ArrayList<>();
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";
        String ans = "";
        for (Category category : _base.get_categories()){
            ans = ans.concat(INSERT).concat("'categoriesInEvents'('eventId','category') VALUES ('").concat("" + _base.get_id());
            ans = ans.concat(PERIOD).concat(category.get_name()).concat("');");
            ret.add(ans);
            ans = "";
        }

        return ret;
    }


    public String insertUpdate(){
        String INSERT = "INSERT INTO ";
        String PERIOD = "','";

        String ans = INSERT.concat("'updates'('id','published','previous','description') VALUES (NULL,'").concat(_base.get_updates().get(0).get_published());
        ans = ans.concat(PERIOD).concat("NULL").concat(PERIOD).concat(_base.get_updates().get(0).get_description());
        ans = ans.concat("');");

        return ans;
    }


}
