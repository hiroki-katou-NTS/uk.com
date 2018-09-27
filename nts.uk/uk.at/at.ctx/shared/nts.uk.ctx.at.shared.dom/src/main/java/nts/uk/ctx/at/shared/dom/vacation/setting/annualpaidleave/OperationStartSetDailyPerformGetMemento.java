/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Interface OperationStartSetDailyPerformGetMemento.
 */
public interface OperationStartSetDailyPerformGetMemento {
	
	/**
	 * Gets the company id.
	 *
	 * @return the company id
	 */
	public CompanyId getCompanyId();
	
	/**
	 * Gets the operate start date daily perform.
	 *
	 * @return the operate start date daily perform
	 */
	public Optional<GeneralDate> getOperateStartDateDailyPerform();
}

