/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredetemineTimeSettingDto.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PredetemineTimeSettingDto implements PredetemineTimeSettingGetMemento {

	/** The range time day. */
	public int rangeTimeDay;

	/** The work time code. */
	public String workTimeCode;

	/** The pred time. */
	public PredetermineTimeDto predTime;

	/** The night shift. */
	public boolean nightShift;

	/** The prescribed timezone setting. */
	public PrescribedTimezoneSettingDto prescribedTimezoneSetting;

	/** The start date clock. */
	public int startDateClock;

	/** The predetermine. */
	public boolean predetermine;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getCompanyId()
	 */
	@Override
	public String getCompanyId() {
		return AppContexts.user().companyId();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getRangeTimeDay()
	 */
	@Override
	public AttendanceTime getRangeTimeDay() {
		return new AttendanceTime(this.rangeTimeDay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.workTimeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getPredTime()
	 */
	@Override
	public PredetermineTime getPredTime() {
		return new PredetermineTime(this.predTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#isNightShift()
	 */
	@Override
	public boolean isNightShift() {
		return this.nightShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getPrescribedTimezoneSetting()
	 */
	@Override
	public PrescribedTimezoneSetting getPrescribedTimezoneSetting() {
		return new PrescribedTimezoneSetting(this.prescribedTimezoneSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#getStartDateClock()
	 */
	@Override
	public TimeWithDayAttr getStartDateClock() {
		return new TimeWithDayAttr(this.startDateClock);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSettingGetMemento#isPredetermine()
	 */
	@Override
	public boolean isPredetermine() {
		return this.predetermine;
	}

}
