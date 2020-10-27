/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTime;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTimePK;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPredetemineTimeSettingSetMemento.
 */
public class JpaPredetemineTimeSettingSetMemento implements PredetemineTimeSettingSetMemento {

	/** The kshmt pred time set. */
	private KshmtWtComPredTime entity;

	public JpaPredetemineTimeSettingSetMemento(KshmtWtComPredTime entity) {
		super();
		if (entity.getKshmtWtComPredTimePK() == null) {
			entity.setKshmtWtComPredTimePK(new KshmtWtComPredTimePK());
		}
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setCompanyId(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.entity.getKshmtWtComPredTimePK().setCid(companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setWorkTimeCode(nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.entity.getKshmtWtComPredTimePK().setWorktimeCd(workTimeCode.v());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setRangeTimeDay(nts.uk.ctx.at.shared.dom.common.time.AttendanceTime)
	 */
	@Override
	public void setRangeTimeDay(AttendanceTime rangeTimeDay) {
		this.entity.setRangeTimeDay(rangeTimeDay.valueAsMinutes());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setPredTime(nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime)
	 */
	@Override
	public void setPredTime(PredetermineTime predTime) {
		predTime.saveToMemento(new JpaPredetermineTimeSetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setNightShift(boolean)
	 */
	@Override
	public void setNightShift(boolean nightShift) {
		this.entity.setNightShiftAtr(BooleanGetAtr.getAtrByBoolean(nightShift));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setPrescribedTimezoneSetting(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PrescribedTimezoneSetting)
	 */
	@Override
	public void setPrescribedTimezoneSetting(PrescribedTimezoneSetting prescribedTimezoneSetting) {
		prescribedTimezoneSetting.saveToMemento(new JpaPrescribedTimezoneSettingSetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setStartDateClock(nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void setStartDateClock(TimeWithDayAttr startDateClock) {
		this.entity.setStartDateClock(startDateClock.valueAsMinutes());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetMemento#
	 * setPredetermine(boolean)
	 */
	@Override
	public void setPredetermine(boolean predetermine) {
		this.entity.setIsIncludeOt(BooleanGetAtr.getAtrByBoolean(predetermine));
	}

}
