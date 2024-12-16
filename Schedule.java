import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.TreeSet;

class Schedule {
    private TreeSet<Event> schedule;

    public Schedule() {
        this.schedule = new TreeSet<>(new EventComparator());
    }

    // Add event to schedule
    public String addEvent(String eventName, int day, int month, int year, int hour, int minute) throws Exception {
        Calendar calStart = Calendar.getInstance();
        calStart.set(year, month - 1, day, hour, minute);
        Date start = calStart.getTime();

        // Add one hour to the start time as default duration. 
        // You can modify this to accept a duration parameter.
        calStart.add(Calendar.HOUR, 1);  
        Date end = calStart.getTime();

        return addEvent(eventName, start, end);
    }

    public String addEvent(String eventName, Date start, Date end) throws Exception {
        Event newEvent = new Event(eventName, start, end);
        if (isConflict(newEvent)) {
            String conflictingEvent = findConflictingEvent(newEvent);
            return "The event '" + eventName + "' conflicts with '" + conflictingEvent + "'";
        }
        this.schedule.add(newEvent);
        System.out.println("Successfully added Event: " + eventName);
        return "Successfully added Event: " + eventName;
    }

    public ArrayList<Event> getSchedule() {
        return new ArrayList<>(this.schedule);
    } 

    public Event getNextEvent() throws Exception {
        if (this.schedule.size() == 0) {
            System.err.println("Error: No events scheduled");
            throw new Exception("No events scheduled");
        }
        return this.schedule.first(); 
    }

    public ArrayList<Event> getEvents(Date startTime, Date endTime) {
        Event dummyStart = new Event("dummy", startTime, startTime);
        Event dummyEnd = new Event("dummy", endTime, endTime);

        // Use the TreeSet's subSet method for efficient retrieval
        return new ArrayList<>(this.schedule.subSet(dummyStart, true, dummyEnd, false));
    }

    public void clearSchedule() {
        this.schedule.clear();
        System.out.println("Schedule cleared.");
    }

    // Removes all events that began before Date t
    public void cleanSchedule(Date t) {
        Event dummy = new Event("dummy", t, t);
        this.schedule.headSet(dummy).clear(); // Efficiently remove events before 't'
        System.out.println("Schedule cleaned. Events before " + t + " removed.");
    }

    // Optimized conflict checking - leverages TreeSet's ordering
    private boolean isConflict(Event newEvent) {
        Event floorEvent = this.schedule.floor(newEvent);
        Event ceilingEvent = this.schedule.ceiling(newEvent);

        return (floorEvent != null && floorEvent.getEnd().after(newEvent.getStart())) ||
               (ceilingEvent != null && ceilingEvent.getStart().before(newEvent.getEnd()));
    }

    private String findConflictingEvent(Event newEvent) {
        Event floorEvent = this.schedule.floor(newEvent);
        if (floorEvent != null && floorEvent.getEnd().after(newEvent.getStart())) {
            return floorEvent.getEventName();
        }

        Event ceilingEvent = this.schedule.ceiling(newEvent);
        if (ceilingEvent != null && ceilingEvent.getStart().before(newEvent.getEnd())) {
            return ceilingEvent.getEventName();
        }

        // Should never reach here if isConflict(newEvent) is true
        return "Unknown"; 
    }

    public void removeEvent(String eventName) throws Exception {
        Event eventToRemove = null;
        for (Event e : this.schedule) {
            if (e.getEventName().equals(eventName)) {
                eventToRemove = e;
                break;
            }
        }
        if (eventToRemove == null) {
            System.err.println("Error: Event '" + eventName + "' does not exist!");
            throw new Exception("Event does not exist!");
        } 
        this.schedule.remove(eventToRemove);
        System.out.println("Event '" + eventName + "' removed successfully.");
    }
}
