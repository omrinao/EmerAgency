package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Update {

    private int _id;
    private Update _before;
    private String _published;
    private Event _event;
    private String _description;
    private DateTimeFormatter _df = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    public Update(Event ev, int id){
        this._event = ev;
        this._id = id;
    }

    public Update(Event _event, Update _before, LocalDateTime _puclished, String _description) {
        this._event = _event;
        this._before = _before;
        this._published = _df.format(_puclished);
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

    public String get_published() {
        return _published;
    }

    public void set_published(LocalDateTime _published) {
        this._published = _df.format(_published);
    }

    public void set_published(String _published) {
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
