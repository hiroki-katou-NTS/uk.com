/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.worktime.predset;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.BooleanGetAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.infra.entity.worktime.predset.KshmtWtComPredTime;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class JpaPredetemineTimeSettingGetMemento.
 */
public class JpaPredetemineTimeSettingGetMemento implements PredetemineTimeSettingGetMemento {

	/** The kwtst work time set. */
	private KshmtWtComPredTime entity;

	/**
	 * Instantiates a new jpa predetemine time setting get memento.
	 *
	 * @param entity the entity
	 * @param lstEntityTime the lst entity time
	 */
	public JpaPredetemineTimeSettingGetMemento(KshmtWtComPredTime entity) {
		super();
		this.entity = entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getCompanyID()
	 */
	@Override
	public String getCompanyId() {
		return this.entity.getKshmtWtComPredTimePK().getCid();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getRangeTimeDay()
	 */
	@Override
	public AttendanceTime getRangeTimeDay() {
		return new AttendanceTime(this.entity.getRangeTimeDay());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#getSiftCD()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.entity.getKshmtWtComPredTimePK().getWorktimeCd());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getAdditionSetID()
	 */
	@Override
	public PredetermineTime getPredTime() {
		return new PredetermineTime(new JpaPredetermineTimeGetMemento(this.entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isNightShift()
	 */
	@Override
	public boolean isNightShift() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getNightShiftAtr());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getPrescribedTimezoneSetting()
	 */
	@Override
	public PrescribedTimezoneSetting getPrescribedTimezoneSetting() {
		return new PrescribedTimezoneSetting(new JpaPrescribedTimezoneSettingGetMemento(entity));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#
	 * getStartDateClock()
	 */
	@Override
	public TimeWithDayAttr getStartDateClock() {
		return new TimeWithDayAttr(this.entity.getStartDateClock());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.predset.PredetemineTimeGetMemento#isPredetermine
	 * ()
	 */
	@Override
	public boolean isPredetermine() {
		return BooleanGetAtr.getAtrByInteger(this.entity.getIsIncludeOt());
	}

}
