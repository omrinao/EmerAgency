package Models;

public class User extends AUser {

    private int _rank;

    public User(String _username, String _password, String _email, AccountStatus _status, int _loginErr, Organization _organiztion) {
        super(_username, _password, _email, _status, _loginErr, _organiztion);
    }
}
