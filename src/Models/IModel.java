package Models;

public interface IModel {

    void getEvent(Category category);

    void publishEvent(Event event);

    void publishUpdate(Update update);
}
