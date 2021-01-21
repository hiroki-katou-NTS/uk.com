/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemAtr;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;

/**
 * The Interface FormulaSetMemento.
 */
public interface FormulaSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param cId the new company id
	 */
	void setCompanyId(CompanyId cId);

	/**
	 * Sets the formula id.
	 *
	 * @param id the new formula id
	 */
	void setFormulaId(FormulaId id);

	/**
	 * Sets the optional item no.
	 *
	 * @param optItemNo the new optional item no
	 */
	void setOptionalItemNo(OptionalItemNo optItemNo);

	/**
	 * Sets the formula name.
	 *
	 * @param name the new formula name
	 */
	void setFormulaName(FormulaName name);

	/**
	 * Sets the calc formula setting.
	 *
	 * @param setting the new calc formula setting
	 */
	void setCalcFormulaSetting(CalcFormulaSetting setting);

	/**
	 * Sets the calc formula atr.
	 *
	 * @param atr the new calc formula atr
	 */
	void setFormulaAtr(OptionalItemAtr atr);

	/**
	 * Sets the calc atr.
	 *
	 * @param calcAtr the new calc atr
	 */
	void setCalcAtr(CalculationAtr calcAtr);

	/**
	 * Sets the symbol.
	 *
	 * @param symbol the new symbol
	 */
	void setSymbol(Symbol symbol);

	/**
	 * Sets the monthly rounding.
	 *
	 * @param rounding the new monthly rounding
	 */
	void setMonthlyRounding(Optional<Rounding> rounding);

	/**
	 * Sets the daily rounding.
	 *
	 * @param rounding the new daily rounding
	 */
	void setDailyRounding(Optional<Rounding> rounding);
}
