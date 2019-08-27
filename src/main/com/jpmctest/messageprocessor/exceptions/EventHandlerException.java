package main.com.jpmctest.messageprocessor.exceptions;

public class EventHandlerException extends Exception {

	private static final long serialVersionUID = 1L;

	public EventHandlerException(String message) {
        super(message);
    }

    public EventHandlerException(String message, Throwable cause) {
        super(message, cause);
    }
}
