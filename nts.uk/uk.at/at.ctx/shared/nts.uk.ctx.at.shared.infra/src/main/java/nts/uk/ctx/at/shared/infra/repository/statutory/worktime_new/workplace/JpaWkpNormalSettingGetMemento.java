/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.workplace;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.statutory.worktime_new.workingplace.KshstWkpNormalSet;
import nts.uk.ctx.at.shared.infra.repository.statutory.worktime_new.share.JpaDefaultSettingGetMemento;

/**
 * The Class JpaWkpNormalSettingGetMemento.
 */
public class JpaWkpNormalSettingGetMemento extends JpaDefaultSettingGetMemento implements WkpNormalSettingGetMemento {

	private KshstWkpNormalSet entity;

	public JpaWkpNormalSettingGetMemento(KshstWkpNormalSet entity) {
		super(entity);
		this.entity = entity;
	}

	@Override
	public Year getYear() {
		return new Year(this.entity.getKshstWkpNormalSetPK().getYear());
	}

	@Override
	public List<MonthlyUnit> getStatutorySetting() {
		return toMonthlyUnitsFromNormalSet();
	}

	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKshstWkpNormalSetPK().getCid());
	}

	@Override
	public WorkplaceId getWorkplaceId() {
		return new WorkplaceId(this.entity.getKshstWkpNormalSetPK().getWkpId());
	}

}
