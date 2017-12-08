/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.predset.dto;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetermineTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredetemineTimeSetDto.
 */
public class PredetemineTimeSetDto implements PredetemineTimeSettingSetMemento {

	private static final Integer TRUE_VAL = 1;
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
	public void setCompanyId(String companyID) {
		this.companyId = companyID;
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
		BreakDownTimeDayDto addTimeDto = BreakDownTimeDayDto.builder()
				.oneDay(predTime.getAddTime().getOneDay().v())
				.morning(predTime.getAddTime().getMorning().v())
				.afternoon(predTime.getAddTime().getAfternoon().v())
				.build();
		BreakDownTimeDayDto predTimeDto = BreakDownTimeDayDto.builder()
				.oneDay(predTime.getPredTime().getOneDay().v())
				.morning(predTime.getPredTime().getMorning().v())
				.afternoon(predTime.getPredTime().getAfternoon().v())
				.build();
		this.predTime = PredetermineTimeDto.builder()
				.addTime(addTimeDto)
				.predTime(predTimeDto)
				.build();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setNightShift(boolean)
	 */
	@Override
	public void setNightShift(boolean nightShift) {
		this.nightShift = nightShift;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.predset.PredetemineTimeSetMemento#setPrescribedTimezoneSetting(nts.uk.ctx.at.shared.dom.predset.PrescribedTimezoneSetting)
	 */
	@Override
	public void setPrescribedTimezoneSetting(PrescribedTimezoneSetting prescribedTimezoneSetting) {
		List<TimezoneDto> timezoneDtos = prescribedTimezoneSetting.getLstTimezone().stream().map(item->{
			return TimezoneDto.builder()
					.workNo(item.getWorkNo())
					.useAtr(item.getUseAtr().value == TRUE_VAL)
					.start(item.getStart().v())
					.end(item.getEnd().v()).build();
		}).collect(Collectors.toList());
		
		this.prescribedTimezoneSetting= PrescribedTimezoneSettingDto.builder()
				.morningEndTime(prescribedTimezoneSetting.getMorningEndTime().v())
				.afternoonStartTime(prescribedTimezoneSetting.getAfternoonStartTime().v())
				.timezone(timezoneDtos)
				.build();
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
