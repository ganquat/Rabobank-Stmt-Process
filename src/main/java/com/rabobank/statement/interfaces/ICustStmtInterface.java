package com.rabobank.statement.interfaces;

import com.rabobank.statement.model.CustStmtModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Ganesh_C01 on 7/13/2018.
 */
public interface ICustStmtInterface {

    public List<CustStmtModel> process(MultipartFile file) ;
}
