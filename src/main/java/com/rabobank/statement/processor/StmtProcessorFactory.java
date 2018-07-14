package com.rabobank.statement.processor;

import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.processor.CSVStmtProcessor;
import com.rabobank.statement.processor.XMLStmtProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Factory class for selecting the statement process object
 */
@Component
public class StmtProcessorFactory {

        @Autowired
        private CSVStmtProcessor csvStmtProcessor;

        @Autowired
        private XMLStmtProcessor xmlStmtProcessor;

        public ICustStmtInterface getStmtProcessor(String fileExtn) {
           if("csv".equalsIgnoreCase(fileExtn)) return csvStmtProcessor;
            else if ("xml".equalsIgnoreCase(fileExtn)) return xmlStmtProcessor;
            return null;
        }
    }

