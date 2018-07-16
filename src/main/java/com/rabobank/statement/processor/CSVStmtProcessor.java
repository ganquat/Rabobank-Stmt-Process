package com.rabobank.statement.processor;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.exception.CustStmtException;
import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.model.CustStmtList;
import com.rabobank.statement.model.CustStmtModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CSV statement processor
 */
@Component
public class CSVStmtProcessor implements ICustStmtInterface,CustStmtConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(CSVStmtProcessor.class);

    private String columnseperator;

    public CSVStmtProcessor(@Value("${csv.columnseperator}") String columnseperator){

       this.columnseperator = columnseperator;
    }
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


        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream(),"UTF-8"));
            custStmtList = br.lines().skip(1).map(mapToItem).collect(Collectors.toList());
            br.close();
        } catch (Exception e) {
            CustStmtException custStmtException = new CustStmtException("Error reading CSV file:", e);
            LOGGER.error("Exception in CSV process method : {}", e.getMessage());
            throw custStmtException;

        }

        return custStmtList;
    }


    private Function<String, CustStmtModel> mapToItem = (line) -> {
        String[] columns = line.split(columnseperator);// a CSV has comma separated lines
        CustStmtModel custStmtModel = new CustStmtModel();
        custStmtModel.setRefernceNo(Integer.parseInt(columns[COL_REFNO]));
        custStmtModel.setAccNumber(columns[COL_ACCTNO]);
        custStmtModel.setDescription(columns[COL_DESC]);
        custStmtModel.setStartBalance(Double.parseDouble(columns[COL_STBAL]));
        custStmtModel.setMutation(Double.parseDouble(columns[COL_MUT]));
        custStmtModel.setEndBalance(Double.parseDouble(columns[COL_EDBAL]));
        //more initialization goes here
        return custStmtModel;
    };
}
