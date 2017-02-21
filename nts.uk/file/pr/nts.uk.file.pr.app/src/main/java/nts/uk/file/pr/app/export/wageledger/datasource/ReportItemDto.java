/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.datasource;

import java.util.Map;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;

/**
 * The Class ReportItemDto.
 */
@Builder
public class ReportItemDto {
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The category. */
	public ItemCategory category;
	
	/** The name. */
	public String name;
	
	/** The money in month. */
	public Map<Integer, Double> moneyInMonth;
	
	/**
	 * The Enum ItemCategory.
	 */
	public enum ItemCategory {
		
		/** The Payment. */
		Payment,
		
		/** The Deduction. */
		Deduction,
		
		/** The Attendance. */
		Attendance,
		
		/** The Tax. */
		Tax,
		
		/** The Tax exemption. */
		TaxExemption
	}
	

}
