package com.rabobank.statement.service;

import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.model.CustStmtModel;
import com.rabobank.statement.processor.StmtProcessorFactory;
import com.rabobank.statement.processor.XMLStmtProcessor;
import com.rabobank.statement.validator.CustStmtValidator;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Service class for processing customer statement
 */
@Service
public class CustStmtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustStmtService.class);

    @Autowired
    private XMLStmtProcessor custStmtProcessor;

    @Autowired
    private StmtProcessorFactory stmtProcessorFactory;

    @Autowired
    private CustStmtValidator custStmtValidator;

    /*
    *   This method validates the customer statement records and updates the failed record in list
    *   @param custStmtModel
    * */
    public List<CustStmtModel> validateCustStmt(MultipartFile file) {
        LOGGER.info("Inside customer statement validation method");

        List<CustStmtModel> custStmtModelList = convertFileDataToCustStmtModel(file);
        List<CustStmtModel> validatedStmtModelList = custStmtValidator.checkForInvalidTransaction(custStmtModelList);

        return validatedStmtModelList;
    }

    /*
     * This method calls the corresponding process method through factory method pattern
     */
    private List<CustStmtModel> convertFileDataToCustStmtModel(MultipartFile file) {
        ICustStmtInterface CustStmtObject = stmtProcessorFactory.getStmtProcessor(
                FilenameUtils.getExtension(file.getOriginalFilename()));

        List<CustStmtModel> custStmtModelList = CustStmtObject.process(file);
        return custStmtModelList;
    }
}
