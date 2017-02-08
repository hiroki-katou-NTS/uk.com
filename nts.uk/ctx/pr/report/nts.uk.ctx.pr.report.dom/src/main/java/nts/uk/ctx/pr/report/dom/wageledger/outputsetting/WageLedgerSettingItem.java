/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

import lombok.Getter;

/**
 * The Class WageLedgerSettingItem.
 */
@Getter
public class WageLedgerSettingItem {
	
	/** The linkage code. */
	private String linkageCode;
	
	/** The type. */
	private WageLedgerItemType type;
	
	/** The order number. */
	private int orderNumber;

	/**
	 * Instantiates a new wage ledger setting item.
	 *
	 * @param linkageCode the linkage code
	 * @param type the type
	 * @param orderNumber the order number
	 */
	public WageLedgerSettingItem(String linkageCode, WageLedgerItemType type, int orderNumber) {
		super();
		this.linkageCode = linkageCode;
		this.type = type;
		this.orderNumber = orderNumber;
	}
}
