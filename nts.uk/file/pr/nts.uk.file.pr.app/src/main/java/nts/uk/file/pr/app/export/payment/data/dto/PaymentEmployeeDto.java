/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.payment.data.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class EmployeeDto.
 */
@Getter
@Setter
public class PaymentEmployeeDto {
	
	/** The employee code. */
	private String employeeCode;
	
	/** The employee name. */
	private String employeeName;
	
	/** The postal code. */
	private String postalCode;
	
	/** The address one. */
	private String addressOne;
	
	/** The address two. */
	private String addressTwo;
	
	/** The tax total. */
	private BigDecimal taxTotal;
	
	/** The tax exemptiontotal. */
	private BigDecimal taxExemptionTotal;
	
	/** The payment total. */
	private BigDecimal paymentTotal;
	
	/** The social insurance total. */
	private BigDecimal socialInsuranceTotal;
	
	/** The taxable amount. */
	private BigDecimal taxableAmount;
	
	/** The deduction total. */
	private BigDecimal deductionTotal;
	
	/** The subscription amount. */
	private BigDecimal subscriptionAmount;
	
	/** The taxable total. */
	private BigDecimal taxableTotal;
	
	
	
	
	
}
