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

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.AmPmAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSet;
import nts.uk.ctx.at.shared.dom.worktime.common.EmTimeZoneSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexOffdayWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy#
	 * canRegisterFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	public void canRegisterFlexWorkSetting(FlexWorkSetting flexWorkSetting, PredetemineTimeSetting predetemineTimeSet) {
		
		// validate list emTimezone, Msg_773
		this.validWorkTimezone(flexWorkSetting, predetemineTimeSet);

		// 使用区分 = 使用しない AND 最低勤務時間 > 所定時間.1日 => Msg_775
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.NOT_USE)
				&& flexWorkSetting.getCoreTimeSetting().getMinWorkTime().valueAsMinutes() > predetemineTimeSet
						.getPredTime().getPredTime().getOneDay().valueAsMinutes()) {
			throw new BusinessException("Msg_775");
		}
		
		// 使用区分 = 使用する AND NOT( 午前終了時刻 >=  コア開始 AND 午後開始時刻 <= コア終了) => Msg_777
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)
				&& !(predetemineTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()
						.valueAsMinutes() >= flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime()
								.valueAsMinutes()
				&& predetemineTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()
						.valueAsMinutes() <= flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime()
								.valueAsMinutes())) {
			throw new BusinessException("Msg_777","KMK003_157");
		}
		
		// 使用区分 = 使用する 
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)){
			
			// get time zone 
			TimezoneUse timezone =  predetemineTimeSet.getPrescribedTimezoneSetting().getTimezoneShiftOne();
			
			// 開始 = 勤務NO = 1の場合の 所定時間帯設定.時間帯.開始
			int startTime = timezone.getStart().valueAsMinutes();
			
			// 終了 = 勤務NO = 1の場合の 所定時間帯設定.時間帯.終了
			int endTime = timezone.getEnd().valueAsMinutes();
			
			// NOT (コアタイム時間帯.開始時刻 >= 開始  &&  コアタイム時間帯.終了時刻 <= 終了) => Msg_773
			if (!(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime().valueAsMinutes() >= startTime
					&& flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime()
							.valueAsMinutes() <= endTime)) {
				//throw new BusinessException("Msg_773");
			}
		}
		
		// 使用区分 = 使用する
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)) {
			this.predeteminePolicyService.validateOneDay(predetemineTimeSet,
					flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime(),
					flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());
		}
		// Msg_781 DesignatedTime
//		this.service.compareWithOneDayRange(predetemineTimeSet,
//				flexWorkSetting.getCommonSetting().getSubHolTimeSet().getSubHolTimeSet().getDesignatedTime());
		
		// Msg_516
		flexWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
//			this.service.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
		});

		// valiadte FlexHalfDayWorkTime
		if (flexWorkSetting.isUseHalfDayShift()) {
			flexWorkSetting.getLstHalfDayWorkTimezone()
					.forEach(halfDay -> this.flexHalfDayPolicy.validate(halfDay, predetemineTimeSet));
			
			// validate Msg_516
			predeteminePolicyService.validateOneDay(predetemineTimeSet,
					predetemineTimeSet.getPrescribedTimezoneSetting().getMorningEndTime(),
					predetemineTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime());

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
