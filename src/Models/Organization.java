package Models;

import java.util.List;

public class Organization {

    private List<AUser> _users;
    private String _name;

    public Organization(List<AUser> _users, String _name) {
        this._users = _users;
        this._name = _name;
    }

    public AUser getRandUser() {
        return _users.get((int) (Math.random()*_users.size()));
    }


    public List<AUser> get_users() {
        return _users;
    }

    public void set_users(List<AUser> _users) {
        this._users = _users;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public AUser get_user(String name){
        for (AUser au : _users)
            if (au.get_username().equals(name))
                return au;

        return null;
    }
}
