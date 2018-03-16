/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.statutory.worktime.companyNew;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborHourGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteComTransLaborHourCommand.
 */
@Getter
@Setter
public class DeleteCompanyTransLaborHourCommand implements ComTransLaborHourGetMemento {

	/** The working time setting new. */
	/* 時間. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * CompanyTransLaborHourGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(AppContexts.user().companyId());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * CompanyTransLaborHourGetMemento#getWorkingTimeSettingNew()
	 */
	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		return this.workingTimeSetting;
	}
}
