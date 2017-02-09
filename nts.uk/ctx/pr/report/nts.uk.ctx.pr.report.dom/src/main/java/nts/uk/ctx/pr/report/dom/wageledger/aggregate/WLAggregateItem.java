/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WLCategory;

/**
 * The Class WageLedgerAggregateItem.
 */
@Getter
public class WLAggregateItem {
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category. */
	private WLCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The code. */
	private WLAggregateItemCode code;
	
	/** The name. */
	private WLAggregateItemName name;
	
	/** The show name zero value. */
	private boolean showNameZeroValue;
	
	/** The show value zero value. */
	private boolean showValueZeroValue;
	
	/** The sub items. */
	private Set<String> subItems;
}
