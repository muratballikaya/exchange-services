package com.exchange.services.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "conversion")
public class Conversion {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "source_currency", nullable = false)
	private String sourceCurrency;

	@Column(name = "target_currency", nullable = false)
	private String targetCurrency;

	@Column(name = "source_amount", nullable = false)
	private Float sourceAmount;

	@Column(name = "target_amount", nullable = false)
	private Float targetAmount;
	
	@Column(name="date",nullable = false)
	private Date date;

	public Conversion() {
		
	}
	public Conversion(String sourceCurrency, Float sourceAmount, String targetCurrency) throws ParseException {
		this.sourceCurrency = sourceCurrency;
		this.sourceAmount = sourceAmount;
		this.targetCurrency = targetCurrency;
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		date= sdf.parse(sdf.format(new Date()));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getSourceAmount() {
		return sourceAmount;
	}

	public void setSourceAmount(Float sourceAmount) {
		this.sourceAmount = sourceAmount;
	}

	public String getSourceCurrency() {
		return sourceCurrency;
	}

	public void setSourceCurrency(String sourceCurrency) {
		this.sourceCurrency = sourceCurrency;
	}

	public Float getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Float targetAmount) {
		this.targetAmount = targetAmount;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
}
