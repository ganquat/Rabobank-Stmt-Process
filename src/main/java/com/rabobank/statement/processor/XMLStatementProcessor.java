package com.rabobank.statement.processor;

import com.rabobank.statement.constants.CustomerStatementConstant;
import com.rabobank.statement.exception.CustomerStatementException;
import com.rabobank.statement.interfaces.ICustomerStatementInterface;
import com.rabobank.statement.model.CustomerStatementList;
import com.rabobank.statement.model.CustomerStatementModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.util.List;

/**
 * XML statement processor
 */
@Component
public class XMLStatementProcessor implements CustomerStatementConstant,ICustomerStatementInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLStatementProcessor.class);

    public XMLStatementProcessor(){

    }
    /*
    * This method reads the xml data from multipart input file and unmarshalls it to Customer statement list object
    * and sends the list for validation
    * @param file
    * */
    public List<CustomerStatementModel> process(MultipartFile file) {
        LOGGER.info("Inside XML statement processor method");
        List<CustomerStatementModel> customerStatementModel = null;
        try {
            JAXBContext context = JAXBContext.newInstance(CustomerStatementList.class);
            Unmarshaller um = context.createUnmarshaller();
            CustomerStatementList customerStatementList = (CustomerStatementList) um.unmarshal(file.getInputStream());
            customerStatementModel = customerStatementList.getcustomerStatementModelList();
        } catch (Exception e) {
            CustomerStatementException customerStatementException = new CustomerStatementException("Error reading XML file:", e);
            LOGGER.info("Exception in XML process method : {}", e.getMessage());
            throw customerStatementException;
        }
        return customerStatementModel;
    }
}
