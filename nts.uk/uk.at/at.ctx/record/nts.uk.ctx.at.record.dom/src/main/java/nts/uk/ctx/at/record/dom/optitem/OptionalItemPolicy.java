/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.util.List;

import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;

/**
 * The Interface OptionalItemPolicy.
 */
public interface OptionalItemPolicy {

	/**
	 * Can register list formula.
	 *
	 * @param formulas the formulas
	 * @return true, if successful
	 */
	boolean canRegisterListFormula(List<Formula> formulas);

}
