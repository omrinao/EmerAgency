package Models;

public class Category {

    private String _name;

    public Category(String _name) {
        this._name = _name;
    }


    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @Override
    public String toString() {
        return "'" + get_name() + "'";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Category))
            return false;
        Category other =(Category) obj;
        return other._name.equals(this._name);
    }
}
