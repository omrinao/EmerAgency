package Models;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Event {

    private String _title;
    private String _published;
    private EventStatus _status;
    private List<Category> _categories;
    private AUser _creator;
    private Map<AUser, EventPermission> _users;
    private TreeMap<Integer, Update> _updates;
    private Map<Organization, AUser> _organizations;
    private int _id;
    private DateTimeFormatter _df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");


    public Event(){
        _updates = new TreeMap<>();
        _organizations = new HashMap<>();
        _categories = new ArrayList<>();
        _users = new HashMap<>();
    }

    public Event(String _title, List<Category> _categories, AUser _creator, String initUpdate, List<Organization> _orgs) {
        this._title = _title;
        this._categories = _categories;
        this._creator = _creator;
        _users = new HashMap<>();
        _users.put(_creator, EventPermission.write);

        this._organizations = new HashMap<>();
        _orgs.forEach(organization -> {
            AUser notified = organization.getRandUser();
            _organizations.put(organization, notified);
            _users.put(notified, EventPermission.write);
            new Notification(notified, this);
        });

        _published = _df.format(LocalDateTime.now());
        _status = EventStatus.inProgress;
        _updates = new TreeMap<>();
        _updates.put(0, new Update(this, null, LocalDateTime.now(), initUpdate));

    }


    public void add_update(Update u){
        _updates.put(u.get_id(), u);
    }

    public Update get_update(int _id){
        return _updates.get(_id);
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_published() {
        return _published;
    }

    public void set_published(LocalDateTime _published) {
        this._published = _df.format(_published);
    }

    public void set_published(String _published) {
        this._published = _published;
    }

    public EventStatus get_status() {
        return _status;
    }

    public void set_status(EventStatus _status) {
        this._status = _status;
    }

    public List<Category> get_categories() {
        return _categories;
    }

    public void set_categories(List<Category> _categories) {
        this._categories = _categories;
    }

    public AUser get_creator() {
        return _creator;
    }

    public void set_creator(AUser _creator) {
        this._creator = _creator;
    }

    public Map<AUser, EventPermission> get_users() {
        return _users;
    }

    public void set_users(Map<AUser, EventPermission> _users) {
        this._users = _users;
    }

    public List<Update> get_updates() {
        ArrayList<Update> ret = new ArrayList<>();
        for (Integer i : _updates.navigableKeySet()){
            ret.add(_updates.get(i));
        }
        return ret;
    }

    public void set_updates(List<Update> _updates) {
        TreeMap<Integer, Update> ret = new TreeMap<>();
        for (Update u : _updates)
            ret.put(u.get_id(), u);

        this._updates = ret;
    }

    public Map<Organization, AUser> get_organizations() {
        return _organizations;
    }

    public void set_organizations(Map<Organization, AUser> _organizations) {
        this._organizations = _organizations;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Event))
            return false;
        Event ev = (Event) obj;
        return _id == ev._id;

    }

    public void add_category(String string) {
        _categories.add(new Category(string));
    }
}

