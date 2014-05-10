package com.taxi.pojos;

import java.math.BigDecimal;



public class LocalChargePojo extends BasePojo {

	private static final long serialVersionUID = 1L;

	private int id;

	private String country;

	private byte isActive;

	private String currency;

	private BigDecimal MCharge;

	private BigDecimal minimumCharge;

	private int roundLevel;

	private int charge;
	
	public int getCharge() {
		return charge;
	}

	public void setCharge(int charge) {
		this.charge = charge;
	}


	public LocalChargePojo() {
		
	}
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
		this.isActive = isActive;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}


	public BigDecimal getMCharge() {
		return this.MCharge;
	}

	public void setMCharge(BigDecimal MCharge) {
		this.MCharge = MCharge;
	}

	public BigDecimal getMinimumCharge() {
		return this.minimumCharge;
	}

	public void setMinimumCharge(BigDecimal minimumCharge) {
		this.minimumCharge = minimumCharge;
	}

	public int getRoundLevel() {
		return this.roundLevel;
	}

	public void setRoundLevel(int roundLevel) {
		this.roundLevel = roundLevel;
	}


	
}
