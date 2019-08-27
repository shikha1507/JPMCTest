package main.com.jpmctest.messageprocessor;

import main.com.jpmctest.messageprocessor.datastore.DataStore;
import main.com.jpmctest.messageprocessor.service.MessageProcessor;
import main.com.jpmctest.messageprocessor.service.ReportGenerator;
import main.com.jpmctest.messageprocessor.service.impl.ReportGeneratorImpl;
import main.com.jpmctest.messageprocessor.service.impl.SalesMessageProcessor;

/**
 * This application will generate the sales report by taking random Sales events as input.
 * It will process them by maintaining a queue.
 */
public class Main {

    public static void main(String[] args) {
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        MessageProcessor messageProcessor = new SalesMessageProcessor(reportGenerator);

        ExternalMessageProcessor processor = new ExternalMessageProcessor();
        DataStore.messageQueue.addAll(processor.generateEvents());
        messageProcessor.startProcessing();
    }
}
