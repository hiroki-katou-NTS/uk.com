/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezonePolicy;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

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

	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingPolicy#
	 * canRegister(nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predetemineTimeSet, 
			FixedWorkSetting fixedWorkSetting) {

		// =============validate list emTimezone, Msg_773==============
		this.validWorkTimezone(be, fixedWorkSetting, predetemineTimeSet);

		// Check #Msg_516 domain StampReflectTimezone
		fixedWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
			this.predService.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
		});

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

		// validate list HalfDayWorkTimezone
		this.fixHalfDayPolicy.validate(be, fixedWorkSetting, predetemineTimeSet);

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(be, predetemineTimeSet, fixedWorkSetting.getCommonSetting());

	}

	/**
	 * Valid work timezone.
	 *
	 * @param be
	 *            the be
	 * @param fixedWorkSet
	 *            the fixed work set
	 * @param predetemineTimeSet
	 *            the predetemine time set
	 */
	private void validWorkTimezone(BundledBusinessException be, FixedWorkSetting fixedWorkSet,
			PredetemineTimeSetting predetemineTimeSet) {
		List<AmPmAtr> lstAmPm = new ArrayList<AmPmAtr>();

		// add one day
		lstAmPm.add(AmPmAtr.ONE_DAY);

		// check use half day
		if (fixedWorkSet.getUseHalfDayShift()) {
			lstAmPm.add(AmPmAtr.AM);
			lstAmPm.add(AmPmAtr.PM);
		}
		List<EmTimeZoneSet> lstFixHalfDay = fixedWorkSet.getLstHalfDayWorkTimezone().stream()
				.filter(fixHalfWork -> lstAmPm.contains(fixHalfWork.getDayAtr()))
				.map(fixHalfWork -> fixHalfWork.getWorkTimezone().getLstWorkingTimezone()).flatMap(Collection::stream)
				.collect(Collectors.toList());

		// validate
		lstFixHalfDay.forEach(workTimezone -> {
			this.emTzPolicy.validate(be, predetemineTimeSet, workTimezone);
			this.emTzPolicy.validateTimezone(be, predetemineTimeSet.getPrescribedTimezoneSetting(),
					workTimezone.getTimezone());
		});
	}
}
