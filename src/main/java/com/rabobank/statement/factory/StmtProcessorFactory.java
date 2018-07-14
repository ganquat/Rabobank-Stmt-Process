package com.rabobank.statement.factory;

import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.processor.CSVStmtProcessor;
import com.rabobank.statement.processor.XMLStmtProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by Ganesh_C01 on 7/13/2018.
 */
@Component
public class StmtProcessorFactory {

        @Autowired
        private CSVStmtProcessor csvStmtProcessor;

        @Autowired
        private XMLStmtProcessor xmlStmtProcessor;

        public ICustStmtInterface getStmtProcessor(String extn) {

           if("csv".equalsIgnoreCase(extn)) return csvStmtProcessor;
            else if ("xml".equalsIgnoreCase(extn)) return xmlStmtProcessor;
            return null;
        }


    }

