package com.rabobank.statement.processor;

import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StatementProcessorFactory {

    private CSVStatementProcessor csvStatementProcessor;
    private XMLStatementProcessor xmlStatementProcessor;
    private YAMLStatementProcessor yamlStatementProcessor; // New field

    @Autowired(required = true)
    public StatementProcessorFactory(CSVStatementProcessor csvStatementProcessor,
                                     XMLStatementProcessor xmlStatementProcessor,
                                     YAMLStatementProcessor yamlStatementProcessor) { // Added to constructor
        this.csvStatementProcessor = csvStatementProcessor;
        this.xmlStatementProcessor = xmlStatementProcessor;
        this.yamlStatementProcessor = yamlStatementProcessor; // Assign new processor
    }

    public ICustomerStatementInterface getStmtProcessor(String fileExtn) {
        if ("csv".equalsIgnoreCase(fileExtn)) {
            return csvStatementProcessor;
        } else if ("xml".equalsIgnoreCase(fileExtn)) {
            return xmlStatementProcessor;
        } else if ("yaml".equalsIgnoreCase(fileExtn) || "yml".equalsIgnoreCase(fileExtn)) { // New condition
            return yamlStatementProcessor;
        }
        return null;
    }
}
