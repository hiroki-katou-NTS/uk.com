/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.dom.wageledger.outputsetting;

/**
 * The Enum WageLedgerItemType.
 */
public enum WageLedgerItemType {
	
	/** The Master. */
	Master(1),
	
	/** The Aggregate. */
	Aggregate(2);
	
	/** The value. */
	public final Integer value;
	
	/**
	 * Instantiates a new wage ledger item type.
	 *
	 * @param value the value
	 */
	private WageLedgerItemType(Integer value) {
		this.value = value;
	}
}
