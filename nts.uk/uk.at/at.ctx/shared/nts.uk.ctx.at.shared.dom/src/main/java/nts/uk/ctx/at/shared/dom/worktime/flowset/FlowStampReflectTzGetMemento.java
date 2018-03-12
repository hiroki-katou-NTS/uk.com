/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * The Interface FlowStampReflectTimezoneGetMemento.
 */
public interface FlowStampReflectTzGetMemento {

	/**
	 * Gets the two times work reflect basic time.
	 *
	 * @return the two times work reflect basic time
	 */
	ReflectReferenceTwoWorkTime getTwoTimesWorkReflectBasicTime();

	/**
	 * Gets the stamp reflect timezone.
	 *
	 * @return the stamp reflect timezone
	 */
	List<StampReflectTimezone> getStampReflectTimezone();
}
