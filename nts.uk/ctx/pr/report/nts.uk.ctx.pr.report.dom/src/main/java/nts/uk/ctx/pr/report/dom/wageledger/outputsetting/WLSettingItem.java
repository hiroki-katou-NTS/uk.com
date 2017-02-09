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
public class WLSettingItem {
	
	/** The linkage code. */
	private String linkageCode;
	
	/** The type. */
	private WLItemType type;
	
	/** The order number. */
	private int orderNumber;

	/**
	 * Instantiates a new wage ledger setting item.
	 *
	 * @param linkageCode the linkage code
	 * @param type the type
	 * @param orderNumber the order number
	 */
	public WLSettingItem(WLSettingItemGetMemento memento) {
		super();
		this.linkageCode = memento.getLinkageCode();
		this.type = memento.getType();
		this.orderNumber = memento.getOrderNumber();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(WLSettingItemSetMemento memento) {
		memento.setLinkageCode(this.linkageCode);
		memento.setOrderNumber(this.orderNumber);
		memento.setType(this.type);
	}
}
