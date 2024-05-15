package webCalendarSpring.services;

import webCalendarSpring.models.Event;

import java.util.List;

public interface EventService {
    Event createEvent(Event event);
    Event findEventById(Long id);
    Event deleteEventById(Long id);

    List<Event> getEvents();

    List<Event> getTodayEvent();

    List<Event> getEventBetweenTwoDate(String startDate, String endDate);
}
