/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.wagetable.history.element.item;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.wagetable.ElementId;

/**
 * The Class RangeItem.
 */
@Getter
public class RangeItem extends BaseItem {

	/** The order number. */
	private Integer orderNumber;

	/** The start val. */
	private Double startVal;

	/** The end val. */
	private Double endVal;

	/**
	 * Instantiates a new range item.
	 *
	 * @param orderNumber
	 *            the order number
	 * @param startVal
	 *            the start val
	 * @param endVal
	 *            the end val
	 * @param uuid
	 *            the uuid
	 */
	public RangeItem(Integer orderNumber, Double startVal, Double endVal, ElementId uuid) {
		super(uuid);
		this.orderNumber = orderNumber;
		this.startVal = startVal;
		this.endVal = endVal;
		this.setDisplayName(startVal + " ~ " + endVal);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RangeItem)) {
			return false;
		}
		RangeItem other = (RangeItem) object;
		if ((this.orderNumber == null && other.orderNumber != null)
				|| (this.orderNumber != null && !this.orderNumber.equals(other.orderNumber))) {
			return false;
		}
		if ((this.startVal == null && other.startVal != null)
				|| (this.startVal != null && !this.startVal.equals(other.startVal))) {
			return false;
		}
		if ((this.endVal == null && other.endVal != null)
				|| (this.endVal != null && !this.endVal.equals(other.endVal))) {
			return false;
		}
		return true;
	}

}
