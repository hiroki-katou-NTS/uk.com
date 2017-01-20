/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.aggregate;

import java.util.Set;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WageLedgerCategory;

/**
 * The Class WageLedgerAggregateItem.
 */
@Getter
public class WageLedgerAggregateItem {
	
	/** The company code. */
	private CompanyCode companyCode;
	
	/** The category. */
	private WageLedgerCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The code. */
	private AggregateItemCode code;
	
	/** The name. */
	private AggregateItemName name;
	
	/** The show name zero value. */
	private boolean showNameZeroValue;
	
	/** The show value zero value. */
	private boolean showValueZeroValue;
	
	/** The sub items. */
	private Set<String> subItems;
}
