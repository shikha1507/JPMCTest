package main.com.jpmctest.messageprocessor.exceptions;

public class EmptyMessageQueueException extends Exception {
   
	private static final long serialVersionUID = 1L;

	public EmptyMessageQueueException(String message) {
        super(message);
    }

    public EmptyMessageQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}
