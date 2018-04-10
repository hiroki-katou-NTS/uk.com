/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset;

/**
 * The Interface FixedWorkCalcSettingGetMemento.
 */
public interface FixedWorkCalcSettingGetMemento {

	/**
	 * Gets the exceeded pred add vacation calc.
	 *
	 * @return the exceeded pred add vacation calc
	 */
	ExceededPredAddVacationCalc getExceededPredAddVacationCalc();

	/**
	 * Gets the over time calc no break.
	 *
	 * @return the over time calc no break
	 */
	OverTimeCalcNoBreak getOverTimeCalcNoBreak();
}
