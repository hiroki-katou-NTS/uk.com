/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Class CompanyRegularLaborHourDto.
 */
@Getter
public class ComRegularLaborHourDto implements ComRegularLaborTimeSetMemento {


	/** The working time setting new. */
	/** 会社労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * CompanyRegularLaborHourSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// Do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * CompanyRegularLaborHourSetMemento#setWorkingTimeSettingNew(nts.uk.ctx.at.
	 * shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;
	}

}
