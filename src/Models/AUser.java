package Models;

public abstract class AUser {

    protected String _username;
    protected String _password;
    protected String _email;
    protected AccountStatus _status;
    protected int _loginErr;
    protected Organization _organiztion;

    public AUser(String _username){
        this._username = _username;
    }

    public AUser(String _username, String _password, String _email, AccountStatus _status, int _loginErr, Organization _organiztion) {
        this._username = _username;
        this._password = _password;
        this._email = _email;
        this._status = _status;
        this._loginErr = _loginErr;
        this._organiztion = _organiztion;
    }

    public String get_username() {
        return _username;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public AccountStatus get_status() {
        return _status;
    }

    public void set_status(AccountStatus _status) {
        this._status = _status;
    }

    public int get_loginErr() {
        return _loginErr;
    }

    public void set_loginErr(int _loginErr) {
        this._loginErr = _loginErr;
    }

    public Organization get_organiztion() {
        return _organiztion;
    }

    public void set_organiztion(Organization _organiztion) {
        this._organiztion = _organiztion;
    }
}
