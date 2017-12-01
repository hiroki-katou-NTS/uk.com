/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.InstantRounding;

/**
 * The Interface EmTimezoneChangeExtentGetMemento.
 */
public interface EmTimezoneChangeExtentGetMemento {
	
	/**
	 * Gets the ahead change.
	 *
	 * @return the ahead change
	 */
	public AttendanceTime getAheadChange();

    /**
     * Gets the unit.
     *
     * @return the unit
     */
    public InstantRounding getUnit();

    /**
     * Gets the behind change.
     *
     * @return the behind change
     */
    public AttendanceTime getBehindChange();
}
