/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;

/**
 * The Interface FlowWorkHolidayTimeZoneSetMemento.
 */
public interface FlWorkHdTzSetMemento {

	/**
	 * Sets the worktime no.
	 *
	 * @param wtNo the new worktime no
	 */
	 void setWorktimeNo(Integer wtNo);

	/**
	 * Sets the use in legal break restrict time.
	 *
	 * @param brTime the new use in legal break restrict time
	 */
	 void setUseInLegalBreakRestrictTime(boolean brTime);

	/**
	 * Sets the in legal break frame no.
	 *
	 * @param brNo the new in legal break frame no
	 */
	 void setInLegalBreakFrameNo(BreakFrameNo brNo);

	/**
	 * Sets the use out legal break restrict time.
	 *
	 * @param brTime the new use out legal break restrict time
	 */
	 void setUseOutLegalBreakRestrictTime(boolean brTime);

	/**
	 * Sets the out legal break frame no.
	 *
	 * @param brNo the new out legal break frame no
	 */
	 void setOutLegalBreakFrameNo(BreakFrameNo brNo);

	/**
	 * Sets the use out legal pub hol restrict time.
	 *
	 * @param uolphrTime the new use out legal pub hol restrict time
	 */
	 void setUseOutLegalPubHolRestrictTime(boolean uolphrTime);

	/**
	 * Sets the out legal pub hol frame no.
	 *
	 * @param no the new out legal pub hol frame no
	 */
	 void setOutLegalPubHolFrameNo(BreakFrameNo no);

	/**
	 * Sets the flow time setting.
	 *
	 * @param ftSet the new flow time setting
	 */
 	void setFlowTimeSetting(FlowTimeSetting ftSet);
}
