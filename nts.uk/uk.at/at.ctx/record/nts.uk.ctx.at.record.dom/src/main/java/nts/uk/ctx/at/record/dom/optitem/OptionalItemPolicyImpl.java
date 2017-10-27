/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem;

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
		return true;
	}

}
