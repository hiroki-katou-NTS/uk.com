/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.pattern.work;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingGetMemento;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSet;
import nts.uk.ctx.at.schedule.infra.entity.shift.pattern.work.KscmtWorkMonthSetPK;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class JpaWorkMonthlySettingGetMemento.
 */
public class JpaWorkMonthlySettingGetMemento implements WorkMonthlySettingGetMemento{

	/** The entity. */
	private KscmtWorkMonthSet entity;
	
	/**
	 * Instantiates a new jpa work monthly setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaWorkMonthlySettingGetMemento(KscmtWorkMonthSet entity) {
		if (entity.getKscmtWorkMonthSetPK() == null) {
			entity.setKscmtWorkMonthSetPK(new KscmtWorkMonthSetPK());
		}
		this.entity = entity;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.entity.getKscmtWorkMonthSetPK().getCid());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingGetMemento#getWorkTypeCode()
	 */
	@Override
	public WorkTypeCode getWorkTypeCode() {
		return new WorkTypeCode(this.entity.getWorkTypeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingGetMemento#getWorkingCode()
	 */
	@Override
	public WorkingCode getWorkingCode() {
		return new WorkingCode(this.entity.getWorkingCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingGetMemento#getDate()
	 */
	@Override
	public GeneralDate getYmdK() {
		return this.entity.getKscmtWorkMonthSetPK().getYmdM();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.pattern.work.
	 * WorkMonthlySettingGetMemento#getMonthlyPatternCode()
	 */
	@Override
	public MonthlyPatternCode getMonthlyPatternCode() {
		return new MonthlyPatternCode(this.entity.getKscmtWorkMonthSetPK().getMPatternCd());
	}

}
