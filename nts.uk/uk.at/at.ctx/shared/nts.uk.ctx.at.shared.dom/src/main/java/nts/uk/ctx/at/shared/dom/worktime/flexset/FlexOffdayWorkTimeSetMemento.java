/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;

/**
 * The Interface FlexOffdayWorkTimeSetMemento.
 */
public interface FlexOffdayWorkTimeSetMemento {
	
	
	/**
	 * Sets the lst work timezone.
	 *
	 * @param lstWorkTimezone the new lst work timezone
	 */
	void setLstWorkTimezone(List<HDWorkTimeSheetSetting> lstWorkTimezone);

	
	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	void setRestTimezone(FlowWorkRestTimezone restTimezone);
}
