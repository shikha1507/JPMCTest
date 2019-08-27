package main.com.jpmctest.messageprocessor.factory;

import main.com.jpmctest.messageprocessor.dao.enums.MessageType;
import main.com.jpmctest.messageprocessor.service.EventHandler;
import main.com.jpmctest.messageprocessor.service.impl.AdjustmentEventHandler;
import main.com.jpmctest.messageprocessor.service.impl.MultiSaleEventHandler;
import main.com.jpmctest.messageprocessor.service.impl.SingleSaleEventHandler;
import java.util.HashMap;
import java.util.Map;

public class EventHandlerFactory {
    private static Map<MessageType, EventHandler> instanceStore;

    static {
        initSingletonStore();
    }

    public static EventHandler getHandler(MessageType type) {
        final EventHandler handler = instanceStore.get(type);

        if (handler == null) {
            System.err.println("Unrecognized event type " + type + ".");
        }

        return handler;
    }

    // To prevent creation of a number of unused objects
    private static void initSingletonStore() {
        instanceStore = new HashMap<>();
        instanceStore.put(MessageType.SINGLE_SALE, new SingleSaleEventHandler());
        instanceStore.put(MessageType.MULTI_SALE, new MultiSaleEventHandler());
        instanceStore.put(MessageType.ADJUSTMENT, new AdjustmentEventHandler());
    }
}
