/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

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
	
	/** The em tz policy. */
	@Inject
	private EmTimeZoneSetPolicy emTzPolicy;

	/** The core time setting policy. */
	@Inject
	private CoreTimeSettingPolicy coreTimeSettingPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy#
	 * canRegisterFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	public void canRegisterFlexWorkSetting(FlexWorkSetting flexWorkSetting, PredetemineTimeSetting predetemineTimeSet) {
		// validate core time setting
		this.coreTimeSettingPolicy.validate(flexWorkSetting.getCoreTimeSetting(), predetemineTimeSet);

		// validate list emTimezone, Msg_773
		this.validWorkTimezone(flexWorkSetting, predetemineTimeSet);

		// Msg_781 DesignatedTime
//		this.service.compareWithOneDayRange(predetemineTimeSet,
//				flexWorkSetting.getCommonSetting().getSubHolTimeSet().getSubHolTimeSet().getDesignatedTime());
		
		// Msg_516 StampReflectTimezone
		flexWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
//			this.service.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
		});

		// valiadte FlexHalfDayWorkTime
		flexWorkSetting.getLstHalfDayWorkTimezone().forEach(halfDay -> this.flexHalfDayPolicy
				.validate(halfDay, predetemineTimeSet));
		
		if (flexWorkSetting.isUseHalfDayShift()) {
			// validate Msg_516 PredetemineTime
			predeteminePolicyService.validatePredetemineTime(predetemineTimeSet);
		}

		// validate FlexOffdayWorkTime
		this.flexOffdayPolicy.validate(predetemineTimeSet, flexWorkSetting.getOffdayWorkTime());

		// validate WorkTimezoneCommonSet
		this.wtzCommonSetPolicy.validate(predetemineTimeSet, flexWorkSetting.getCommonSetting());
	}
	

	/**
	 * Valid work timezone.
	 *
	 * @param flexWorkSetting the flex work setting
	 * @param predetemineTimeSet the predetemine time set
	 * @see Check message Msg_773
	 */
	private void validWorkTimezone(FlexWorkSetting flexWorkSetting, PredetemineTimeSetting predetemineTimeSet) {
		List<AmPmAtr> lstAmPm = new ArrayList<AmPmAtr>();
		
		// add one day
		lstAmPm.add(AmPmAtr.ONE_DAY);
		
		// check use half day
		if (flexWorkSetting.isUseHalfDayShift()) {
			lstAmPm.add(AmPmAtr.AM);
			lstAmPm.add(AmPmAtr.PM);
		}
		List<EmTimeZoneSet> lstFixHalfDay = flexWorkSetting.getLstHalfDayWorkTimezone().stream()
				.filter(fixHalfWork -> lstAmPm.contains(fixHalfWork.getAmpmAtr()))
				.map(fixHalfWork -> fixHalfWork.getWorkTimezone().getLstWorkingTimezone())
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		
		// validate
		lstFixHalfDay.forEach(workTimezone -> this.emTzPolicy.validate(predetemineTimeSet, workTimezone));
	}
}
