package com.rabobank.statement.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name ="records")

/*
 * POJO class for JAXB binding XML file
 */
public class CustomerStatementList {

    @XmlElement(name ="record")
    private List<CustomerStatementModel> customerStatementModel;

    public CustomerStatementList() { }

    public List<CustomerStatementModel> getcustomerStatementModelList() {
        return customerStatementModel;
    }

    public void setcustomerStatementModelList(List<CustomerStatementModel> customerStatementModel) {
        this.customerStatementModel = customerStatementModel;
    }

}
