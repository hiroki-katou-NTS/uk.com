/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.difftimeset.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeDeductTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeOTTimezoneSet;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.DiffTimeWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class DiffTimeWorkSettingPolicyImpl.
 */
@Stateless
public class DiffTimeWorkSettingPolicyImpl implements DiffTimeWorkSettingPolicy {

	/** The predetemine policy service. */
	@Inject
	private PredeteminePolicyService predeteminePolicyService;

	/** The diff time half policy. */
	@Inject
	private DiffTimeHalfDayWorkTimezonePolicy diffTimeHalfPolicy;

	/** The common work time policy. */
	@Inject
	private CommonWorkTimePolicy commonWorkTimePolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;

	/** The diff time stamp reflect timezone policy. */
	@Inject
	private DiffTimeStampReflectTimezonePolicy diffTimeStampReflectTimezonePolicy;

	/**
	 * Validate.
	 *
	 * @param be
	 *            the be
	 * @param pred
	 *            the pred
	 * @param displayMode
	 *            the display mode
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimezoneSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.difftimeset.
	 * DiffTimezoneSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting pred, WorkTimeDisplayMode displayMode,
			DiffTimeWorkSetting diffTimeWorkSetting) {

		// Validate list work halfday
		this.validateHalfDayWork(be, pred, displayMode, diffTimeWorkSetting);

		// validate StampReflectTimezone 516
		// this.validateStampReflectTimezone(pred, diffTimeWorkSetting);

		// validate HDWorkTimeSheetSetting 516
		this.validateHDWorkTimeSheetSetting(be, pred, diffTimeWorkSetting);

		// validate EmTimezoneChangeExtent
		this.validateEmTimezoneChangeExtent(be, pred, diffTimeWorkSetting);

		// validate common setting
		this.commonWorkTimePolicy.validate(be, pred, diffTimeWorkSetting.getCommonSet());

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(be, pred, diffTimeWorkSetting.getCommonSet());

		// validate list stamp timezone
		if (DisplayMode.DETAIL.equals(displayMode.getDisplayMode())) {
			this.diffTimeStampReflectTimezonePolicy.validate(be, pred, diffTimeWorkSetting);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.difftimeset.policy.
	 * DiffTimeWorkSettingPolicy#filterTimezone(nts.uk.ctx.at.shared.dom.
	 * worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting)
	 */
	@Override
	public void filterTimezone(PredetemineTimeSetting pred, WorkTimeDisplayMode displayMode,
			DiffTimeWorkSetting diffTimeWorkSetting) {
		// Filter AM PM
		diffTimeWorkSetting.getHalfDayWorkTimezones().forEach(diffTime -> {
			this.diffTimeHalfPolicy.filterTimezone(pred, diffTime, displayMode.getDisplayMode(),
					diffTimeWorkSetting.isUseHalfDayShift());
		});
	}

