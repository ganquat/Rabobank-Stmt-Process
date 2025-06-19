package com.robobank.statement.validator;

import com.rabobank.statement.model.CustomerStatementModel;
import com.rabobank.statement.validator.CustomerStatementValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerStatementValidatorTest {

    CustomerStatementValidator customerStatementValidator;

    List<CustomerStatementModel> customerStatementModelList;

    @Before
    public void setup() {
        customerStatementValidator = new CustomerStatementValidator();
    }

    @Test
    public void checkForInvalidTransactionWithValidDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getValidData(), new HashSet<>());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(false, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(false, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithDuplicateDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getDuplicateData(), new HashSet<>());

        Assert.assertNotNull(ouputData);
        Assert.assertEquals(2, ouputData.size());
        Assert.assertEquals(true, ouputData.get(0).isFailedRecord());
        Assert.assertEquals(true, ouputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForInvalidTransactionWithInvalidMutationDataTest() {
        List<CustomerStatementModel> ouputData = customerStatementValidator.checkForInvalidTransaction(getInvalidMutationData(), new HashSet<>());

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

    @Test
    public void checkForCrossStatementDuplicateTest() {
        Set<Integer> processedReferences = new HashSet<>();
        processedReferences.add(112806); // This reference is also in getValidData() and getDuplicateData()

        List<CustomerStatementModel> recordsToTest = new ArrayList<>();
        // This record has a reference number that is now in processedReferences
        recordsToTest.add(new CustomerStatementModel(112806, "NL27SNSB0917829871", "Clothes for Willem Dekker", 91.23, +15.57, 106.8));
        // This record has a new reference number
        recordsToTest.add(new CustomerStatementModel(999999, "NL69ABNA0433647324", "New item", 50.0, +10.0, 60.0));

        List<CustomerStatementModel> outputData = customerStatementValidator.checkForInvalidTransaction(recordsToTest, processedReferences);

        Assert.assertNotNull(outputData);
        Assert.assertEquals(2, outputData.size());
        Assert.assertTrue("Record with existing reference should be failed", outputData.get(0).isFailedRecord());
        Assert.assertFalse("Record with new reference should not be failed by this check", outputData.get(1).isFailedRecord());
    }

    @Test
    public void checkForAnomalyDetection_VeryLargeMutationTest() {
        List<CustomerStatementModel> recordsToTest = new ArrayList<>();
        recordsToTest.add(new CustomerStatementModel(200001, "NL93ABNA0585619023", "Large Deposit", 100.0, 20000.0, 20100.0)); // Exceeds MAX_MUTATION_THRESHOLD

        List<CustomerStatementModel> outputData = customerStatementValidator.checkForInvalidTransaction(recordsToTest, new HashSet<>());
        Assert.assertNotNull(outputData);
        Assert.assertEquals(1, outputData.size());
        Assert.assertTrue("Record with very large mutation should be failed", outputData.get(0).isFailedRecord());
    }

    @Test
    public void checkForAnomalyDetection_VerySmallMutationTest() {
        List<CustomerStatementModel> recordsToTest = new ArrayList<>();
        recordsToTest.add(new CustomerStatementModel(200002, "NL93ABNA0585619023", "Micro Transaction", 10.0, 0.01, 10.01)); // Below MIN_MUTATION_THRESHOLD

        List<CustomerStatementModel> outputData = customerStatementValidator.checkForInvalidTransaction(recordsToTest, new HashSet<>());
        Assert.assertNotNull(outputData);
        Assert.assertEquals(1, outputData.size());
        Assert.assertTrue("Record with very small mutation should be failed", outputData.get(0).isFailedRecord());
    }

    @Test
    public void checkForAnomalyDetection_ZeroMutationTest() {
        List<CustomerStatementModel> recordsToTest = new ArrayList<>();
        // Zero mutation, should not be flagged by the small mutation check, only by balance check if applicable
        recordsToTest.add(new CustomerStatementModel(200003, "NL93ABNA0585619023", "Balance Inquiry", 10.0, 0.0, 10.0));

        List<CustomerStatementModel> outputData = customerStatementValidator.checkForInvalidTransaction(recordsToTest, new HashSet<>());
        Assert.assertNotNull(outputData);
        Assert.assertEquals(1, outputData.size());
        // Assuming correct balance, this should not be failed by anomaly detection.
        // The balance check (10.0 + 0.0 == 10.0) is correct.
        Assert.assertFalse("Record with zero mutation and correct balance should not be failed", outputData.get(0).isFailedRecord());
    }

    @Test
    public void checkForAnomalyDetection_ValidMutationAtThresholdTest() {
        List<CustomerStatementModel> recordsToTest = new ArrayList<>();
        // Transaction exactly at MIN_MUTATION_THRESHOLD (e.g. 0.05)
        recordsToTest.add(new CustomerStatementModel(200004, "NL93ABNA0585619023", "Small Valid Tx", 10.0, 0.05, 10.05));
        // Transaction exactly at MAX_MUTATION_THRESHOLD (e.g. 10000.0)
        recordsToTest.add(new CustomerStatementModel(200005, "NL93ABNA0585619023", "Large Valid Tx", 100.0, 10000.0, 10100.0));

        List<CustomerStatementModel> outputData = customerStatementValidator.checkForInvalidTransaction(recordsToTest, new HashSet<>());
        Assert.assertNotNull(outputData);
        Assert.assertEquals(2, outputData.size());
        Assert.assertFalse("Record with mutation at MIN_MUTATION_THRESHOLD should be valid", outputData.get(0).isFailedRecord());
        Assert.assertFalse("Record with mutation at MAX_MUTATION_THRESHOLD should be valid", outputData.get(1).isFailedRecord());
    }
}
