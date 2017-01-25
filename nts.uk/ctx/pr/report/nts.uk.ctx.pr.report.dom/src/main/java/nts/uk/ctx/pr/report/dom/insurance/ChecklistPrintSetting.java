/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.insurance;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;

/**
 * The Class ChecklistPrintSetting.
 */
@Getter
public class ChecklistPrintSetting {
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The show category insurance item. */
	private Boolean showCategoryInsuranceItem;
	
	/** The show delivery notice amount. */
	private Boolean showDeliveryNoticeAmount;
	
	/** The show detail. */
	private Boolean showDetail;
	
	/** The show office. */
	private Boolean showOffice;
}
