package com.robobank.statement.service;

import com.rabobank.statement.exception.CustStmtException;
import com.rabobank.statement.model.CustStmtModel;
import com.rabobank.statement.processor.CSVStmtProcessor;
import com.rabobank.statement.processor.StmtProcessorFactory;
import com.rabobank.statement.processor.XMLStmtProcessor;
import com.rabobank.statement.service.CustStmtService;
import com.rabobank.statement.validator.CustStmtValidator;
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
public class CustStmtServiceTest {

    MockMultipartFile file;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    XMLStmtProcessor xmlStmtProcessor = new XMLStmtProcessor();

    @Mock
    CSVStmtProcessor csvStmtProcessor = new CSVStmtProcessor(",");

    @InjectMocks
    StmtProcessorFactory stmtProcessorFactory;

    @Mock
    CustStmtValidator custStmtValidator = new CustStmtValidator();

    @InjectMocks
    CustStmtService custStmtService;

    InputStream validFile;
    InputStream inValidFile;

    @Before
    public void setup(){
         stmtProcessorFactory = new StmtProcessorFactory(csvStmtProcessor,xmlStmtProcessor);
        custStmtService = new CustStmtService(stmtProcessorFactory,custStmtValidator);
        validFile = ClassLoader.getSystemResourceAsStream("records.xml");
        inValidFile = ClassLoader.getSystemResourceAsStream("invalid.xml");

    }

    @Test
    public void validateCustStmtWithValidDataTest() throws IOException {
        file = new MockMultipartFile("file", "records.xml", "multipart/form-data", validFile);
        List<CustStmtModel> records = custStmtService.validateCustStmt(file);
        Assert.assertNotNull(records);
        Assert.assertEquals(false,records.get(0).isFailedRecord());
        Assert.assertEquals(true,records.get(1).isFailedRecord());
    }

    @Test
    public void validateCustStmtWithInValidDataTest() throws IOException{
        thrown.expect(CustStmtException.class);
        file = new MockMultipartFile("file", "invalid.xml", "multipart/form-data", inValidFile);
        List<CustStmtModel> Records = custStmtService.validateCustStmt(file);
    }
}
