/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;

/**
 * The Interface ExceededPredAddVacationCalcSetMemento.
 */
public interface ExceededPredAddVacationCalcSetMemento {

	/**
	 * Sets the calc method.
	 *
	 * @param calcMethod the new calc method
	 */
	void setCalcMethod(CalcMethodExceededPredAddVacation calcMethod);

	/**
	 * Sets the ot frame no.
	 *
	 * @param otFrameNo the new ot frame no
	 */
	void setOtFrameNo(OverTimeFrameNo otFrameNo);
}
