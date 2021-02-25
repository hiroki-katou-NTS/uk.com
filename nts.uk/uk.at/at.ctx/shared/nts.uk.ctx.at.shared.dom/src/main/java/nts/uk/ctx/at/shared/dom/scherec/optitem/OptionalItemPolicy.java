/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.List;

import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;

/**
 * The Interface OptionalItemPolicy.
 */
public interface OptionalItemPolicy {

	/**
	 * Can register.
	 *
	 * @param optItem the opt item
	 * @param formulas the formulas
	 * @return true, if successful
	 */
	boolean canRegister(OptionalItem optItem, List<Formula> formulas);

}
