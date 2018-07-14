package com.rabobank.statement.interfaces;

import com.rabobank.statement.model.CustStmtModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Interface class for process object
 */
public interface ICustStmtInterface {

    public List<CustStmtModel> process(MultipartFile file) ;
}
