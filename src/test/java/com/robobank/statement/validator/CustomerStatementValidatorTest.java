package com.robobank.statement.validator;

import com.rabobank.statement.model.CustomerStatementModel;
import com.rabobank.statement.validator.CustomerStatementValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CustomerStatementValidatorTest {

    CustomerStatementValidator customerStatementValidator;

    List<CustomerStatementModel> customerStatementModelList;

    @Before
    public void setup() {
        customerStatementValidator = new CustomerStatementValidator();
    }

    @Test
    public void checkForInvalidTransactionWithValidDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getValidData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(false, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(false, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithDuplicateDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getDuplicateData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(true, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(true, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithInvalidMutationDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getInvalidMutationData());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(1, ouputData.size());
        Assert.assertEquals(true, ouputData.get(0).isFailedRecord());
    }

    private List<CustomerStatementModel> getDuplicateData() {
        List<CustomerStatementModel> duplicateData = new ArrayList<CustomerStatementModel>();
        duplicateData.add(new CustomerStatementModel(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, -10.91, 79.92));
        duplicateData.add(new CustomerStatementModel(112806, "NL69ABNA0433647324", "Clothes for Richard de Vries", 90.83, -10.91, 79.92));

        return duplicateData;
    }

    private List<CustomerStatementModel> getInvalidMutationData() {
        List<CustomerStatementModel> invalidData = new ArrayList<CustomerStatementModel>();
        invalidData.add(new CustomerStatementModel(165102, "NL93ABNA0585619023", "Tickets for Rik Theuß", 3980, +1000, 4981));
        return invalidData;
    }

    private List<CustomerStatementModel> getValidData() {
        List<CustomerStatementModel> validData = new ArrayList<CustomerStatementModel>();
        validData.add(new CustomerStatementModel(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, +15.57, 106.8));
        validData.add(new CustomerStatementModel(183049, "NL69ABNA0433647324", "Clothes for Jan King", 86.66, +44.5, 131.16));
        return validData;
    }
}
