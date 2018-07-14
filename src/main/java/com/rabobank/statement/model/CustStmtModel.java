package com.rabobank.statement.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name ="record")

/*
POJO class for Customer statement model
 */
public class CustStmtModel {

	@XmlAttribute(name ="reference")
	private Integer refernceNo;
	@XmlElement(name ="accountNumber")
	private String accNumber;
	@XmlElement(name ="description")
	private String description;
	@XmlElement(name ="startBalance")
	private double startbalance;
	@XmlElement(name ="mutation")
	private double mutation;
	@XmlElement(name ="endBalance")
	private double endbalance;

	private boolean failedRecord;


	public CustStmtModel() {

	}


	public CustStmtModel(Integer refernceNo, String accNumber, String description, double startbalance, double mutation, double endbalance) {
		this.refernceNo = refernceNo;
		this.accNumber = accNumber;
		this.description = description;
		this.startbalance = startbalance;
		this.mutation = mutation;
		this.endbalance = endbalance;
	}

	public boolean isFailedRecord() {
		return failedRecord;
	}

	public void setFailedRecord(boolean failedRecord) {
		this.failedRecord = failedRecord;
	}


	


	public Integer getRefernceNo() {
		return refernceNo;
	}

	public void setRefernceNo(Integer refernceNo) {
		this.refernceNo = refernceNo;
	}

	public String getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(String accNumber) {
		this.accNumber = accNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getStartbalance() {
		return startbalance;
	}

	public void setStartbalance(double startbalance) {
		this.startbalance = startbalance;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public double getEndbalance() {
		return endbalance;
	}

	public void setEndbalance(double endbalance) {
		this.endbalance = endbalance;
	}



}
