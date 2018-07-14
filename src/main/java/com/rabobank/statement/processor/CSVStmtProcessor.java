package com.rabobank.statement.processor;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.exception.CustStmtException;
import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.model.CustStmtModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV statement processor
 */
@Component
public class CSVStmtProcessor implements ICustStmtInterface,CustStmtConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVStmtProcessor.class);

    @Value("${csv.lineseperator}")
    private String lineseperator;

    @Value("${csv.columnseperator}")
    private String columnseperator;

    /*
     *  This method reads the CSV data from multipart input file and converts it to Customer statement list
     *  object and sends the list for validation
     *  @param file
    * */
    public List<CustStmtModel> process(MultipartFile file) {
        LOGGER.info("Inside CSV statement processor method");
        CustStmtModel custStmtModel;
        List<CustStmtModel> custStmtList = new ArrayList<CustStmtModel>();
        InputStream ip = null;
        BufferedReader br = null;
        String[] rows = null;
        String[] columns = null;
        try {
            byte[] bytes = file.getBytes();
            String completeData = new String(bytes);
            rows = completeData.split(lineseperator);

            // Discard the first row in the CSV file
            for (int i = 1; i < rows.length ;i++ ) {
                columns = rows[i].split(columnseperator);
                custStmtModel = new CustStmtModel();
                custStmtModel.setRefernceNo(Integer.parseInt(columns[COL_REFNO]));
                custStmtModel.setAccNumber(columns[COL_ACCTNO]);
                custStmtModel.setDescription(columns[COL_DESC]);
                custStmtModel.setStartBalance(Double.parseDouble(columns[COL_STBAL]));
                custStmtModel.setMutation(Double.parseDouble(columns[COL_MUT]));
                custStmtModel.setEndBalance(Double.parseDouble(columns[COL_EDBAL]));
                custStmtList.add(custStmtModel);
            }
        }
        catch(Exception e) {
            CustStmtException custStmtException = new CustStmtException("Error reading CSV file:", e);
            LOGGER.error("Exception in CSV process method", e.getMessage());
            throw custStmtException;
        }
        return custStmtList;
    }
}
