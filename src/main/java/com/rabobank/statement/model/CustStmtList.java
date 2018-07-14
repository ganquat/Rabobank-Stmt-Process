package com.rabobank.statement.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name ="records")

/*
POJO class for JAXB binding XML file
 */
public class CustStmtList {


    @XmlElement(name ="record")
    private List<CustStmtModel> custStmtModel;

    public CustStmtList() {

    }

    public List<CustStmtModel> getCustStmtModelList() {
        return custStmtModel;
    }

    public void setCustStmtModelList(List<CustStmtModel> custStmtModel) {
        this.custStmtModel = custStmtModel;
    }

}
