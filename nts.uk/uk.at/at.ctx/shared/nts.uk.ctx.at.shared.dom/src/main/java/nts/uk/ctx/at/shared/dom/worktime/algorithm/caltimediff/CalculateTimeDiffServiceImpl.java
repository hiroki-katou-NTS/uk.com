/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.algorithm.caltimediff;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.CollectionAtr;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.JoggingWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktype.DailyWork;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class CalculateTimeDiffServiceImpl.
 */
@Stateless
public class CalculateTimeDiffServiceImpl implements CalculateTimeDiffService {

	/** The pre time set repo. */
	@Inject
	public PredetemineTimeSettingRepository preTimeSetRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.algorithm.caltimediff.
	 * CalculateTimeDiffService#caculateJoggingWorkTime(nts.uk.shr.com.time.
	 * TimeWithDayAttr, nts.uk.ctx.at.shared.dom.worktype.DailyWork,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PrescribedTimezoneSetting)
	 */
	@Override
	public JoggingWorkTime caculateJoggingWorkTime(TimeWithDayAttr scheduleStartClock, DailyWork dailyWork,
			PrescribedTimezoneSetting prescribedTimezone) {
		JoggingWorkTime joggingWorkTime = new JoggingWorkTime();

		boolean isAfternoon = dailyWork.getMorning().equals(WorkTypeClassification.Holiday)
				|| dailyWork.getMorning().equals(WorkTypeClassification.Pause)
				|| dailyWork.getMorning().equals(WorkTypeClassification.AnnualHoliday)
				|| dailyWork.getMorning().equals(WorkTypeClassification.YearlyReserved)
				|| dailyWork.getMorning().equals(WorkTypeClassification.SpecialHoliday)
				|| dailyWork.getMorning().equals(WorkTypeClassification.TimeDigestVacation)
				|| dailyWork.getMorning().equals(WorkTypeClassification.SubstituteHoliday)
				|| dailyWork.getMorning().equals(WorkTypeClassification.Absence);

		boolean isOneDayOrMorning = dailyWork.getWorkTypeUnit().equals(WorkTypeUnit.OneDay) || !isAfternoon;

		TimeWithDayAttr startTime = isOneDayOrMorning ? prescribedTimezone.getTimezoneShiftOne().getStart()
				: prescribedTimezone.getAfternoonStartTime();

		// set worktime values
		int calculatedTime = scheduleStartClock.valueAsMinutes() - startTime.valueAsMinutes();
		joggingWorkTime.setAtr(calculatedTime > 0 ? CollectionAtr.AFTER : CollectionAtr.BEFORE);
		joggingWorkTime.setTime(new AttendanceTime(Math.abs(calculatedTime)));

		return joggingWorkTime;
	}

}
