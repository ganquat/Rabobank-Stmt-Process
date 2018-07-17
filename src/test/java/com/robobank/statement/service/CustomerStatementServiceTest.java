package com.robobank.statement.service;

import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.model.CustomerStatementModel;
import com.rabobank.statement.processor.CSVStatementProcessor;
import com.rabobank.statement.processor.StatementProcessorFactory;
import com.rabobank.statement.processor.XMLStatementProcessor;
import com.rabobank.statement.service.CustomerStatementService;
import com.rabobank.statement.validator.CustomerStatementValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Ganesh_C01 on 7/16/2018.
 */
public class CustomerStatementServiceTest {

    MockMultipartFile file;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    XMLStatementProcessor xmlStatementProcessor = new XMLStatementProcessor();

    @Mock
    CSVStatementProcessor csvStatementProcessor = new CSVStatementProcessor(",");

    @InjectMocks
    StatementProcessorFactory statementProcessorFactory;

    @Mock
    CustomerStatementValidator customerStatementValidator = new CustomerStatementValidator();

    @InjectMocks
    CustomerStatementService customerStatementService;

    InputStream validFile;
    InputStream inValidFile;

    @Before
    public void setup(){
         statementProcessorFactory = new StatementProcessorFactory(csvStatementProcessor, xmlStatementProcessor);
        customerStatementService = new CustomerStatementService(statementProcessorFactory, customerStatementValidator);
        validFile = ClassLoader.getSystemResourceAsStream("records.xml");
        inValidFile = ClassLoader.getSystemResourceAsStream("invalid.xml");

    }

    @Test
    public void validateCustStmtWithValidDataTest() throws IOException {
        file = new MockMultipartFile("file", "records.xml", "multipart/form-data", validFile);
        List<CustomerStatementModel> records = customerStatementService.validateCustStmt(file);
        Assert.assertNotNull(records);
        Assert.assertEquals(false,records.get(0).isFailedRecord());
        Assert.assertEquals(true,records.get(1).isFailedRecord());
    }

    @Test
    public void validateCustStmtWithInValidDataTest() throws IOException{
        thrown.expect(CustomerStatementException.class);
        file = new MockMultipartFile("file", "invalid.xml", "multipart/form-data", inValidFile);
        List<CustomerStatementModel> Records = customerStatementService.validateCustStmt(file);
    }
}
