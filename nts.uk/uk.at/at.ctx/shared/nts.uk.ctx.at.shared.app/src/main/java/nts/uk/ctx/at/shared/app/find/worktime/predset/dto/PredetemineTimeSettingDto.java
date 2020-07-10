/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredetemineTimeSettingDto.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredetemineTimeSettingDto implements PredetemineTimeSettingSetMemento {

	/** The company id. */
	public String companyId;

	/** The range time day. */
	public int rangeTimeDay;

	/** The sift CD. */
	public String workTimeCode;

	/** The addition set ID. */
	public PredetermineTimeDto predTime;

	/** The night shift. */
	public boolean nightShift;

	/** The prescribed timezone setting. */
	public PrescribedTimezoneSettingDto prescribedTimezoneSetting;

	/** The start date clock. */
	public int startDateClock;

	/** The predetermine. */
	public boolean predetermine;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setCompanyID(java.lang.String)
	 */
	@Override
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setRangeTimeDay(int)
	 */
	@Override
	public void setRangeTimeDay(AttendanceTime rangeTimeDay) {
		this.rangeTimeDay = rangeTimeDay.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setSiftCD(java.lang.String)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode workTimeCode) {
		this.workTimeCode = workTimeCode.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setAdditionSetID(java.lang.String)
	 */
	@Override
	public void setPredTime(PredetermineTime predTime) {
		this.predTime = new PredetermineTimeDto();
		predTime.saveToMemento(this.predTime);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setNightShift(boolean)
	 */
	@Override
	public void setNightShift(boolean nightShift) {
		this.nightShift = nightShift;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#
	 * setPrescribedTimezoneSetting(nts.uk.ctx.at.shared.dom.predset.
	 * PrescribedTimezoneSetting)
	 */
	@Override
	public void setPrescribedTimezoneSetting(PrescribedTimezoneSetting prescribedTimezoneSetting) {
		this.prescribedTimezoneSetting = new PrescribedTimezoneSettingDto();
		prescribedTimezoneSetting.saveToMemento(this.prescribedTimezoneSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setStartDateClock(int)
	 */
	@Override
	public void setStartDateClock(TimeWithDayAttr startDateClock) {
		this.startDateClock = startDateClock.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setPredetermine(boolean)
	 */
	@Override
	public void setPredetermine(boolean predetermine) {
		this.predetermine = predetermine;
	}
}
