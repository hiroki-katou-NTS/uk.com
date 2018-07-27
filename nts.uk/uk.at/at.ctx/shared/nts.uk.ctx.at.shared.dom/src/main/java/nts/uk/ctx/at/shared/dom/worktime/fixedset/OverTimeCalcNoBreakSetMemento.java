/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;

/**
 * The Interface OverTimeCalcNoBreakSetMemento.
 */
public interface OverTimeCalcNoBreakSetMemento {

	/**
	 * Sets the calc method.
	 *
	 * @param calcMethod the new calc method
	 */
	void setCalcMethod(CalcMethodNoBreak calcMethod);

	/**
	 * Sets the in law OT.
	 *
	 * @param inLawOT the new in law OT
	 */
	void setInLawOT(OTFrameNo inLawOT);

	/**
	 * Sets the not in law OT.
	 *
	 * @param notInLawOT the new not in law OT
	 */
	void setNotInLawOT(OTFrameNo notInLawOT);

}
