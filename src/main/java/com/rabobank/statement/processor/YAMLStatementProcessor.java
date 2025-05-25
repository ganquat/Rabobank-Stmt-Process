package com.rabobank.statement.processor;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import com.rabobank.statement.model.CustomerStatementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Component
public class YAMLStatementProcessor implements ICustomerStatementInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(YAMLStatementProcessor.class);
    private final ObjectMapper objectMapper;

    public YAMLStatementProcessor() {
        this.objectMapper = new ObjectMapper(new YAMLFactory());
    }

    @Override
    public List<CustomerStatementModel> process(MultipartFile file) {
        LOGGER.info("Inside YAML statement processor method");
        try (InputStream inputStream = file.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<CustomerStatementModel>>() {});
        } catch (Exception e) {
            LOGGER.error("Exception in YAML process method: {}", e.getMessage(), e);
            // It's good practice to wrap the original exception
            throw new CustomerStatementException("Error reading or parsing YAML file: " + file.getOriginalFilename(), e);
        }
    }
}
