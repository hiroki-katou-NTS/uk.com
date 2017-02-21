/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.file.pr.app.export.wageledger.data;

import java.util.List;

import lombok.Builder;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class ReportItemDto.
 */
@Builder
public class ReportItemDto {
	
	/** The payment type. */
	public PaymentType paymentType;
	
	/** The category. */
	public WLCategory category;
	
	/** The name. */
	public String name;
	
	/** The monthly datas. */
	public List<MonthlyData> monthlyDatas;
	
	/** The is tax. */
	public Boolean isTax;
	
	/** The is 年末調整前職金額. */
	public Boolean isPositionMoney;
	
	/** The is 年末調整その他金額. */
	public Boolean isOtherMoney;
	
	/** The is net salary. */
	// TODO: wait confirm.
	public Boolean isNetSalary;
	
	/**
	 * The Enum TaxCategory.
	 */
	public enum TaxCategory {
		
		/** The Tax. */
		Tax,
		
		/** The Exemption tax. */
		ExemptionTax,
		
		/** The Income tax. */
		IncomeTax,
		
		/** The Inhabitant tax. */
		InhabitantTax
	}
}
