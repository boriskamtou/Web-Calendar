package webCalendarSpring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webCalendarSpring.errors.EventNotFoundException;
import webCalendarSpring.models.Event;
import webCalendarSpring.repository.EventRepository;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class EventServiceImpl implements EventService {
    EventRepository repository;

    @Autowired
    public EventServiceImpl(EventRepository repository) {
        this.repository = repository;
    }

    @Override
    public Event createEvent(Event event) {
        return repository.save(event);
    }

    @Override
    public Event findEventById(Long id) {
        Optional<Event> optionalEvent = repository.findById(id);
        if (optionalEvent.isPresent()) {
            return optionalEvent.get();
        }
        throw new EventNotFoundException("The event doesn't exist!");
    }

    @Override
    public Event deleteEventById(Long id) {
        Optional<Event> optionalEvent = repository.findById(id);
        if (optionalEvent.isPresent()) {
            repository.deleteById(id);
            return optionalEvent.get();
        }
        throw new EventNotFoundException("The event doesn't exist!");
    }

    @Override
    public List<Event> getEvents() {
        return repository.findAll();
    }

    @Override
    public List<Event> getTodayEvent() {
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return repository.findAllEventByDate(date);
    }

    @Override
    public List<Event> getEventBetweenTwoDate(String start_time, String end_time) {
        List<Event> eventList = repository.findAll();
        List<Event> eventsBetweenDates = new ArrayList<>();

/*        if (start_time.isBlank() || end_time.isBlank()) {
            return eventList;
        }*/

        for (Event event : eventList) {
            LocalDate date = LocalDate.parse(event.getDate());
            LocalDate startDate = LocalDate.parse(start_time);
            LocalDate endDate = LocalDate.parse(end_time);
            if ((date.isAfter(startDate) || date.equals(startDate)) && (date.isBefore(endDate) || date.equals(endDate))) {
                eventsBetweenDates.add(event);
            }
        }
        return eventsBetweenDates;
    }
}
