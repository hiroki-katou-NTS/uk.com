/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;

/**
 * The Interface FixOffdayWorkTimezoneGetMemento.
 */
public interface FixOffdayWorkTimezoneGetMemento {

	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
    TimezoneOfFixedRestTimeSet getRestTimezone();

	/**
	 * Gets the lst work timezone.
	 *
	 * @return the lst work timezone
	 */
	List<HDWorkTimeSheetSetting> getLstWorkTimezone();
	
}
