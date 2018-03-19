/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.statutory.worktime.workplaceNew;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborWorkTimeSetMemento;

/**
 * The Class WkpDeforLaborWorkHourDto.
 */
@Getter
public class WkpDeforLaborWorkHourDto implements WkpDeforLaborWorkTimeSetMemento {


	/** The workplace id. */
	private String workplaceId;

	/** The working time setting new. */
	private WorkingTimeSetting workingTimeSetting;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpDeforLaborWorkHourSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.
	 * common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpDeforLaborWorkHourSetMemento#setWorkplaceId(nts.uk.ctx.at.shared.dom.
	 * common.WorkplaceId)
	 */
	@Override
	public void setWorkplaceId(WorkplaceId workplaceId) {
		this.workplaceId = workplaceId.toString();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.
	 * WkpDeforLaborWorkHourSetMemento#setWorkingTimeSettingNew(nts.uk.ctx.at.
	 * shared.dom.statutory.worktime.sharedNew.WorkingTimeSettingNew)
	 */
	@Override
	public void setWorkingTimeSet(WorkingTimeSetting workingTimeSetting) {
		this.workingTimeSetting = workingTimeSetting;

	}

}
