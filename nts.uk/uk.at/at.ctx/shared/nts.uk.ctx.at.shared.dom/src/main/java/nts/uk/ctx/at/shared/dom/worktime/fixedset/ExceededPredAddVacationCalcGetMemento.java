/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodExceededPredAddVacation;

/**
 * The Interface ExceededPredAddVacationCalcGetMemento.
 */
public interface ExceededPredAddVacationCalcGetMemento {

	/**
	 * Gets the calc method.
	 *
	 * @return the calc method
	 */
	CalcMethodExceededPredAddVacation getCalcMethod();

	/**
	 * Gets the ot frame no.
	 *
	 * @return the ot frame no
	 */
	OverTimeFrameNo getOtFrameNo();
}
