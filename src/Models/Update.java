package Models;

import java.util.Date;

public class Update {

    private int _id;
    private Update _before;
    private Date _published;
    private Event _event;
    private String _description;

    public Update(Event _event, Update _before, Date _puclished, String _description) {
        this._event = _event;
        this._before = _before;
        this._published = _puclished;
        this._description = _description;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Update get_before() {
        return _before;
    }

    public void set_before(Update _before) {
        this._before = _before;
    }

    public Date get_published() {
        return _published;
    }

    public void set_published(Date _published) {
        this._published = _published;
    }

    public Event get_event(){
        return _event;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }
}
