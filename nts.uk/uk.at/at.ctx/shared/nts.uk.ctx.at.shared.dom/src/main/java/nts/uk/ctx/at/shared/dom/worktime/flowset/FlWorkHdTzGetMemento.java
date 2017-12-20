/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;

/**
 * The Interface FlowWorkHolidayTimeZoneGetMemento.
 */
public interface FlWorkHdTzGetMemento {

	/**
	 * Gets the worktime no.
	 *
	 * @return the worktime no
	 */
	 Integer getWorktimeNo();

	/**
	 * Gets the use in legal break restrict time.
	 *
	 * @return the use in legal break restrict time
	 */
	 boolean getUseInLegalBreakRestrictTime();

	/**
	 * Gets the in legal break frame no.
	 *
	 * @return the in legal break frame no
	 */
	 BreakFrameNo getInLegalBreakFrameNo();

	/**
	 * Gets the use out legal break restrict time.
	 *
	 * @return the use out legal break restrict time
	 */
	 boolean getUseOutLegalBreakRestrictTime();

	/**
	 * Gets the out legal break frame no.
	 *
	 * @return the out legal break frame no
	 */
	 BreakFrameNo getOutLegalBreakFrameNo();

	/**
	 * Gets the use out legal pub hol restrict time.
	 *
	 * @return the use out legal pub hol restrict time
	 */
	 boolean getUseOutLegalPubHolRestrictTime();

	/**
	 * Gets the out legal pub hol frame no.
	 *
	 * @return the out legal pub hol frame no
	 */
	 BreakFrameNo getOutLegalPubHolFrameNo();

	/**
	 * Gets the flow time setting.
	 *
	 * @return the flow time setting
	 */
 	FlowTimeSetting getFlowTimeSetting();
}
