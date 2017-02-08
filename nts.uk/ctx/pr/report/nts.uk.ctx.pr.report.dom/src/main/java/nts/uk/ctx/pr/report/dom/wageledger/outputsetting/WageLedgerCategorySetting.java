/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;


import java.util.List;

import lombok.Getter;
import nts.uk.ctx.pr.report.dom.wageledger.PaymentType;
import nts.uk.ctx.pr.report.dom.wageledger.WageLedgerCategory;

/**
 * The Class WageLedgerCategorySetting.
 */
@Getter
public class WageLedgerCategorySetting {
	
	/** The category. */
	private WageLedgerCategory category;
	
	/** The payment type. */
	private PaymentType paymentType;
	
	/** The output items. */
	private List<WageLedgerSettingItem> outputItems;

	/**
	 * Instantiates a new wage ledger category setting.
	 *
	 * @param category the category
	 * @param paymentType the payment type
	 * @param outputItems the output items
	 */
	public WageLedgerCategorySetting(WageLedgerCategory category, PaymentType paymentType,
			List<WageLedgerSettingItem> outputItems) {
		super();
		this.category = category;
		this.paymentType = paymentType;
		this.outputItems = outputItems;
	}
}
