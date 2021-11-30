/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import java.util.List;

/**
 * The Interface WorkTimezoneLateEarlySetGetMemento.
 */
public interface WorkTimezoneLateEarlySetGetMemento {

	/**
  * Gets the common set.
  *
  * @return the common set
  */
 TreatLateEarlyTime getCommonSet();

	/**
	 * Gets the other class set.
	 *
	 * @return the other class set
	 */
	 List<OtherEmTimezoneLateEarlySet> getOtherClassSet();
}
