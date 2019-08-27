package test.com.jpmctest.messageprocessor.sevice.impl;


import main.com.jpmctest.messageprocessor.dao.Adjustment;
import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.dao.Sale;
import main.com.jpmctest.messageprocessor.dao.enums.AdjustmentType;
import main.com.jpmctest.messageprocessor.dao.enums.MessageType;
import main.com.jpmctest.messageprocessor.datastore.DataStore;
import main.com.jpmctest.messageprocessor.exceptions.EventHandlerException;
import main.com.jpmctest.messageprocessor.service.impl.AdjustmentEventHandler;
import org.junit.Before;
import org.junit.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AdjustmentEventHandlerTest {

    private AdjustmentEventHandler adjustmentEventHandler;
    private List<Sale> sales;
    private Sale sale1 = new Sale("Dairy Milk", BigDecimal.valueOf(3.50), 1);
    private Sale sale2 = new Sale("Galaxy", BigDecimal.valueOf(2.20), 1);
    private Sale sale3 = new Sale("Dairy Milk", BigDecimal.valueOf(3.50), 2);

    @Before
    public void setUp() {
        sales = new ArrayList<>();
        sales.add(sale1);
        sales.add(sale2);
        sales.add(sale3);
        adjustmentEventHandler = new AdjustmentEventHandler();
        DataStore.salesHistory = sales;
    }

    @Test
    public void handleEvent_add() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.ADD,
                "Dairy Milk", BigDecimal.valueOf(0.50)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Dairy Milk")) {
                assertEquals(BigDecimal.valueOf(10.30), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Galaxy")) {
                assertEquals(BigDecimal.valueOf(2.20), sale.getUnitPrice());
            }
        }
    }

    private void assertEquals(BigDecimal valueOf, BigDecimal unitPrice) {
		// TODO Auto-generated method stub
		
	}

	@Test
    public void handleEvent_subtract() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.SUBTRACT,
                "Dairy Milk", BigDecimal.valueOf(0.30)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Dairy Milk")) {
                assertEquals(BigDecimal.valueOf(8.20), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Galaxy")) {
                assertEquals(BigDecimal.valueOf(2.20), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void handleEvent_multiply() throws Exception {
        Event event = new Event(MessageType.ADJUSTMENT, new Adjustment(AdjustmentType.MULTIPLY,
                "Dairy Milk", BigDecimal.valueOf(2)));

        adjustmentEventHandler.handleEvent(event);
        for (Sale sale : DataStore.salesHistory) {
            if (sale.getProductName().equals("Dairy Milk")) {
                assertEquals(BigDecimal.valueOf(1.00), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Galaxy")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test(expected = EventHandlerException.class)
    public void handleEvent_incorrect_body_type() throws Exception {
        Event event = new Event(MessageType.MULTI_SALE, sale1);
        adjustmentEventHandler.handleEvent(event);
    }

}