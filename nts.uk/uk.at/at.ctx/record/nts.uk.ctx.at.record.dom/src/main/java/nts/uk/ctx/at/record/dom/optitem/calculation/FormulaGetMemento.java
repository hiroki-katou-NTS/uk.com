/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OptionalItemFormulaGetMemento.
 */
public interface FormulaGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the optional item formula id.
	 *
	 * @return the optional item formula id
	 */
	FormulaId getOptionalItemFormulaId();

	/**
	 * Gets the optional item no.
	 *
	 * @return the optional item no
	 */
	OptionalItemNo getOptionalItemNo();

	/**
	 * Gets the optional item formula name.
	 *
	 * @return the optional item formula name
	 */
	FormulaName getOptionalItemFormulaName();

	/**
	 * Gets the optional item formula setting.
	 *
	 * @return the optional item formula setting
	 */
	CalcFormulaSetting getOptionalItemFormulaSetting();

	/**
	 * Gets the calculation formula atr.
	 *
	 * @return the calculation formula atr
	 */
	FormulaAtr getCalculationFormulaAtr();

	/**
	 * Gets the symbol.
	 *
	 * @return the symbol
	 */
	Symbol getSymbol();

	/**
	 * Gets the monthly rounding.
	 *
	 * @return the monthly rounding
	 */
	Rounding getMonthlyRounding();

	/**
	 * Gets the daily rounding.
	 *
	 * @return the daily rounding
	 */
	Rounding getDailyRounding();
}
