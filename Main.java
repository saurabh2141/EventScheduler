import java.util.Date;

public class Main {
    public static void main(String[] args) {
        try {
            // Create the Schedule object
            Schedule schedule = new Schedule();
            
            // Add events to the schedule
            schedule.addEvent("Meeting", 15, 12, 2024, 10, 30);
            schedule.addEvent("Doctor Appointment", 15, 12, 2024, 14, 00);
            
            // Display all scheduled events
            System.out.println("Scheduled Events: ");
            for (Event e : schedule.getSchedule()) {
                System.out.println(e.getEventName() + " from " + e.getStart() + " to " + e.getEnd());
            }
            
            // Get the next event
            Event nextEvent = schedule.getNextEvent();
            System.out.println("\nNext Event: " + nextEvent.getEventName() + " from " + nextEvent.getStart() + " to " + nextEvent.getEnd());
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
