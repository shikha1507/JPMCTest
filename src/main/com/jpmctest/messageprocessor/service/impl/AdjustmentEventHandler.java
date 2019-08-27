package main.com.jpmctest.messageprocessor.service.impl;

import main.com.jpmctest.messageprocessor.dao.Adjustment;
import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.dao.Sale;
import main.com.jpmctest.messageprocessor.dao.enums.AdjustmentType;
import main.com.jpmctest.messageprocessor.datastore.DataStore;
import main.com.jpmctest.messageprocessor.exceptions.EventHandlerException;
import main.com.jpmctest.messageprocessor.service.EventHandler;
import java.math.BigDecimal;


public class AdjustmentEventHandler implements EventHandler {
    @Override
    public void handleEvent(Event event) throws EventHandlerException {
        if (!(event.getEventBody() instanceof Adjustment)) {
            throw new EventHandlerException("Event body for Adjustment event is not of type Adjustment");
        }

        Adjustment adjustment = (Adjustment) event.getEventBody();

        applyAdjustment(adjustment);

        DataStore.addAdjustment(adjustment);
    }

    private void applyAdjustment(Adjustment adjustment) {
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals(adjustment.getProductName())) {

                if (adjustment.getType() == AdjustmentType.ADD) {
                    sale.setUnitPrice(sale.getUnitPrice().add(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentType.MULTIPLY) {
                    sale.setUnitPrice(sale.getUnitPrice().multiply(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentType.SUBTRACT) {
                    BigDecimal newPrice = sale.getUnitPrice().subtract(adjustment.getAmount());
                    newPrice = newPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newPrice;
                    sale.setUnitPrice(newPrice);
                }
            }
        }
    }
}
