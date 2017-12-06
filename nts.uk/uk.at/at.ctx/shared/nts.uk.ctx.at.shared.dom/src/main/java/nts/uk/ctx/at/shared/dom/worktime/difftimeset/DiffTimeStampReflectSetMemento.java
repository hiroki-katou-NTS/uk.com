/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * The Interface DiffTimeStampReflectSetMemento.
 */
public interface DiffTimeStampReflectSetMemento {

	/**
	 * Sets the stamp reflect timezone.
	 *
	 * @param stampReflectTimezone the new stamp reflect timezone
	 */
	public void setStampReflectTimezone(StampReflectTimezone stampReflectTimezone);

	/**
	 * Sets the checks if is update start time.
	 *
	 * @param isUpdateStartTime the new checks if is update start time
	 */
	public void setIsUpdateStartTime(boolean isUpdateStartTime);
}
