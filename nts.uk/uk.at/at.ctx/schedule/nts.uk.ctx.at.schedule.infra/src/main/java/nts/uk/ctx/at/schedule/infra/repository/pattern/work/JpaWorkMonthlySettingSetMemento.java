/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingSetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

public class JpaWorkMonthlySettingSetMemento implements WorkMonthlySettingSetMemento {
	
	/** The entity. */
	private KscmtWorkMonthSet entity;

	/**
	 * Instantiates a new jpa work monthly setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkMonthlySettingSetMemento(KscmtWorkMonthSet entity) {
		if (entity.getKscmtWorkMonthSetPK() == null) {
			entity.setKscmtWorkMonthSetPK(new KscmtWorkMonthSetPK());
		}
		this.entity = entity;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setCompanyId(nts.uk.ctx.at.shared.dom.common
	 * .CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.entity.getKscmtWorkMonthSetPK().setCid(companyId.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setWorkTypeCode(nts.uk.ctx.at.schedule.dom.
	 * shift.pattern.work.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode workTypeCode) {
		this.entity.setWorkTypeCd(workTypeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setWorkingCode(nts.uk.ctx.at.schedule.dom.
	 * shift.pattern.work.WorkingCode)
	 */
	@Override
	public void setWorkingCode(WorkTimeCode workingCode) {
		this.entity.setWorkingCd(workingCode == null ? null : workingCode.v());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setYmdK(GeneralDate ymdk) {
		this.entity.getKscmtWorkMonthSetPK().setYmdM(ymdk);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setMonthlyPatternCode(nts.uk.ctx.at.schedule
	 * .dom.shift.pattern.monthly.MonthlyPatternCode)
	 */
	@Override
	public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode) {
		this.entity.getKscmtWorkMonthSetPK().setMPatternCd(monthlyPatternCode.v());
	}

}
