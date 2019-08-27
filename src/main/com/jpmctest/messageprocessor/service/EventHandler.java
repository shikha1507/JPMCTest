package main.com.jpmctest.messageprocessor.service;

import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.exceptions.EventHandlerException;


public interface EventHandler {
    void handleEvent(Event event) throws EventHandlerException;
}