	/**
	 * Validate HD work time sheet setting.
	 *
	 * @param be
	 *            the be
	 * @param pred
	 *            the pred
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	// TODO Not use because not mapping to screen
	// private void validateStampReflectTimezone(BundledBusinessException be,
	// PredetemineTimeSetting pred,
	// DiffTimeWorkSetting diffTimeWorkSetting) {
	// diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().stream().forEach(item
	// -> {
	// // get start and end time
	// TimeWithDayAttr start = item.getStartTime();
	// TimeWithDayAttr end = item.getEndTime();
	// // validate
	// if (!this.predeteminePolicyService.validateOneDay(pred, start, end)) {
	// // throw new BusinessException("Msg_516");
	// }
	// });
	//
	// // get start and end time
	// // TimeWithDayAttr start =
	// //
	// diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone();
	// // TimeWithDayAttr end =
	// //
	// diffTimeWorkSetting.getStampReflectTimezone().getStampReflectTimezone().getEndTime();
	//
	// // validate
	// // this.predeteminePolicyService.validateOneDay(pred, start, end);
	// }

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
	private void validateHDWorkTimeSheetSetting(BundledBusinessException be, PredetemineTimeSetting pred,
			DiffTimeWorkSetting diffTimeWorkSetting) {
		// validate restime vs pred
		diffTimeWorkSetting.getDayoffWorkTimezone().getRestTimezone().getRestTimezones().stream().forEach(item -> {
			if (this.predeteminePolicyService.validateOneDay(pred, item.getStart(), item.getEnd())) {
				be.addMessage("Msg_516", "KMK003_21");
			}
		});

		// validate work time vs pred
		diffTimeWorkSetting.getDayoffWorkTimezone().getWorkTimezones().stream().forEach(item -> {
			if (this.predeteminePolicyService.validateOneDay(pred, item.getTimezone().getStart(),
					item.getTimezone().getEnd())) {
				be.addMessage("Msg_516", "KMK003_90");
			}
		});
	}

	/**
	 * Validate em timezone change extent.
	 *
	 * @param be
	 *            the be
	 * @param pred
	 *            the pred
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validateEmTimezoneChangeExtent(BundledBusinessException be, PredetemineTimeSetting pred,
			DiffTimeWorkSetting diffTimeWorkSetting) {

		// validate Msg_781
		int startTime = pred.getStartDateClock().valueAsMinutes();
		int endTime = startTime + pred.getRangeTimeDay().valueAsMinutes();

		int aheadChange = diffTimeWorkSetting.getChangeExtent().getAheadChange().valueAsMinutes();
		int behindChange = diffTimeWorkSetting.getChangeExtent().getBehindChange().valueAsMinutes();

		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(halfDay -> {
			halfDay.getWorkTimezone().getEmploymentTimezones().stream().forEach(item -> {
				boolean isInvalidAheadChange = item.getTimezone().getStart().valueAsMinutes() - aheadChange < startTime;
				boolean isInvalidBehindChange = behindChange + item.getTimezone().getEnd().valueAsMinutes() > endTime;

				if (isInvalidAheadChange || isInvalidBehindChange) {
					be.addMessage("Msg_781");
				}
			});
		});

		// validate Msg_783 for rest time of halfday
		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(item -> {

			List<DiffTimeDeductTimezone> lstRestTime = item.getRestTimezone().getRestTimezones().stream()
					.filter(halftime -> !halftime.isUpdateStartTime())
					.sorted((obj1, obj2) -> obj1.getStart().compareTo(obj2.getStart())).collect(Collectors.toList());
			List<EmTimeZoneSet> lstWorkTime = item.getWorkTimezone().getEmploymentTimezones().stream()
					.sorted((obj1, obj2) -> obj1.getTimezone().getStart().compareTo(obj2.getTimezone().getStart()))
					.collect(Collectors.toList());

			lstRestTime.stream().forEach(rest -> {
				// if worktime not include resttime
				if (this.restIsInWorkTime(rest, lstWorkTime)) {
					List<EmTimeZoneSet> lstWorkFilter = lstWorkTime.stream()
							.filter(work -> (work.getTimezone().getStart().v() - aheadChange <= rest.getStart()
									.valueAsMinutes())
									&& (work.getTimezone().getEnd().v() - aheadChange >= rest.getEnd().valueAsMinutes())
									&& (work.getTimezone().getStart().v() + behindChange <= rest.getStart()
											.valueAsMinutes())
									&& (work.getTimezone().getEnd().v() + behindChange >= rest.getEnd()
											.valueAsMinutes()))
							.collect(Collectors.toList());
					if (CollectionUtil.isEmpty(lstWorkFilter)) {
						be.addMessage("Msg_783");
					}
				}
			});
		});

		// validate Msg_994 for work time of halfday
		diffTimeWorkSetting.getHalfDayWorkTimezones().stream().forEach(item -> {
			item.getWorkTimezone().getEmploymentTimezones().stream().forEach(work -> {
				this.validateLeftOfWork(work, item.getWorkTimezone().getOTTimezones(), aheadChange, be);
				this.validateRightOfWork(work, item.getWorkTimezone().getOTTimezones(), behindChange, be);
			});
		});
	}

	/**
	 * Validate left of work.
	 *
	 * @param work
	 *            the work
	 * @param lstOT
	 *            the lst OT
	 * @param aheadChange
	 *            the ahead change
	 * @param be
	 *            the be
	 */
	// get left Ot of work
	private void validateLeftOfWork(EmTimeZoneSet work, List<DiffTimeOTTimezoneSet> lstOT, int aheadChange,
			BundledBusinessException be) {
		List<DiffTimeOTTimezoneSet> lstLeft = lstOT.stream()
				.filter(ot -> ot.getTimezone().getEnd().v() <= work.getTimezone().getStart().v())
				.filter(ot -> ot.isEarlyOTUse()).filter(ot -> !ot.isUpdateStartTime())
				.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()))
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(lstLeft)) {
			DiffTimeOTTimezoneSet ot = lstLeft.get(lstLeft.size() - 1);
			if (work.getTimezone().getStart().v() - aheadChange < ot.getTimezone().getEnd().v()) {
				be.addMessage("Msg_994");
			}
		}
	}

	/**
	 * Validate right of work.
	 *
	 * @param work
	 *            the work
	 * @param lstOT
	 *            the lst OT
	 * @param behindChange
	 *            the behind change
	 * @param be
	 *            the be
	 */
	// get right Ot of work
	private void validateRightOfWork(EmTimeZoneSet work, List<DiffTimeOTTimezoneSet> lstOT, int behindChange,
			BundledBusinessException be) {
		List<DiffTimeOTTimezoneSet> lstRight = lstOT.stream()
				.filter(ot -> ot.getTimezone().getStart().v() >= work.getTimezone().getEnd().v())
				.filter(ot -> !ot.isEarlyOTUse()).filter(ot -> !ot.isUpdateStartTime())
				.sorted((a, b) -> a.getTimezone().getStart().compareTo(b.getTimezone().getStart()))
				.collect(Collectors.toList());

		if (!CollectionUtil.isEmpty(lstRight)) {
			DiffTimeOTTimezoneSet ot = lstRight.get(0);
			if (work.getTimezone().getEnd().v() + behindChange > ot.getTimezone().getStart().v()) {
				be.addMessage("Msg_994");
			}
		}
	}

	/**
	 * Rest is in work time.
	 *
	 * @param restTime
	 *            the rest time
	 * @param lstWorkTime
	 *            the lst work time
	 * @return true, if successful
	 */
	// check if rest time in work time
	private boolean restIsInWorkTime(DiffTimeDeductTimezone restTime, List<EmTimeZoneSet> lstWorkTime) {
		List<EmTimeZoneSet> lst = lstWorkTime.stream()
				.filter(work -> (work.getTimezone().getStart().v() <= restTime.getStart().v())
						&& (work.getTimezone().getEnd().v() >= restTime.getEnd().v()))
				.collect(Collectors.toList());
		// if have rest time valid
		return lst.size() > 0;
	}

	/**
	 * Validate half day work.
	 *
	 * @param be
	 *            the be
	 * @param predTime
	 *            the pred time
	 * @param displayMode
	 *            the display mode
	 * @param diffTimeWorkSetting
	 *            the diff time work setting
	 */
	private void validateHalfDayWork(BundledBusinessException be, PredetemineTimeSetting predTime,
			WorkTimeDisplayMode displayMode, DiffTimeWorkSetting diffTimeWorkSetting) {
		List<AmPmAtr> lstAmPm = new ArrayList<AmPmAtr>();
		lstAmPm.add(AmPmAtr.ONE_DAY);
		if (diffTimeWorkSetting.isUseHalfDayShift()) {
			lstAmPm.add(AmPmAtr.AM);
			lstAmPm.add(AmPmAtr.PM);
		}

		List<DiffTimeHalfDayWorkTimezone> lstDiffHalfWork = diffTimeWorkSetting.getHalfDayWorkTimezones().stream()
				.filter(diffHalfWork -> lstAmPm.contains(diffHalfWork.getAmPmAtr())).collect(Collectors.toList());

		lstDiffHalfWork.forEach(diffHalfWork -> {
			this.diffTimeHalfPolicy.validate(be, predTime, displayMode, diffHalfWork,
					diffTimeWorkSetting.isUseHalfDayShift());
		});
	}

}
