/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredeteminePolicyServiceImpl.
 */
@Stateless
public class PredeteminePolicyServiceImpl implements PredeteminePolicyService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#validateOneDay(nts.uk.ctx.at.shared.dom.worktime
	 * .predset.PredetemineTimeSetting, nts.uk.shr.com.time.TimeWithDayAttr,
	 * nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public boolean validateOneDay(PredetemineTimeSetting pred, TimeWithDayAttr startTime, TimeWithDayAttr endTime) {
		int predEndTime = pred.getStartDateClock().valueAsMinutes() + pred.getRangeTimeDay().valueAsMinutes();
		int predStartTime = pred.getStartDateClock().valueAsMinutes();
		return !(this.isBetweenTime(startTime, new TimeWithDayAttr(predStartTime), new TimeWithDayAttr(predEndTime))
				&& this.isBetweenTime(endTime, new TimeWithDayAttr(predStartTime), new TimeWithDayAttr(predEndTime)));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#compareWithOneDayRange(nts.uk.ctx.at.shared.dom.
	 * worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime)
	 */
	@Override
	public void compareWithOneDayRange(BundledBusinessException be, PredetemineTimeSetting pred, DesignatedTime designatedTime) {
		AttendanceTime oneDayRange = pred.getRangeTimeDay();

		OneDayTime designatedHalfDayTime = designatedTime.getHalfDayTime();
		OneDayTime designatedOneDayTime = designatedTime.getOneDayTime();
		if (designatedHalfDayTime.greaterThanOrEqualTo(oneDayRange)
				|| designatedOneDayTime.greaterThanOrEqualTo(oneDayRange)) {
			be.addMessage("Msg_781");
		}
	}
	
	/**
	 * Checks if is between time.
	 *
	 * @param time the time
	 * @param startTime the start time
	 * @param endTime the end time
	 * @return true, if is between time
	 */
	private boolean isBetweenTime(TimeWithDayAttr time, TimeWithDayAttr startTime, TimeWithDayAttr endTime){
		return time.greaterThanOrEqualTo(startTime) && time.lessThanOrEqualTo(endTime);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#compareWithOneDayRange(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime)
	 */
	@Override
	public void compareWithOneDayRange(BundledBusinessException be, PredetemineTimeSetting pred,
			LateEarlyGraceTime lateEarlyGraceTime) {
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#validatePredetemineTime(nts.uk.ctx.at.shared.dom
	 * .worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validatePredetemineTime(BundledBusinessException be, PredetemineTimeSetting pred) {
		int predEndTime = pred.getStartDateClock().valueAsMinutes() + pred.getRangeTimeDay().valueAsMinutes();
		int predStartTime = pred.getStartDateClock().valueAsMinutes();
		
		// validate morning
		if (!this.isBetweenTime(pred.getPrescribedTimezoneSetting().getMorningEndTime(),
				new TimeWithDayAttr(predStartTime), new TimeWithDayAttr(predEndTime))) {
			be.addMessage("Msg_516", "KMK003_39");
		}
		
		// validate afternoon
		if (!this.isBetweenTime(pred.getPrescribedTimezoneSetting().getAfternoonStartTime(),
				new TimeWithDayAttr(predStartTime), new TimeWithDayAttr(predEndTime))) {
			be.addMessage("Msg_516", "KMK003_40");
		}
		
	}

}
