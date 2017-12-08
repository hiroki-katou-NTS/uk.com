/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredeteminePolicyServiceImpl.
 */
public class PredeteminePolicyServiceImpl implements PredeteminePolicyService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#validateOneDay(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode,
	 * nts.uk.shr.com.time.TimeWithDayAttr, nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void validateOneDay( PredetemineTimeSetting pred, TimeWithDayAttr startTime,
			TimeWithDayAttr endTime) {
		int predEndTime = pred.getStartDateClock().valueAsMinutes() + pred.getRangeTimeDay().valueAsMinutes();
		int predStartTime = pred.getStartDateClock().valueAsMinutes();
		if (startTime.valueAsMinutes() < predStartTime || endTime.valueAsMinutes() > predEndTime) {
			throw new BusinessException("Msg_516");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#compareWithOneDayRange(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode,
	 * nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime)
	 */
	@Override
	public void compareWithOneDayRange( PredetemineTimeSetting pred, DesignatedTime designatedTime) {
		AttendanceTime oneDayRange = pred.getRangeTimeDay();

		OneDayTime designatedHalfDayTime = designatedTime.getHalfDayTime();
		OneDayTime designatedOneDayTime = designatedTime.getOneDayTime();
		if (designatedHalfDayTime.greaterThanOrEqualTo(oneDayRange)
				|| designatedOneDayTime.greaterThanOrEqualTo(oneDayRange)) {
			throw new BusinessException("Msg_781");
		}
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
	public void compareWithOneDayRange( PredetemineTimeSetting pred,
			LateEarlyGraceTime lateEarlyGraceTime) {
		if (lateEarlyGraceTime.valueAsMinutes() > pred.getRangeTimeDay().valueAsMinutes()) {
			throw new BusinessException("Msg_517");
		}
	}

}
