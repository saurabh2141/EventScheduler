import java.util.Date;

public class Event implements Comparable<Event> {
    private String eventName;
    private Date start;
    private Date end;

    // Event constructor
    public Event(String eventName, Date start, Date end) {
        this.eventName = eventName;
        this.start = start;
        this.end = end;
    }

    // Getters for eventName, start, and end
    public String getEventName() {
        return eventName;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    public int compareTo(Event other) {
        int compare = this.start.compareTo(other.start);
        if (compare == 0) {
            return this.eventName.compareTo(other.eventName); // Ensure uniqueness 
        } 
        return compare;
    }
}
