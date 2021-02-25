/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.List;

/**
 * The Interface ItemSelectionSetMemento.
 */
public interface ItemSelectionSetMemento {

	/**
	 * Sets the minus segment.
	 *
	 * @param segment the new minus segment
	 */
	void setMinusSegment(MinusSegment segment);

	/**
	 * Sets the list selected attendance item.
	 *
	 * @param items the new list selected attendance item
	 */
	void setListSelectedAttendanceItem(List<SelectedAttendanceItem> items);
}
