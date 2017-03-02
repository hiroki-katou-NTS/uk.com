/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.element;

import lombok.Getter;

/**
 * The Class RangeItem.
 */
@Getter
public class RangeItem {

	/** The order number. */
	private Integer orderNumber;

	/** The start val. */
	private Integer startVal;

	/** The end val. */
	private Integer endVal;

	/** The uuid. */
	private String uuid;

	/**
	 * Instantiates a new range item.
	 *
	 * @param orderNumber the order number
	 * @param startVal the start val
	 * @param endVal the end val
	 * @param uuid the uuid
	 */
	public RangeItem(Integer orderNumber, Integer startVal, Integer endVal, String uuid) {
		super();
		this.orderNumber = orderNumber;
		this.startVal = startVal;
		this.endVal = endVal;
		this.uuid = uuid;
	}

}
