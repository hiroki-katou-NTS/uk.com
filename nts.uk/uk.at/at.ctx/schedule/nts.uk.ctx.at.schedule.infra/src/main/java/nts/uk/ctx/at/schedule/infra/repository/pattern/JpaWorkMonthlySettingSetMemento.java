/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkingCode;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.KwmmtWorkMonthSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

public class JpaWorkMonthlySettingSetMemento implements WorkMonthlySettingSetMemento {
	
	/** The entity. */
	private KwmmtWorkMonthSet entity;

	/**
	 * Instantiates a new jpa work monthly setting set memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkMonthlySettingSetMemento(KwmmtWorkMonthSet entity) {
		if (entity.getKwmmtWorkMonthSetPK() == null) {
			entity.setKwmmtWorkMonthSetPK(new KwmmtWorkMonthSetPK());
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
		this.entity.getKwmmtWorkMonthSetPK().setCid(companyId.v());
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
	public void setWorkingCode(WorkingCode workingCode) {
		this.entity.setWorkingCd(workingCode.v());
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingSetMemento#setDate(nts.arc.time.GeneralDate)
	 */
	@Override
	public void setDate(GeneralDate date) {
		this.entity.getKwmmtWorkMonthSetPK().setYmdK(date);
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
		this.entity.getKwmmtWorkMonthSetPK().setMPatternCd(monthlyPatternCode.v());
	}

}
