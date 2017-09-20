/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optitem.calculation.disporder;

import nts.uk.ctx.at.record.dom.optitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaId;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface FormulaDispOrderGetMemento.
 */
public interface FormulaDispOrderGetMemento {

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the optional item no.
	 *
	 * @return the optional item no
	 */
	OptionalItemNo getOptionalItemNo();

	/**
	 * Gets the optional item formula id.
	 *
	 * @return the optional item formula id
	 */
	FormulaId getFormulaId();

	/**
	 * Gets the disp order.
	 *
	 * @return the disp order
	 */
	DispOrder getDispOrder();
}
