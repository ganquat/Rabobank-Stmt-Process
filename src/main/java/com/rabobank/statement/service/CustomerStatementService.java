package com.rabobank.statement.service;

import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import com.rabobank.statement.model.CustomerStatementModel;
import com.rabobank.statement.processor.StatementProcessorFactory;
import com.rabobank.statement.validator.CustomerStatementValidator;
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
public class CustomerStatementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerStatementService.class);

    private StatementProcessorFactory statementProcessorFactory;
    private CustomerStatementValidator customerStatementValidator;

    @Autowired(required=true)
    public CustomerStatementService(StatementProcessorFactory statementProcessorFactory,
                                    CustomerStatementValidator customerStatementValidator) {

        this.statementProcessorFactory = statementProcessorFactory;
        this.customerStatementValidator = customerStatementValidator;
    }


    /*
    *   This method validates the customer statement records and updates the failed record in list
    *   @param custStmtModel
    * */
    public List<CustomerStatementModel> validateCustStmt(MultipartFile file) {
        LOGGER.info("Inside customer statement validation method");

        List<CustomerStatementModel> customerStatementModelList = convertFileDataToCustStmtModel(file);
        List<CustomerStatementModel> validatedStmtModelList = customerStatementValidator.checkForInvalidTransaction(customerStatementModelList);

        return validatedStmtModelList;
    }

    /*
     * This method calls the corresponding process method through factory method pattern
     */
    private List<CustomerStatementModel> convertFileDataToCustStmtModel(MultipartFile file) {
        ICustomerStatementInterface CustStmtObject = statementProcessorFactory.getStmtProcessor(
                FilenameUtils.getExtension(file.getOriginalFilename()));

        List<CustomerStatementModel> customerStatementModelList = CustStmtObject.process(file);
        return customerStatementModelList;
    }
}
