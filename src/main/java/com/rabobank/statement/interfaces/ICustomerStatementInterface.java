package com.rabobank.statement.interfaces;

import com.rabobank.statement.model.CustomerStatementModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface class for process object
 */
public interface ICustomerStatementInterface {

    public List<CustomerStatementModel> process(MultipartFile file) ;
}
