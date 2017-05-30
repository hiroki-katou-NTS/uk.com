/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.dom.company.organization.jobtitle;

import nts.uk.ctx.basic.dom.common.history.Period;

/**
 * The Interface JobTitleSetMemento.
 */
public interface JobTitleSetMemento {

	/**
	 * Sets the position id.
	 *
	 * @param positionId the new position id
	 */
	 void setPositionId(PositionId positionId);

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	 void setCompanyId(CompanyId companyId);

	/**
	 * Sets the sequence code.
	 *
	 * @param sequenceCode the new sequence code
	 */
	  void setSequenceCode(SequenceCode sequenceCode);

	/**
	 * Sets the period.
	 *
	 * @param period the new period
	 */
	  void setPeriod(Period period);

	/**
	 * Sets the position code.
	 *
	 * @param positionCode the new position code
	 */
	  void setPositionCode(PositionCode positionCode);

	/**
	 * Sets the position name.
	 *
	 * @param positionName the new position name
	 */
	  void setPositionName(PositionName positionName);
}
