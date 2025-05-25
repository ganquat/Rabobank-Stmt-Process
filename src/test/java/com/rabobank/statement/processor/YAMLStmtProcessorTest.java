package com.rabobank.statement.processor;

import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.model.CustomerStatementModel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.*;

public class YAMLStmtProcessorTest {

    private YAMLStatementProcessor yamlStatementProcessor;

    @Before
    public void setUp() {
        yamlStatementProcessor = new YAMLStatementProcessor();
    }

    @Test
    public void testProcessValidYAMLFile() throws IOException {
        // Load the sample YAML file
        InputStream inputStream = new FileInputStream("src/test/resources/records.yaml");
        MultipartFile multipartFile = new MockMultipartFile("records.yaml", "records.yaml", "application/x-yaml", inputStream);

        List<CustomerStatementModel> statements = yamlStatementProcessor.process(multipartFile);

        assertNotNull(statements);
        assertEquals(5, statements.size()); // Check if all records are parsed

        // Verify first record
        CustomerStatementModel firstRecord = statements.get(0);
        assertEquals(194261, firstRecord.getRefernceNo());
        assertEquals("NL91RABO0315273637", firstRecord.getAccNumber());
        assertEquals("Clothes from Jan Bakker", firstRecord.getDescription());
        assertEquals(21.6, firstRecord.getStartBalance(), 0.001);
        assertEquals(-41.83, firstRecord.getMutation(), 0.001);
        assertEquals(-10.23, firstRecord.getEndBalance(), 0.001);
        
        // Verify a record with positive mutation
        CustomerStatementModel secondRecord = statements.get(1);
        assertEquals(112806, secondRecord.getRefernceNo());
        assertEquals("NL27SNSB0917829871", secondRecord.getAccNumber());
        assertEquals(15.57, secondRecord.getMutation(), 0.001);
        assertEquals(106.8, secondRecord.getEndBalance(), 0.001);

    }

    @Test(expected = CustomerStatementException.class)
    public void testProcessInvalidYAMLFile() throws IOException {
        String invalidYamlContent = "refernceNo: 123\naccNumber: NL-INVALID-ACC\n  description: Invalid YAML"; // Malformed YAML
        MultipartFile multipartFile = new MockMultipartFile("invalid.yaml", "invalid.yaml", "application/x-yaml", invalidYamlContent.getBytes());
        yamlStatementProcessor.process(multipartFile); // This should throw CustomerStatementException
    }

    @Test(expected = CustomerStatementException.class)
    public void testProcessEmptyFile() throws IOException {
        MultipartFile multipartFile = new MockMultipartFile("empty.yaml", "empty.yaml", "application/x-yaml", new byte[0]);
        yamlStatementProcessor.process(multipartFile); // This should throw CustomerStatementException or similar
    }
    
    @Test
    public void testProcessYAMLFileWithIncorrectFieldType() throws IOException {
        String yamlContentWithWrongType = 
            "- refernceNo: \"ShouldBeANumber\"\n" + // String instead of int
            "  accNumber: NL91RABO0315273637\n" +
            "  description: \"Clothes from Jan Bakker\"\n" +
            "  startBalance: 21.6\n" +
            "  mutation: -41.83\n" +
            "  endBalance: -10.23";
        MultipartFile multipartFile = new MockMultipartFile("wrongtype.yaml", "wrongtype.yaml", "application/x-yaml", yamlContentWithWrongType.getBytes());
        
        try {
            yamlStatementProcessor.process(multipartFile);
            fail("Expected CustomerStatementException due to type mismatch");
        } catch (CustomerStatementException e) {
            // Expected
            assertTrue(e.getMessage().contains("Error reading or parsing YAML file"));
        }
    }
}
