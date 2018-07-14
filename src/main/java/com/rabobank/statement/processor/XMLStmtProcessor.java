package com.rabobank.statement.processor;

import com.rabobank.statement.constants.CustStmtConstant;
import com.rabobank.statement.exception.CustStmtException;
import com.rabobank.statement.interfaces.ICustStmtInterface;
import com.rabobank.statement.model.CustStmtList;
import com.rabobank.statement.model.CustStmtModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.util.*;

/**
 * Created by Ganesh_C01 on 7/13/2018.
 */
@Component
public class XMLStmtProcessor implements CustStmtConstant,ICustStmtInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(XMLStmtProcessor.class);
    /*
       This method reads the xml data from multipart input file and unmarshalls it to Customer statement list object
       and sends the list for validation
       @param file
    */
    public List<CustStmtModel> process(MultipartFile file) {
        LOGGER.info("Inside XML statement processor method");
        List<CustStmtModel> custStmtModel= null;
        try {
            JAXBContext context = JAXBContext.newInstance(CustStmtList.class);
            Unmarshaller um = context.createUnmarshaller();
            CustStmtList custStmtList = (CustStmtList) um.unmarshal(file.getInputStream());
            custStmtModel = custStmtList.getCustStmtModelList();


        } catch (Exception e) {
            CustStmtException custStmtException = new CustStmtException("Error reading XML file:",e);
            throw custStmtException;
        }

        return custStmtModel;

    }

}
