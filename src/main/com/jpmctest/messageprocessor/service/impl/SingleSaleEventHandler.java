package main.com.jpmctest.messageprocessor.service.impl;

import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.dao.Sale;
import main.com.jpmctest.messageprocessor.datastore.DataStore;
import main.com.jpmctest.messageprocessor.exceptions.EventHandlerException;
import main.com.jpmctest.messageprocessor.service.EventHandler;

public class SingleSaleEventHandler implements EventHandler {

    @Override
    public void handleEvent(Event event) throws EventHandlerException {
        if (!(event.getEventBody() instanceof Sale)) {
            throw new EventHandlerException("Event body for SingleSale event is not of type Sale");
        }

        Sale sale = (Sale) event.getEventBody();
        if (sale.getTotalUnits() != 1) {
            throw new EventHandlerException("SingleSale has incorrect totalUnits value=" + sale.getTotalUnits());
        }

        DataStore.addSale(sale);
    }
}
