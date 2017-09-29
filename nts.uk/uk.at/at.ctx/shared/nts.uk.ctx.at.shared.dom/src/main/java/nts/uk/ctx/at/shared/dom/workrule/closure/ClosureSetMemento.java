/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.List;

import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;

/**
 * The Interface ClosureSetMemento.
 */
public interface ClosureSetMemento {

	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	void setCompanyId(CompanyId companyId);

	/**
	 * Sets the closure id.
	 *
	 * @param closureId the new closure id
	 */
	void setClosureId(Integer closureId);

	/**
	 * Sets the use classification.
	 *
	 * @param useClassification the new use classification
	 */
	void setUseClassification(UseClassification useClassification);

	/**
	 * Sets the closure month.
	 *
	 * @param closureMonth the new closure month
	 */
	void setClosureMonth(CurrentMonth closureMonth);

	/**
	 * Sets the emp codes.
	 *
	 * @param empCodes the new emp codes
	 */
	void setEmpCodes(List<EmploymentCode> empCodes);

	/**
	 * Sets the closure histories.
	 *
	 * @param closureHistories the new closure histories
	 */
	void setClosureHistories(List<ClosureHistory> closureHistories);

}
