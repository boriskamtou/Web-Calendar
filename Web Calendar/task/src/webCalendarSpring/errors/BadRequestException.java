package webCalendarSpring.errors;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super("Please enter valid input");
    }
}
