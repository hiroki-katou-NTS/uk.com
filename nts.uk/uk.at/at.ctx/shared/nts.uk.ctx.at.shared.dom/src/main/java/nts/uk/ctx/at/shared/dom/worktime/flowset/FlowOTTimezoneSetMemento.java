/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flowset;

import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.SettlementOrder;

/**
 * The Interface FlowOTTimezoneSetMemento.
 */
public interface FlowOTTimezoneSetMemento {

	/**
	 * Sets the worktime no.
	 *
	 * @param no the new worktime no
	 */
	void setWorktimeNo(Integer no);

	/**
	 * Sets the restrict time.
	 *
	 * @param val the new restrict time
	 */
	 void setRestrictTime(boolean val);

	/**
	 * Sets the OT frame no.
	 *
	 * @param no the new OT frame no
	 */
	 void setOTFrameNo(OvertimeWorkFrameNo no);

	/**
	 * Sets the flow time setting.
	 *
	 * @param ftSet the new flow time setting
	 */
	 void setFlowTimeSetting(FlowTimeSetting ftSet);

	/**
	 * Sets the in legal OT frame no.
	 *
	 * @param no the new in legal OT frame no
	 */
	 void setInLegalOTFrameNo(OvertimeWorkFrameNo no);

	/**
	 * Sets the settlement order.
	 *
	 * @param od the new settlement order
	 */
	 void setSettlementOrder(SettlementOrder od);
}
