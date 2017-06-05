/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;

/**
 * The Interface JobTitleGetMemento.
 */
public interface JobTitleGetMemento {

	/**
	 * Gets the position id.
	 *
	 * @return the position id
	 */
	PositionId getPositionId();

	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	CompanyId getCompanyId();

	/**
	 * Gets the sequence code.
	 *
	 * @return the sequence code
	 */
	 SequenceCode getSequenceCode();

	/**
	 * Gets the period.
	 *
	 * @return the period
	 */
	 Period getPeriod();

	/**
	 * Gets the position code.
	 *
	 * @return the position code
	 */
	 PositionCode getPositionCode();

	/**
	 * Gets the position name.
	 *
	 * @return the position name
	 */
	 PositionName getPositionName();
}
