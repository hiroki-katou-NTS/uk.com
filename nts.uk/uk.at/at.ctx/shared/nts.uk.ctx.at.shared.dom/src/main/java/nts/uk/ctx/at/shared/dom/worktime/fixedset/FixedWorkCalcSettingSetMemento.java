/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

/**
 * The Interface FixedWorkCalcSettingSetMemento.
 */
public interface FixedWorkCalcSettingSetMemento {

	/**
	 * Sets the exceeded pred add vacation calc.
	 *
	 * @param exceededPredAddVacationCalc the new exceeded pred add vacation calc
	 */
	void setExceededPredAddVacationCalc(ExceededPredAddVacationCalc exceededPredAddVacationCalc);

	/**
	 * Sets the over time calc no break.
	 *
	 * @param overTimeCalcNoBreak the new over time calc no break
	 */
	void setOverTimeCalcNoBreak(OverTimeCalcNoBreak overTimeCalcNoBreak);
}
