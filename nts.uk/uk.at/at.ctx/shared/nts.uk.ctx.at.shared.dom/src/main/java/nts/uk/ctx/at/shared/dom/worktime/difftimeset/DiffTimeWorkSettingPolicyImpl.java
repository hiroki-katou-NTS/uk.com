/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeWorkSettingPolicyImpl.
 */
@Stateless
public class DiffTimeWorkSettingPolicyImpl implements DiffTimeWorkSettingPolicy {

	/** The predetemine policy service. */
	@Inject
	private PredeteminePolicyService predeteminePolicyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimezoneSetting)
	 */
	@Override
	public boolean canRegister(PredetemineTimeSet pred, DiffTimeWorkSetting diffTimeWorkSetting) {
		//validate StampReflectTimezone 516
		this.validateStampReflectTimezone(pred, diffTimeWorkSetting);

		//validate HDWorkTimeSheetSetting 516
		this.validateHDWorkTimeSheetSetting(pred, diffTimeWorkSetting);
		
		return true;
	}

	/**
	 * Validate stamp reflect timezone.
	 *
	 * @param companyId
	 *            the company id
	 * @param worktimeCode
	 *            the worktime code
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validateStampReflectTimezone(PredetemineTimeSet pred, DiffTimeWorkSetting diffTimeWorkSetting) {

		// get start and end time
		TimeWithDayAttr start = diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().getStartTime();
		TimeWithDayAttr end = diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().getEndTime();

		// validate
		this.predeteminePolicyService.validateOneDay(pred, start, end);
	}

	/**
	 * Validate diff timezone set.
	 *
	 * @param companyId
	 *            the company id
	 * @param worktimeCode
	 *            the worktime code
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validateHDWorkTimeSheetSetting(PredetemineTimeSet pred,
			DiffTimeWorkSetting diffTimeWorkSetting) {
		diffTimeWorkSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().stream().forEach(item -> {
			this.predeteminePolicyService.validateOneDay(pred, item.getStart(), item.getEnd());
		});
	}
}
