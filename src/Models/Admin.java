package Models;

public class Admin extends AUser {

    public Admin(String _username, String _password, String _email, AccountStatus _status, int _loginErr, Organization _organiztion) {
        super(_username, _password, _email, _status, _loginErr, _organiztion);
    }
}
