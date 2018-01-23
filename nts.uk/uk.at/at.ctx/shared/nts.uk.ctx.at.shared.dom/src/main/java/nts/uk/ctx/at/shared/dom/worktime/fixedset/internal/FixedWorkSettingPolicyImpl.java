/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.fixedset.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
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

	@Inject
	private PredeteminePolicyService predService;

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
	public void canRegister(FixedWorkSetting fixedWorkSetting, PredetemineTimeSetting predetemineTimeSet) {
		
		//=============validate list emTimezone, Msg_773==============
		this.validWorkTimezone(fixedWorkSetting, predetemineTimeSet);
		
		// Check #Msg_516 domain StampReflectTimezone
		fixedWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
			this.predService.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
		});
		
		// Check #Msg_516 domain HDWorkTimeSheetSetting
		fixedWorkSetting.getOffdayWorkTimezone().getLstWorkTimezone().forEach(setting -> {
			if (this.predService.validateOneDay(predetemineTimeSet, setting.getTimezone().getStart(),
					setting.getTimezone().getEnd())) {
				throw new BusinessException("Msg_516", "KMK003_90");
			}
		});
		
		// Check #Msg_516 domain FixRestTimezoneSet
		fixedWorkSetting.getOffdayWorkTimezone().getRestTimezone().getLstTimezone().forEach(setting -> {
			if (this.predService.validateOneDay(predetemineTimeSet, setting.getStart(), setting.getEnd())) {
				throw new BusinessException("Msg_516", "KMK003_21");
			}
		});

		// check use half day
		if (fixedWorkSetting.getUseHalfDayShift()) {
			// validate Msg_516 PredetemineTimeSetting
			predService.validatePredetemineTime(predetemineTimeSet);
		}

		// validate list HalfDayWorkTimezone
		this.fixHalfDayPolicy.validate(fixedWorkSetting, predetemineTimeSet);

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(predetemineTimeSet, fixedWorkSetting.getCommonSetting());

	}

	/**
	 * Valid work timezone.
	 *
	 * @param fixedWorkSet the fixed work set
	 * @param predetemineTimeSet the predetemine time set
	 * @see Check message Msg_773
	 */
	private void validWorkTimezone(FixedWorkSetting fixedWorkSet, PredetemineTimeSetting predetemineTimeSet) {
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
				.map(fixHalfWork -> fixHalfWork.getWorkTimezone().getLstWorkingTimezone())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		// validate
		lstFixHalfDay.forEach(workTimezone -> {
			this.emTzPolicy.validate(predetemineTimeSet, workTimezone);
			this.emTzPolicy.validateTimezone(predetemineTimeSet.getPrescribedTimezoneSetting(),
					workTimezone.getTimezone());
		});
	}
}
