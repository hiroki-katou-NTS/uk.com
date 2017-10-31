/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;

/**
 * The Class OptionalItemPolicyImpl.
 */
@Stateless
public class OptionalItemPolicyImpl implements OptionalItemPolicy {

	/** The Constant MAXIMUM. */
	private static final int MAXIMUM = 50;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemService#validateFormulaCount
	 * (int)
	 */
	@Override
	public boolean canRegisterListFormula(List<Formula> formulas) {
		if (formulas.size() > MAXIMUM) {
			throw new BusinessException("Msg_762");
		}
		if (isSymbolOverlapped(formulas)) {
			throw new BusinessException("Msg_508");
		}
		return true;
	}

	/**
	 * Checks if is symbol overlapped.
	 *
	 * @param formulas the formulas
	 * @return true, if is symbol overlapped
	 */
	private boolean isSymbolOverlapped(List<Formula> formulas) {
		final long FREQUENCY = 1;
		// return true if symbol frequency > 1
		return formulas.stream().anyMatch(formula -> Collections.frequency(formulas, formula) > FREQUENCY);
	}

}
