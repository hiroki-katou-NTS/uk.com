/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.companyNew;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeSetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;

/**
 * The Class ComTransLaborHourDto.
 */
@Getter
@Setter
public class ComTransLaborHourDto implements ComTransLaborTimeSetMemento {

	/** The working time setting. */
	/** 会社労働時間設定. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborHourSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common.
	 * CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// do nothing

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.
	 * ComTransLaborHourSetMemento#setWorkingTimeSet(nts.uk.ctx.at.shared.dom.
	 * statutory.worktime.sharedNew.WorkingTimeSetting)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;

	}

}
