/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.Formula;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaSetting;

/**
 * The Class OptionalItemPolicyImpl.
 */
@Stateless
public class OptionalItemPolicyImpl implements OptionalItemPolicy {

	/** The Constant MAXIMUM_FORMULA_COUNT. */
	private static final int MAXIMUM_FORMULA_COUNT = 50;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.OptionalItemService#validateFormulaCount
	 * (int)
	 */
	@Override
	public boolean canRegister(OptionalItem optItem, List<Formula> formulas) {
		if (formulas.size() > MAXIMUM_FORMULA_COUNT) {
			throw new BusinessException("Msg_762");
		}
		if (isSymbolOverlapped(formulas)) {
			throw new BusinessException("Msg_508");
		}
		formulas.forEach(formula -> {
			if (!this.isFormulaSettingValid(formula, formulas)) {
				throw new BusinessException("Msg_114");
			}
		});
		return true;
	}

	/**
	 * Checks if is symbol overlapped.
	 *
	 * @param formulas the formulas
	 * @return true, if is symbol overlapped
	 */
	private boolean isSymbolOverlapped(List<Formula> formulas) {
		Set<String> symbols = new HashSet<String>();
		return formulas.stream().anyMatch(formula -> {
			return symbols.add(formula.getSymbol().v()) == false;
		});
	}

	/**
	 * Checks if is formula setting valid.
	 *
	 * @param formula the formula
	 * @return true, if is formula setting valid
	 */
	private boolean isFormulaSettingValid(Formula formula, List<Formula> formulas) {
		if(formula.getCalcFormulaSetting().getFormulaSetting().isPresent()) {
			FormulaSetting formulaSetting = formula.getCalcFormulaSetting().getFormulaSetting().get();
	
			if (formula.getCalcAtr().equals(CalculationAtr.FORMULA_SETTING) && formulaSetting.isBothItemSelect()
					&& formulaSetting.isOperatorAddOrSub()) {
	
				// get formula by id
				Formula leftItem = formulas.stream()
						.filter(item -> item.getFormulaId().equals(formulaSetting.getLeftItem().getFormulaItemId().get()))
						.findFirst().get();
				Formula rightItem = formulas.stream()
						.filter(item -> item.getFormulaId().equals(formulaSetting.getRightItem().getFormulaItemId().get()))
						.findFirst().get();
	
				// compare left item's attribute vs right item's attribute
				return leftItem.getFormulaAtr().equals(rightItem.getFormulaAtr());
			}
		}
		return true;
	}

}
