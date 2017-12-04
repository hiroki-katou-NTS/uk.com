/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.FlowWorkRestTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.HDWorkTimeSheetSetting;

/**
 * The Interface FlexOffdayWorkTimeSetMemento.
 */
public interface FlexOffdayWorkTimeSetMemento {
	
	
	/**
	 * Sets the work timezone.
	 *
	 * @param workTimezone the new work timezone
	 */
	void setWorkTimezone(List<HDWorkTimeSheetSetting> workTimezone);

	
	/**
	 * Sets the rest timezone.
	 *
	 * @param restTimezone the new rest timezone
	 */
	void setRestTimezone(FlowWorkRestTimezone restTimezone);
}
