/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.List;

/**
 * The Interface ItemSelectionGetMemento.
 */
public interface ItemSelectionGetMemento {

	/**
	 * Gets the minus segment.
	 *
	 * @return the minus segment
	 */
	MinusSegment getMinusSegment();

	/**
	 * Gets the list selected attendance item.
	 *
	 * @return the list selected attendance item
	 */
	List<SelectedAttendanceItem> getListSelectedAttendanceItem();
}
