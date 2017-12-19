/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import java.util.List;

import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;

/**
 * The Interface FlowStampReflectTimezoneSetMemento.
 */
public interface FlStampReflectTzSetMemento {

	/**
	 * Sets the two times work reflect basic time.
	 *
	 * @param rtwt the new two times work reflect basic time
	 */
	void setTwoTimesWorkReflectBasicTime(ReflectReferenceTwoWorkTime rtwt);

	/**
	 * Sets the stamp reflect timezone.
	 *
	 * @param lstRtz the new stamp reflect timezone
	 */
	void setStampReflectTimezone(List<StampReflectTimezone> lstRtz);
}
