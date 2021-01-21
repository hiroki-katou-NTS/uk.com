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
 * The Interface FormulaGetMemento.
 */
public interface FormulaGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the formula id.
	 *
	 * @return the formula id
	 */
	FormulaId getFormulaId();

	/**
	 * Gets the optional item no.
	 *
	 * @return the optional item no
	 */
	OptionalItemNo getOptionalItemNo();

	/**
	 * Gets the formula name.
	 *
	 * @return the formula name
	 */
	FormulaName getFormulaName();

	/**
	 * Gets the calc formula setting.
	 *
	 * @return the calc formula setting
	 */
	CalcFormulaSetting getCalcFormulaSetting();

	/**
	 * Gets the calc formula atr.
	 *
	 * @return the calc formula atr
	 */
	CalculationAtr getCalcFormulaAtr();

	/**
	 * Gets the calc formula atr.
	 *
	 * @return the calc formula atr
	 */
	OptionalItemAtr getFormulaAtr();

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
	Optional<Rounding> getMonthlyRounding();

	/**
	 * Gets the daily rounding.
	 *
	 * @return the daily rounding
	 */
	Optional<Rounding> getDailyRounding();
}
