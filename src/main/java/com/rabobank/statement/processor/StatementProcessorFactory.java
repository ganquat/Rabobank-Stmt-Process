package com.rabobank.statement.processor;

import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Factory class for selecting the statement process object
 */
@Component
public class StatementProcessorFactory {


        private CSVStatementProcessor csvStatementProcessor;
        private XMLStatementProcessor xmlStatementProcessor;
		
		@Autowired(required=true)
        public StatementProcessorFactory(CSVStatementProcessor csvStatementProcessor,
                                         XMLStatementProcessor xmlStatementProcessor){
            this.csvStatementProcessor = csvStatementProcessor;
            this.xmlStatementProcessor = xmlStatementProcessor;
        }

        public ICustomerStatementInterface getStmtProcessor(String fileExtn) {
           if("csv".equalsIgnoreCase(fileExtn)) return csvStatementProcessor;
            else if ("xml".equalsIgnoreCase(fileExtn)) return xmlStatementProcessor;
            return null;
        }
    }

