package com.rabobank.statement.model;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name ="record")

/*
 * POJO class for Customer statement model
 */
public class CustomerStatementModel {

	@XmlAttribute(name ="reference")
	private Integer refernceNo;
	@XmlElement(name ="accountNumber")
	private String accNumber;
	@XmlElement(name ="description")
	private String description;
	@XmlElement(name ="startBalance")
	private double startBalance;
	@XmlElement(name ="mutation")
	private double mutation;
	@XmlElement(name ="endBalance")
	private double endBalance;

	private boolean failedRecord;

	public CustomerStatementModel() { }

	public CustomerStatementModel(Integer refernceNo,
								  String accNumber,
								  String description,
								  double startBalance,
								  double mutation,
								  double endBalance) {
		this.refernceNo = refernceNo;
		this.accNumber = accNumber;
		this.description = description;
		this.startBalance = startBalance;
		this.mutation = mutation;
		this.endBalance = endBalance;
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

	public double getStartBalance() {
		return startBalance;
	}

	public void setStartBalance(double startBalance) {
		this.startBalance = startBalance;
	}

	public double getMutation() {
		return mutation;
	}

	public void setMutation(double mutation) {
		this.mutation = mutation;
	}

	public double getEndBalance() {
		return endBalance;
	}

	public void setEndBalance(double endBalance) {
		this.endBalance = endBalance;
	}

}
