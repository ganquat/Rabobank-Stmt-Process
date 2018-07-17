package com.robobank.statement.processor;

import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.model.CustomerStatementModel;
import com.rabobank.statement.processor.CSVStatementProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CSVStatementProcessorTest {

    MockMultipartFile file;

    @InjectMocks
    CSVStatementProcessor csvStatementProcessor = new CSVStatementProcessor(",");
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    InputStream validFile;
    InputStream inValidFile;

    @Before
    public void setup() {
        validFile = ClassLoader.getSystemResourceAsStream("records.csv");
        inValidFile = ClassLoader.getSystemResourceAsStream("invalid.csv");
    }

    @Test
    public void processValidTest() throws IOException {
        file = new MockMultipartFile("file", "records.csv", "multipart/form-data", validFile);
        List<CustomerStatementModel> csvRecords = csvStatementProcessor.process(file);
        Assert.assertNotNull(csvRecords);
        Assert.assertEquals(10, csvRecords.size());
    }

    @Test
    public void processInvalidTest() throws IOException {
        thrown.expect(CustomerStatementException.class);
        file = new MockMultipartFile("file", "invalid.csv", "multipart/form-data", inValidFile);
        List<CustomerStatementModel> csvRecords = csvStatementProcessor.process(file);
    }
}
