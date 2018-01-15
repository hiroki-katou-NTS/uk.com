/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;

/**
 * The Interface FlowOTTimezoneGetMemento.
 */
public interface FlOTTimezoneGetMemento {

	/**
	 * Gets the worktime no.
	 *
	 * @return the worktime no
	 */
	Integer getWorktimeNo();

	/**
	 * Gets the restrict time.
	 *
	 * @return the restrict time
	 */
	 boolean getRestrictTime();

	/**
	 * Gets the OT frame no.
	 *
	 * @return the OT frame no
	 */
	 OvertimeWorkFrameNo getOTFrameNo();

	/**
	 * Gets the flow time setting.
	 *
	 * @return the flow time setting
	 */
	 FlowTimeSetting getFlowTimeSetting();

	/**
	 * Gets the in legal OT frame no.
	 *
	 * @return the in legal OT frame no
	 */
	 OvertimeWorkFrameNo getInLegalOTFrameNo();

	/**
	 * Gets the settlement order.
	 *
	 * @return the settlement order
	 */
	 SettlementOrder getSettlementOrder();
}
