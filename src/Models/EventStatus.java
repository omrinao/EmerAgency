package Models;

public enum EventStatus{
    inProgress, ended;

    public EventStatus getStatus(String s){
        if (inProgress.toString().equals(s))
            return inProgress;
        return ended;
    }
}
