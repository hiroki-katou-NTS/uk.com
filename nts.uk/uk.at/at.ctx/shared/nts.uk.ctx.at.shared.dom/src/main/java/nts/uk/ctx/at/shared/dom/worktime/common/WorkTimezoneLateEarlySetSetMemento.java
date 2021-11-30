/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

/**
 * The Interface WorkTimezoneLateEarlySetSetMemento.
 */
public interface WorkTimezoneLateEarlySetSetMemento {

	/**
	 * Sets the common set.
	 *
	 * @param set the new common set
	 */
	void setCommonSet(TreatLateEarlyTime set);

	/**
	 * Sets the other class set.
	 *
	 * @param list the new other class set
	 */
	 void setOtherClassSet(List<OtherEmTimezoneLateEarlySet> list);
}
