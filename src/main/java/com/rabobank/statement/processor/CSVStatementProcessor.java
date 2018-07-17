package com.rabobank.statement.processor;

import com.rabobank.statement.constants.CustomerStatementConstant;
import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import com.rabobank.statement.model.CustomerStatementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * CSV statement processor
 */
@Component
public class CSVStatementProcessor implements ICustomerStatementInterface, CustomerStatementConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVStatementProcessor.class);

    private String columnseperator;

    public CSVStatementProcessor(@Value("${csv.columnseperator}") String columnseperator) {

        this.columnseperator = columnseperator;
    }

    /*
     *  This method reads the CSV data from multipart input file and converts it to Customer statement list
     *  object and sends the list for validation
     *  @param file
    * */
    public List<CustomerStatementModel> process(MultipartFile file) {
        LOGGER.info("Inside CSV statement processor method");
        CustomerStatementModel customerStatementModel;
        List<CustomerStatementModel> customerStatementList = new ArrayList<CustomerStatementModel>();
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            customerStatementList = bufferedReader.lines().skip(1).map(mapToItem).collect(Collectors.toList());
        } catch (Exception e) {
            CustomerStatementException customerStatementException = new CustomerStatementException("Error reading CSV file:", e);
            LOGGER.error("Exception in CSV process method : {}", e.getMessage());
            throw customerStatementException;

        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {
                CustomerStatementException customerStatementException = new CustomerStatementException("Error closing buffered reader:", e);
                LOGGER.error("Exception in CSV process method : {}", e.getMessage());
                throw customerStatementException;
            }
        }
        return customerStatementList;
    }


    private Function<String, CustomerStatementModel> mapToItem = (line) -> {
        String[] columns = line.split(columnseperator);// a CSV has comma separated lines
        CustomerStatementModel customerStatementModel = new CustomerStatementModel();
        customerStatementModel.setRefernceNo(Integer.parseInt(columns[COL_REFNO]));
        customerStatementModel.setAccNumber(columns[COL_ACCTNO]);
        customerStatementModel.setDescription(columns[COL_DESC]);
        customerStatementModel.setStartBalance(Double.parseDouble(columns[COL_STBAL]));
        customerStatementModel.setMutation(Double.parseDouble(columns[COL_MUT]));
        customerStatementModel.setEndBalance(Double.parseDouble(columns[COL_EDBAL]));
        return customerStatementModel;
    };
}
