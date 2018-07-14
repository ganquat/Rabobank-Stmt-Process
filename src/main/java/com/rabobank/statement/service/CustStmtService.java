package com.rabobank.statement.service;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.factory.StmtProcessorFactory;
import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.model.CustStmtList;
import com.rabobank.statement.model.CustStmtModel;
import com.rabobank.statement.processor.XMLStmtProcessor;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import java.util.List;

/**
 * Created by Ganesh_C01 on 7/13/2018.
 */
@Service
public class CustStmtService implements CustStmtConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustStmtService.class);

    @Autowired
    private XMLStmtProcessor custStmtProcessor;

    @Autowired
    private StmtProcessorFactory stmtProcessorFactory;

    /*
    This method validates the customer statement records and updates the failed record in list
    @param custStmtModel

     */
    public List<CustStmtModel> validateCustStmt(MultipartFile file) {
        LOGGER.info("Inside customer statement validation method");

        List<CustStmtModel> custStmtModelList = convertFileDataToCustStmtModel(file);
        List<CustStmtModel> validatedStmtModelList = checkForInvalidTransaction(custStmtModelList);

        return validatedStmtModelList;
    }

    /*
    This method calls the corresponding process method through factory method pattern
     */
    public List<CustStmtModel> convertFileDataToCustStmtModel(MultipartFile file) {
        ICustStmtInterface CustStmtObject = stmtProcessorFactory.getStmtProcessor(
                FilenameUtils.getExtension(file.getOriginalFilename()));

        List<CustStmtModel> custStmtModelList = CustStmtObject.process(file);
        return custStmtModelList;
    }

    /*
    This method check for invalid transaction from the list of customer statement model list
     */
    public List<CustStmtModel> checkForInvalidTransaction(List<CustStmtModel> custStmtModelList){

        for (CustStmtModel custModel :  custStmtModelList) {

            //Calculate and check the end balance value
            if (Math.round((custModel.getStartbalance() + custModel.getMutation())*ROUND_TWO_DECIMAL)/ROUND_TWO_DECIMAL == custModel.getEndbalance()) {
                // If end balance is correct, loop through all values and check duplicate ref no
                for (CustStmtModel custModel1 : custStmtModelList) {
                    if ((custModel.getRefernceNo().equals(custModel1.getRefernceNo())
                            && (!custModel.equals(custModel1))))
                        //duplicate refno
                        custModel.setFailedRecord(true);
                }
            }
            else
                //end balance is not correct
                custModel.setFailedRecord(true);
        }
        return custStmtModelList;
    }

}
