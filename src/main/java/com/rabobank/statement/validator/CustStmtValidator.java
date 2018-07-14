package com.rabobank.statement.validator;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.model.CustStmtModel;

import java.util.List;


/*
 * Checks for invalid transaction from the list of customer statement model list
 */
public class CustStmtValidator implements CustStmtConstant {

    public List<CustStmtModel> checkForInvalidTransaction(List<CustStmtModel> custStmtModelList){

        for (CustStmtModel custModel :  custStmtModelList) {
            //Calculate and check the end balance value
            if (Math.round((custModel.getStartBalance() + custModel.getMutation()) *
                    ROUND_TWO_DECIMAL)/ROUND_TWO_DECIMAL == custModel.getEndBalance()) {
                // If end balance is correct, loop through all values and check duplicate ref no
                for (CustStmtModel custModel1 : custStmtModelList) {
                    if ((custModel.getRefernceNo().equals(custModel1.getRefernceNo())
                            && (!custModel.equals(custModel1))))
                        //duplicate reference no
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
