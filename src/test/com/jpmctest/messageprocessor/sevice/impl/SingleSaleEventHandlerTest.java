package test.com.jpmctest.messageprocessor.sevice.impl;


import main.com.jpmctest.messageprocessor.dao.Adjustment;
import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.dao.Sale;
import main.com.jpmctest.messageprocessor.dao.enums.AdjustmentType;
import main.com.jpmctest.messageprocessor.dao.enums.MessageType;
import main.com.jpmctest.messageprocessor.datastore.DataStore;
import main.com.jpmctest.messageprocessor.exceptions.EventHandlerException;
import main.com.jpmctest.messageprocessor.service.impl.SingleSaleEventHandler;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class SingleSaleEventHandlerTest {
    private SingleSaleEventHandler singleSaleEventHandler;
    private List<Sale> sales;
    private Sale sale1 = new Sale("Dairy Milk", BigDecimal.valueOf(0.50), 1);
    private Sale sale2 = new Sale("Galaxy", BigDecimal.valueOf(0.75), 3);

    @Before
    public void setUp() {
        singleSaleEventHandler = new SingleSaleEventHandler();
        sales = new ArrayList<>();
        DataStore.salesHistory = sales;
    }

    @Test
    public void handleEvent_ok() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale1);
        singleSaleEventHandler.handleEvent(event);
        assertEquals(sale1, DataStore.salesHistory.get(0));
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_unit_size_3_should_throw_exception() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale2);
        singleSaleEventHandler.handleEvent(event);
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_incorrect_body_type() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, new Adjustment(AdjustmentType.MULTIPLY,
                "Dairy Milk", BigDecimal.valueOf(2)));
        singleSaleEventHandler.handleEvent(event);
    }

}