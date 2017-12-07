/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.predset.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DesignatedTime;
import nts.uk.ctx.at.shared.dom.worktime.common.LateEarlyGraceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.OneDayTime;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetRepository;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class PredeteminePolicyServiceImpl.
 */
@Stateless
public class PredeteminePolicyServiceImpl implements PredeteminePolicyService {

	/** The predetemine time set repository. */
	@Inject
	private PredetemineTimeSetRepository predetemineTimeSetRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.predset.service.
	 * PredeteminePolicyService#validateOneDay(java.lang.String,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode,
	 * nts.uk.shr.com.time.TimeWithDayAttr, nts.uk.shr.com.time.TimeWithDayAttr)
	 */
	@Override
	public void validateOneDay(String companyId, WorkTimeCode worktimeCode, TimeWithDayAttr startTime,
			TimeWithDayAttr endTime) {

		PredetemineTimeSet pred = this.getPredByWorkTimeCode(companyId, worktimeCode);

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
	public void compareWithOneDayRange(String companyId, WorkTimeCode worktimeCode, DesignatedTime designatedTime) {

		PredetemineTimeSet pred = this.getPredByWorkTimeCode(companyId, worktimeCode);

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
	public void compareWithOneDayRange(String companyId, WorkTimeCode worktimeCode,
			LateEarlyGraceTime lateEarlyGraceTime) {
		PredetemineTimeSet pred = this.getPredByWorkTimeCode(companyId, worktimeCode);
		if (lateEarlyGraceTime.valueAsMinutes() > pred.getRangeTimeDay().valueAsMinutes()) {
			throw new BusinessException("Msg_517");
		}
	}

	private PredetemineTimeSet getPredByWorkTimeCode(String companyId, WorkTimeCode worktimeCode) {
		return predetemineTimeSetRepository.findByWorkTimeCode(companyId, worktimeCode.v());
	}

}
