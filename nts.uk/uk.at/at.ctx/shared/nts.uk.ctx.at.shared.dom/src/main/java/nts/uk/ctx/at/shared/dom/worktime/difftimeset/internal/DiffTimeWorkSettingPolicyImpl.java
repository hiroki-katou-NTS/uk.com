/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.EmTimezoneChangeExtent;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeWorkSettingPolicyImpl.
 */
@Stateless
public class DiffTimeWorkSettingPolicyImpl implements DiffTimeWorkSettingPolicy {

	// /** The predetemine policy service. */
	// @Inject
	// private PredeteminePolicyService predeteminePolicyService;

	/** The diff time half policy. */
	@Inject
	private DiffTimeHalfDayWorkTimezonePolicy diffTimeHalfPolicy;

	/** The common work time policy. */
	@Inject
	private CommonWorkTimePolicy commonWorkTimePolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;
	
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
		// validate StampReflectTimezone 516
		this.validateStampReflectTimezone(pred, diffTimeWorkSetting);

		// validate HDWorkTimeSheetSetting 516
		this.validateHDWorkTimeSheetSetting(pred, diffTimeWorkSetting);

		diffTimeWorkSetting.getHalfDayWorkTimezones()
				.forEach(halfDay -> this.diffTimeHalfPolicy.validate(halfDay, pred));

		// validate EmTimezoneChangeExtent
		this.validateEmTimezoneChangeExtent(pred, diffTimeWorkSetting);
		
		//validate common setting
		this.commonWorkTimePolicy.validate(pred, diffTimeWorkSetting.getCommonSet());

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(pred, diffTimeWorkSetting.getCommonSet());
		
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
		// this.predeteminePolicyService.validateOneDay(pred, start, end);
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
	private void validateHDWorkTimeSheetSetting(PredetemineTimeSetting pred, DiffTimeWorkSetting diffTimeWorkSetting) {
		diffTimeWorkSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().stream().forEach(item -> {
			// this.predeteminePolicyService.validateOneDay(pred,
			// item.getStart(), item.getEnd());
		});
	}

	/**
	 * Validate em timezone change extent.
	 *
	 * @param pred the pred
	 * @param emTimezoneChangeExtent the em timezone change extent
	 */
	private void validateEmTimezoneChangeExtent(PredetemineTimeSetting pred,
			DiffTimeWorkSetting diffTimeWorkSetting) {

		// validate Msg_781
		int startTime = pred.getStartDateClock().valueAsMinutes();
		int endTime = startTime+ pred.getRangeTimeDay().valueAsMinutes();
		
		int aheadChange = diffTimeWorkSetting.getChangeExtent().getAheadChange().valueAsMinutes();
		int behindChange = diffTimeWorkSetting.getChangeExtent().getBehindChange().valueAsMinutes();
		
		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(halfDay -> {
			halfDay.getWorkTimezone().getEmploymentTimezones().stream().forEach(item -> {
				boolean isInvalidAheadChange = startTime + aheadChange > item.getTimezone().getStart().valueAsMinutes();
				boolean isInvalidBehindChange = behindChange + item.getTimezone().getEnd().valueAsMinutes() > endTime;

				if (isInvalidAheadChange || isInvalidBehindChange) {
					throw new BusinessException("Msg_781");
				}
			});
		});
		
		// TODO
		// validate Msg_783 for work time
		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(item->{
			
			//TODO waiting confirm get update start time
			boolean canUpdateStartTime = false;
			if (canUpdateStartTime) {
				diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(halfDay -> {
					halfDay.getWorkTimezone().getEmploymentTimezones().stream().forEach(workTime -> {
						//TODO waiting confirm get fix rest time 
						int fixRestTimeStart = 0;
						int fixRestTimeEnd = 0;

						boolean invalidBehind = workTime.getTimezone().getStart().valueAsMinutes()
								+ behindChange > fixRestTimeStart;
						boolean invalidAhead = workTime.getTimezone().getEnd().valueAsMinutes()
								- aheadChange < fixRestTimeEnd;
						if (invalidAhead || invalidBehind) {
							throw new BusinessException("Msg_783");
						}
					});
				});
			}
		});
		
		// validate Msg_783 for OT time
		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(item -> {
			item.getWorkTimezone().getEmploymentTimezones().stream().forEach(workItem->{
				int workStart = workItem.getTimezone().getStart().valueAsMinutes();
				int workEnd = workItem.getTimezone().getEnd().valueAsMinutes();
				
				//TODO get end of fixed start
				int endOfFixStart= 0;
				
				//TODO get start of fixed end
				int startOfFixEnd = 0;
				
				if (workStart - aheadChange < endOfFixStart) {
					throw new BusinessException("Msg_783");
				}
				if (workEnd + behindChange > startOfFixEnd) {
					throw new BusinessException("Msg_783");
				}
			});
		});
		
		// January 2k18 validate Msg_784 
	}
}
