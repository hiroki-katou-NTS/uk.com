/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkRestTimezone;

/**
 * The Interface FlexOffdayWorkTimeGetMemento.
 */
public interface FlexOffdayWorkTimeGetMemento {

	/**
	 * Gets the lst work timezone.
	 *
	 * @return the lst work timezone
	 */
	List<HDWorkTimeSheetSetting> getLstWorkTimezone();
	
	
	/**
	 * Gets the rest timezone.
	 *
	 * @return the rest timezone
	 */
	FlowWorkRestTimezone getRestTimezone();
}
