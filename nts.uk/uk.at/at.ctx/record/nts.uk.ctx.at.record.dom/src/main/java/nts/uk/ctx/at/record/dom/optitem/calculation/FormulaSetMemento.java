/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OptionalItemFormulaSetMemento.
 */
public interface FormulaSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param cId the new company id
	 */
	void setCompanyId(CompanyId cId);

	/**
	 * Sets the optional item formula id.
	 *
	 * @param id the new optional item formula id
	 */
	void setOptionalItemFormulaId(FormulaId id);

	/**
	 * Sets the optional item no.
	 *
	 * @param optItemNo the new optional item no
	 */
	void setOptionalItemNo(OptionalItemNo optItemNo);

	/**
	 * Sets the optional item formula name.
	 *
	 * @param name the new optional item formula name
	 */
	void setOptionalItemFormulaName(FormulaName name);

	/**
	 * Sets the optional item formula setting.
	 *
	 * @param setting the new optional item formula setting
	 */
	void setOptionalItemFormulaSetting(CalcFormulaSetting setting);

	/**
	 * Sets the calculation formula atr.
	 *
	 * @param atr the new calculation formula atr
	 */
	void setCalculationFormulaAtr(FormulaAtr atr);

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
	void setMonthlyRounding(MonthlyRounding rounding);

	/**
	 * Sets the daily rounding.
	 *
	 * @param rounding the new daily rounding
	 */
	void setDailyRounding(DailyRounding rounding);
}
