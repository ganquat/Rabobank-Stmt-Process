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


        private CSVStmtProcessor csvStmtProcessor;
        private XMLStmtProcessor xmlStmtProcessor;
		
		@Autowired(required=true)
        public StmtProcessorFactory(CSVStmtProcessor csvStmtProcessor,
                                    XMLStmtProcessor xmlStmtProcessor){
            this.csvStmtProcessor = csvStmtProcessor;
            this.xmlStmtProcessor = xmlStmtProcessor;
        }

        public ICustStmtInterface getStmtProcessor(String fileExtn) {
           if("csv".equalsIgnoreCase(fileExtn)) return csvStmtProcessor;
            else if ("xml".equalsIgnoreCase(fileExtn)) return xmlStmtProcessor;
            return null;
        }
    }

