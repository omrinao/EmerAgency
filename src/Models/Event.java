package Models;

import java.util.*;

public class Event {

    private String _title;
    private Date _published;
    private EventStatus _status;
    private List<Category> _categories;
    private AUser _creator;
    private Map<AUser, EventPermission> _users;
    private List<Update> _updates;
    private Map<Organization, AUser> _organizations;
    private int _id;

    public Event(String _title, List<Category> _categories, AUser _creator, String initUpdate, List<Organization> _orgs) {
        this._title = _title;
        this._categories = _categories;
        this._creator = _creator;
        _users = new HashMap<>();
        _users.put(_creator, EventPermission.read);

        this._organizations = new HashMap<>();
        _orgs.forEach(organization -> {
            AUser notified = organization.getRandUser();
            _organizations.put(organization, notified);
            _users.put(notified, EventPermission.write);
            new Notification(notified, this);
        });

        _published = new Date();
        _status = EventStatus.inProgress;
        _updates = new ArrayList<>();
        _updates.add(new Update(this, null, new Date(), initUpdate));

    }


    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public Date get_published() {
        return _published;
    }

    public void set_published(Date _published) {
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
        return _updates;
    }

    public void set_updates(List<Update> _updates) {
        this._updates = _updates;
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
}

