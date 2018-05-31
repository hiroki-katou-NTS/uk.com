/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.FixHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.policy.FixedWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FixedWorkSettingPolicyImpl.
 */
@Stateless
public class FixedWorkSettingPolicyImpl implements FixedWorkSettingPolicy {

	/** The pred service. */
	@Inject
	private PredeteminePolicyService predService;

	/** The fix half day policy. */
	@Inject
	private FixHalfDayWorkTimezonePolicy fixHalfDayPolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;

	/** The fixed stamp reflect timezone policy. */
	@Inject
	private FixedStampReflectTimezonePolicy fixedStampReflectTimezonePolicy;

	/**
	 * Validate.
	 *
	 * @param be
	 *            the be
	 * @param predetemineTimeSet
	 *            the predetemine time set
	 * @param displayMode
	 *            the display mode
	 * @param fixedWorkSetting
	 *            the fixed work setting
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSet,
			WorkTimeDisplayMode displayMode, FixedWorkSetting fixedWorkSetting) {

		// Validate list work halfday
		this.validateHalfDayWork(be, predetemineTimeSet, displayMode, fixedWorkSetting);

		// Check domain StampReflectTimezone
		// #Msg_520
		// List<StampReflectTimezone> listGoWork =
		// fixedWorkSetting.getLstStampReflectTimezone().stream()
		// .filter(domain -> domain.getClassification() ==
		// GoLeavingWorkAtr.GO_WORK)
		// .collect(Collectors.toList());
		// List<StampReflectTimezone> listLeaveWork =
		// fixedWorkSetting.getLstStampReflectTimezone().stream()
		// .filter(domain -> domain.getClassification() ==
		// GoLeavingWorkAtr.LEAVING_WORK)
		// .collect(Collectors.toList());
		// if
		// (!predetemineTimeSet.getPrescribedTimezoneSetting().isUseShiftTwo())
		// {
		// listGoWork = listGoWork.stream()
		// .filter(domain -> domain.getWorkNo().v() == 1)
		// .collect(Collectors.toList());
		// listLeaveWork = listLeaveWork.stream()
		// .filter(domain -> domain.getWorkNo().v() == 1)
		// .collect(Collectors.toList());
		// }
		// if (this.isOverlap(listGoWork) || this.isOverlap(listLeaveWork)) {
		// be.addMessage("Msg_520");
		// }

		// validate list stamp timezone
		if (DisplayMode.DETAIL.equals(displayMode.getDisplayMode())) {
			this.fixedStampReflectTimezonePolicy.validate(be, predetemineTimeSet, fixedWorkSetting);
		}

		// Check #Msg_516 domain HDWorkTimeSheetSetting
		fixedWorkSetting.getOffdayWorkTimezone().getLstWorkTimezone().forEach(setting -> {
			if (this.predService.validateOneDay(predetemineTimeSet, setting.getTimezone().getStart(),
					setting.getTimezone().getEnd())) {
				be.addMessage("Msg_516", "KMK003_90");
			}
		});

		// Check #Msg_516 domain FixRestTimezoneSet
		fixedWorkSetting.getOffdayWorkTimezone().getRestTimezone().getLstTimezone().forEach(setting -> {
			if (this.predService.validateOneDay(predetemineTimeSet, setting.getStart(), setting.getEnd())) {
				be.addMessage("Msg_516", "KMK003_21");
			}
		});

		// check use half day
		if (fixedWorkSetting.getUseHalfDayShift()) {
			// validate Msg_516 PredetemineTimeSetting
			predService.validatePredetemineTime(be, predetemineTimeSet);
		}

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(be, predetemineTimeSet, fixedWorkSetting.getCommonSetting());

		// Filter AM PM
		fixedWorkSetting.getLstHalfDayWorkTimezone().forEach(fixedTime -> {
			this.fixHalfDayPolicy.filterTimezone(predetemineTimeSet, fixedTime, displayMode.getDisplayMode(),
					fixedWorkSetting.getUseHalfDayShift());
		});
	}

	/**
	 * Validate half day work.
	 *
	 * @param be
	 *            the be
	 * @param predetemineTimeSet
	 *            the predetemine time set
	 * @param displayMode
	 *            the display mode
	 * @param fixedWorkSetting
	 *            the fixed work setting
	 */
	// private boolean isOverlap(List<StampReflectTimezone> listTimezone) {
	// Collections.sort(listTimezone,
	// Comparator.comparing(StampReflectTimezone::getStartTime));
	//
	// for (int i = 0; i < listTimezone.size(); i++) {
	// StampReflectTimezone tz1 = listTimezone.get(i);
	// for (int j = i + 1; j < listTimezone.size(); j++) {
	// StampReflectTimezone tz2 = listTimezone.get(j);
	// // check overlap
	// if (!(tz1.getEndTime().lessThanOrEqualTo(tz2.getStartTime())
	// || tz1.getStartTime().greaterThanOrEqualTo(tz2.getEndTime()))) {
	// return true;
	// }
	// }
	// }
	// return false;
	// }

	/**
	 * Validate half day work.
	 *
	 * @param be
	 *            the be
	 * @param predetemineTimeSet
	 *            the predetemine time set
	 * @param displayMode
	 *            the display mode
	 * @param fixedWorkSetting
	 *            the fixed work setting
	 */
	private void validateHalfDayWork(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSet,
			WorkTimeDisplayMode displayMode, FixedWorkSetting fixedWorkSetting) {
		List<AmPmAtr> lstAmPm = new ArrayList<AmPmAtr>();
		lstAmPm.add(AmPmAtr.ONE_DAY);
		if (fixedWorkSetting.getUseHalfDayShift()) {
			lstAmPm.add(AmPmAtr.AM);
			lstAmPm.add(AmPmAtr.PM);
		}

		List<FixHalfDayWorkTimezone> lstFixHalfWork = fixedWorkSetting.getLstHalfDayWorkTimezone().stream()
				.filter(fixHalfWork -> lstAmPm.contains(fixHalfWork.getDayAtr())).collect(Collectors.toList());

		lstFixHalfWork.forEach(fixHalfWork -> {
			this.fixHalfDayPolicy.validateFixedAndDiff(be, predetemineTimeSet, displayMode, fixHalfWork,
					fixedWorkSetting.getUseHalfDayShift());
		});
	}
}
