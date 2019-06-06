package Controller;

import Models.Category;
import Models.Event;
import Models.Update;

public interface IController {

    void getEvent(Category category);

    void publishEvent(Event event);

    void publishUpdate(Update update);

}
