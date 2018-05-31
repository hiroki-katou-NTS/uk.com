/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.CoreTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexHalfDayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexOffdayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexStampReflectTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.DisplayMode;
import nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode;

/**
 * The Class FlexWorkSettingPolicyImpl.
 */
@Stateless
public class FlexWorkSettingPolicyImpl implements FlexWorkSettingPolicy {

	/** The predetemine policy service. */
	@Inject
	private PredeteminePolicyService predeteminePolicyService;

	/** The flex half day policy. */
	@Inject
	private FlexHalfDayWorkTimePolicy flexHalfDayPolicy;

	/** The flex offday policy. */
	@Inject
	private FlexOffdayWorkTimePolicy flexOffdayPolicy;

	/** The wtz common set policy. */
	@Inject
	private WorkTimezoneCommonSetPolicy wtzCommonSetPolicy;

	/** The core time setting policy. */
	@Inject
	private CoreTimeSettingPolicy coreTimeSettingPolicy;

	/** The flex stamp reflect timezone policy. */
	@Inject
	private FlexStampReflectTimezonePolicy flexStampReflectTimezonePolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy#validate(
	 * nts.arc.error.BundledBusinessException,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.worktimedisplay.WorkTimeDisplayMode,
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimeDisplayMode displayMode,
			FlexWorkSetting flexWork) {

		// Validate list work halfday
		this.validateHalfDayWork(be, predTime, displayMode, flexWork);

		// validate core time setting
		this.coreTimeSettingPolicy.validate(be, flexWork.getCoreTimeSetting(), predTime);

		if (flexWork.isUseHalfDayShift()) {
			// validate Msg_516 PredetemineTime
			predeteminePolicyService.validatePredetemineTime(be, predTime);
		}

		// validate FlexOffdayWorkTime
		this.flexOffdayPolicy.validate(be, predTime, flexWork.getOffdayWorkTime());

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(be, predTime, flexWork.getCommonSetting());

		// validate list stamp timezone
		if (DisplayMode.DETAIL.equals(displayMode.getDisplayMode())) {
			this.flexStampReflectTimezonePolicy.validate(be, predTime, flexWork);
		}
		
		// Filter AM PM
		flexWork.getLstHalfDayWorkTimezone().forEach(flexTime -> {
			this.flexHalfDayPolicy.filterTimezone(predTime, flexTime, displayMode.getDisplayMode(),
					flexWork.isUseHalfDayShift());
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
	 * @param flexWorkSetting
	 *            the flex work setting
	 */
	private void validateHalfDayWork(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSet,
			WorkTimeDisplayMode displayMode, FlexWorkSetting flexWorkSetting) {
		List<AmPmAtr> lstAmPm = new ArrayList<AmPmAtr>();
		lstAmPm.add(AmPmAtr.ONE_DAY);
		if (flexWorkSetting.isUseHalfDayShift()) {
			lstAmPm.add(AmPmAtr.AM);
			lstAmPm.add(AmPmAtr.PM);
		}

		List<FlexHalfDayWorkTime> lstFlexHalfWork = flexWorkSetting.getLstHalfDayWorkTimezone().stream()
				.filter(flexHalfWork -> lstAmPm.contains(flexHalfWork.getAmpmAtr())).collect(Collectors.toList());

		lstFlexHalfWork.forEach(flexHalfWork -> {
			this.flexHalfDayPolicy.validate(be, predetemineTimeSet, displayMode, flexHalfWork,
					flexWorkSetting.isUseHalfDayShift());
		});
	}
}
