/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;

/**
 * The Interface EmTimezoneChangeExtentSetMemento.
 */
public interface EmTimezoneChangeExtentSetMemento {
	
	/**
	 * Sets the ahead change.
	 *
	 * @param aheadChange the new ahead change
	 */
	public void setAheadChange(AttendanceTime aheadChange);

    /**
     * Sets the unit.
     *
     * @param unit the new unit
     */
    public void setUnit(InstantRounding unit);

    /**
     * Sets the behind change.
     *
     * @param behindChange the new behind change
     */
    public void setBehindChange(AttendanceTime behindChange);
}
