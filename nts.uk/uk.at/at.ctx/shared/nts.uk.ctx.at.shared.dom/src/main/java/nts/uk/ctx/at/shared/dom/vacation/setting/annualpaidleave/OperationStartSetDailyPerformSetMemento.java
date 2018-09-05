/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OperationStartSetDailyPerformSetMemento.
 */
public interface OperationStartSetDailyPerformSetMemento {
	
	/**
	 * Sets the company id.
	 *
	 * @param companyId the new company id
	 */
	public void setCompanyId(CompanyId companyId);
	
	/**
	 * Sets the operate start date daily perform.
	 *
	 * @param operateStartDateDailyPerform the new operate start date daily perform
	 */
	public void setOperateStartDateDailyPerform(Optional<GeneralDate> operateStartDateDailyPerform);
}

