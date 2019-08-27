package main.com.jpmctest.messageprocessor;

import main.com.jpmctest.messageprocessor.dao.Adjustment;
import main.com.jpmctest.messageprocessor.dao.Event;
import main.com.jpmctest.messageprocessor.dao.Sale;
import main.com.jpmctest.messageprocessor.dao.enums.AdjustmentType;
import main.com.jpmctest.messageprocessor.dao.enums.MessageType;
import java.math.BigDecimal;
import java.util.*;

/**
 * This class will generate random sales events based.
 */
public class ExternalMessageProcessor {
	
    Set<String> products = new HashSet<>(Arrays.asList("Dairy Milk", "Galaxy", "Maltesers", "Twirl", "Wispa"));
    Set<BigDecimal> adjustmentPrices = new HashSet<>(Arrays.asList(BigDecimal.valueOf(1.50),
            BigDecimal.valueOf(0.50), BigDecimal.valueOf(1.30)));
    Set<Integer> amounts = new HashSet<>(Arrays.asList(5,6,2));
    Set<AdjustmentType> adjustmentTypes = new HashSet<>(Arrays.asList(AdjustmentType.ADD, AdjustmentType.MULTIPLY, AdjustmentType.SUBTRACT));

    Map<String, BigDecimal> productPrices = new HashMap<String, BigDecimal>(){
	{
        put("Dairy Milk", BigDecimal.valueOf(3.50));
        put("Galaxy", BigDecimal.valueOf(2.20));
        put("Maltesers", BigDecimal.valueOf(4.30));
        put("Twirl", BigDecimal.valueOf(2.00));
        put("Wispa", BigDecimal.valueOf(3.10));}};


    public List<Event> generateEvents() {
        List<Event> events = new LinkedList<>();

        for (int i = 1; i < 60; i++) {
            if (i % 11 == 0) {
            	events.add(new Event(MessageType.ADJUSTMENT,
                        new Adjustment((AdjustmentType) getRandom(adjustmentTypes),
                                (String) getRandom(products),
                                (BigDecimal) getRandom(adjustmentPrices))));
            }
            else if (i % 5 == 0) {
                String product = (String) getRandom(products);
                events.add(new Event(MessageType.MULTI_SALE,
                        new Sale(product,
                                productPrices.get(product),
                                (Integer) getRandom(amounts))));
            } else {
                String product = (String) getRandom(products);
                events.add(new Event(MessageType.SINGLE_SALE,
                        new Sale(product,
                                productPrices.get(product),
                                1)));
            }
        }

        return events;
    }

    private Object getRandom(Collection<?> e) {
        return e.stream()
                .skip((int) (e.size() * Math.random()))
                .findFirst().get();
    }
}
