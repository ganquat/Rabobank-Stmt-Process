package com.robobank.statement.processor;

import com.rabobank.statement.exception.CustStmtException;
import com.rabobank.statement.model.CustStmtModel;
import com.rabobank.statement.processor.CSVStmtProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class CSVStmtProcessorTest {

    MockMultipartFile file;

    @Mock
    private String lineseperator;
    @Mock
    private String columnseperator;
    @InjectMocks
    CSVStmtProcessor csvStmtProcessor = new CSVStmtProcessor();
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    InputStream validFile;
    InputStream inValidFile;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(csvStmtProcessor, "lineseperator", "\\n");
        ReflectionTestUtils.setField(csvStmtProcessor, "columnseperator", ",");
        validFile = ClassLoader.getSystemResourceAsStream("records.csv");
        inValidFile = ClassLoader.getSystemResourceAsStream("invalid.csv");
    }

    @Test
    public void processValidTest() throws IOException {
        file = new MockMultipartFile("file", "records.csv", "multipart/form-data", validFile);
        List<CustStmtModel> csvRecords = csvStmtProcessor.process(file);
        Assert.assertNotNull(csvRecords);
        Assert.assertEquals(10, csvRecords.size());
    }

    @Test
    public void processInvalidTest() throws IOException {
        thrown.expect(CustStmtException.class);
        file = new MockMultipartFile("file", "invalid.csv", "multipart/form-data", inValidFile);
        List<CustStmtModel> csvRecords = csvStmtProcessor.process(file);
    }
}
