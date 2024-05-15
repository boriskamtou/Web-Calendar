package webCalendarSpring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webCalendarSpring.models.Event;
import webCalendarSpring.services.EventService;

import java.util.*;

@RestController
public class EventController {

    private final EventService service;

    @Autowired
    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping("/event/today")
    public ResponseEntity<?> getTodayEvents() {
        List<Event> eventList = service.getTodayEvent();
        if (eventList.isEmpty()) {
            return ResponseEntity.ok(new ArrayList<>());
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventList);
    }

    @PostMapping("/event")
    public ResponseEntity<?> createEvent(@RequestBody @Valid Event event) {
        Event createdEvent = service.createEvent(event);
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "The event has been added!");
        response.put("event", createdEvent.getEvent());
        response.put("date", createdEvent.getDate());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEventById(@PathVariable("id") Long id) {
        Event event = service.findEventById(id);
        return ResponseEntity.ok().body(event);
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEventById(@PathVariable("id") Long id) {
        Event event = service.deleteEventById(id);
        return ResponseEntity.ok().body(event);
    }

    @GetMapping("/event")
    public ResponseEntity<?> getEvents() {
        List<Event> eventList = service.getEvents();
        if (eventList.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventList);
    }

    @GetMapping(value = "/event", params = {"start_time", "end_time"})
    public ResponseEntity<?> getEventBetweenTwoDate(
            @RequestParam("start_time") String startTime,
            @RequestParam("end_time") String endTime
    ) {
        List<Event> eventList = service.getEventBetweenTwoDate(startTime, endTime);
        if (eventList.size() == 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(eventList);
    }
}
