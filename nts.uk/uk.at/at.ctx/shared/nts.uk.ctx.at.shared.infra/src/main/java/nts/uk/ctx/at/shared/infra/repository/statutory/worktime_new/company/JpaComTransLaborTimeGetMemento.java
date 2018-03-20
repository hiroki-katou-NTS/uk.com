/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.company;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeGetMemento;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.company.KshstComTransLabTime;

/**
 * The Class JpaCompanySettingGetMemento.
 */
public class JpaComTransLaborTimeGetMemento implements ComTransLaborTimeGetMemento {
	
	private CompanyId companyId;

	private WorkingTimeSetting workingTimeSet;

	public JpaComTransLaborTimeGetMemento(KshstComTransLabTime entity) {
		this.companyId = new CompanyId(entity.getCid());
		// TODO: this.workingTimeSet
	}

	@Override
	public CompanyId getCompanyId() {
		return this.companyId;
	}

	@Override
	public WorkingTimeSetting getWorkingTimeSet() {
		return null;
	}

}
