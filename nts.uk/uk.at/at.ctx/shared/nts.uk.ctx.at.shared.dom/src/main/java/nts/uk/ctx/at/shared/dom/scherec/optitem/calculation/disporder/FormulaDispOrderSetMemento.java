/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.disporder;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemNo;
import nts.uk.ctx.at.shared.dom.scherec.optitem.calculation.FormulaId;

/**
 * The Interface FormulaDispOrderSetMemento.
 */
public interface FormulaDispOrderSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param comId the new company id
	 */
	void setCompanyId(CompanyId comId);

	/**
	 * Sets the optional item no.
	 *
	 * @param optNo the new optional item no
	 */
	void setOptionalItemNo(OptionalItemNo optNo);

	/**
	 * Sets the optional item formula id.
	 *
	 * @param formulaId the new optional item formula id
	 */
	void setOptionalItemFormulaId(FormulaId formulaId);

	/**
	 * Sets the disp order.
	 *
	 * @param dispOrder the new disp order
	 */
	void setDispOrder(DispOrder dispOrder);
}
