/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeWorkSettingPolicyImpl.
 */
@Stateless
public class DiffTimeWorkSettingPolicyImpl implements DiffTimeWorkSettingPolicy {

//	/** The predetemine policy service. */
//	@Inject
//	private PredeteminePolicyService predeteminePolicyService;

	/** The diff time half policy. */
	@Inject
	private DiffTimeHalfDayWorkTimezonePolicy diffTimeHalfPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimezoneSetting)
	 */
	@Override
	public boolean canRegister(PredetemineTimeSetting pred, DiffTimeWorkSetting diffTimeWorkSetting) {
		//validate StampReflectTimezone 516
		this.validateStampReflectTimezone(pred, diffTimeWorkSetting);

		//validate HDWorkTimeSheetSetting 516
		this.validateHDWorkTimeSheetSetting(pred, diffTimeWorkSetting);

		diffTimeWorkSetting.getHalfDayWorkTimezones().forEach(halfDay -> this.diffTimeHalfPolicy.validate(halfDay, pred));
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
	private void validateStampReflectTimezone(PredetemineTimeSetting pred, DiffTimeWorkSetting diffTimeWorkSetting) {

		// get start and end time
		TimeWithDayAttr start = diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().getStartTime();
		TimeWithDayAttr end = diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().getEndTime();

		// validate
//		this.predeteminePolicyService.validateOneDay(pred, start, end);
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
	private void validateHDWorkTimeSheetSetting(PredetemineTimeSetting pred,
			DiffTimeWorkSetting diffTimeWorkSetting) {
		diffTimeWorkSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().stream().forEach(item -> {
//			this.predeteminePolicyService.validateOneDay(pred, item.getStart(), item.getEnd());
		});
	}
}
