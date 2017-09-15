/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.optionalitem.calculationformula.disporder;

import nts.uk.ctx.at.record.dom.optionalitem.OptionalItemNo;
import nts.uk.ctx.at.record.dom.optionalitem.calculationformula.OptionalItemFormulaId;
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
	OptionalItemFormulaId getOptionalItemFormulaId();

	/**
	 * Gets the disp order.
	 *
	 * @return the disp order
	 */
	int getDispOrder();
}
