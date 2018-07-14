package com.robobank.statement.validator;

import com.rabobank.statement.model.CustStmtModel;
import com.rabobank.statement.validator.CustStmtValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CustStmtValidatorTest {

    CustStmtValidator custStmtValidator;

    List<CustStmtModel> custStmtModelList;

    @Before
    public void setup() {
        custStmtValidator = new CustStmtValidator();
    }

    @Test
    public void checkForInvalidTransactionWithValidDataTest() {
        List<CustStmtModel> ouputData = custStmtValidator.checkForInvalidTransaction(getValidData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(false, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(false, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithDuplicateDataTest() {
        List<CustStmtModel> ouputData = custStmtValidator.checkForInvalidTransaction(getDuplicateData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(true, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(true, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithInvalidMutationDataTest() {
        List<CustStmtModel> ouputData = custStmtValidator.checkForInvalidTransaction(getInvalidMutationData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(1, ouputData.size());
        Assert.assertEquals(true, ouputData.get(0).isFailedRecord());
    }

    private List<CustStmtModel> getDuplicateData() {
        List<CustStmtModel> duplicateData = new ArrayList<CustStmtModel>();
        duplicateData.add(new CustStmtModel(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, -10.91, 79.92));
        duplicateData.add(new CustStmtModel(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, -10.91, 79.92));
        return duplicateData;
    }

    private List<CustStmtModel> getInvalidMutationData() {
        List<CustStmtModel> invalidData = new ArrayList<CustStmtModel>();
        invalidData.add(new CustStmtModel(165102, "NL93ABNA0585619023", "Tickets for Rik Theuß", 3980, +1000, 4981));
        return invalidData;
    }

    private List<CustStmtModel> getValidData() {
        List<CustStmtModel> validData = new ArrayList<CustStmtModel>();
        validData.add(new CustStmtModel(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, +15.57, 106.8));
        validData.add(new CustStmtModel(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, +44.5, 131.16));
        return validData;
    }
}
