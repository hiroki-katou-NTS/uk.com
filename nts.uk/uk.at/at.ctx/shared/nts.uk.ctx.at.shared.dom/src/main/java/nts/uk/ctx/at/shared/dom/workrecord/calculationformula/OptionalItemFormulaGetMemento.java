/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrecord.calculationformula;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.workrecord.OptionalItemNo;

/**
 * The Interface OptionalItemFormulaGetMemento.
 */
public interface OptionalItemFormulaGetMemento {

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
	OptionalItemFormulaId getOptionalItemFormulaId();

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
	OptionalItemFormulaName getOptionalItemFormulaName();

	/**
	 * Gets the optional item formula setting.
	 *
	 * @return the optional item formula setting
	 */
	OptionalItemFormulaSetting getOptionalItemFormulaSetting();

	/**
	 * Gets the calculation formula attribute.
	 *
	 * @return the calculation formula attribute
	 */
	CalculationFormulaAttribute getCalculationFormulaAttribute();

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
	MonthlyRounding getMonthlyRounding();

	/**
	 * Gets the daily rounding.
	 *
	 * @return the daily rounding
	 */
	DailyRounding getDailyRounding();
}
