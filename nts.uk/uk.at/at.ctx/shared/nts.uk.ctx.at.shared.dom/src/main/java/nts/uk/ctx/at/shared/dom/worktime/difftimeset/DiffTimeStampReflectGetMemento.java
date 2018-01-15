/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * The Interface DiffTimeStampReflectGetMemento.
 */
public interface DiffTimeStampReflectGetMemento {
	
	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	public StampReflectTimezone getStampReflectTimezone();

	/**
	 * Checks if is checks if is update start time.
	 *
	 * @return true, if is checks if is update start time
	 */
	public boolean isIsUpdateStartTime();
}
