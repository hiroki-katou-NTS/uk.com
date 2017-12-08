/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.predset.Timezone;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

/**
 * The Class FlexWorkSettingPolicyImpl.
 */
@Stateless
public class FlexWorkSettingPolicyImpl implements FlexWorkSettingPolicy {
	
	/** The service. */
	@Inject
	private PredeteminePolicyService service;
	
	/** The work no one. */
	public static int WORK_NO_ONE = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingPolicy#
	 * canRegisterFlexWorkSetting(nts.uk.ctx.at.shared.dom.worktime.flexset.
	 * FlexWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet)
	 */
	@Override
	public void canRegisterFlexWorkSetting(FlexWorkSetting flexWorkSetting, PredetemineTimeSet predetemineTimeSet) {

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
			throw new BusinessException("Msg_777");
		}
		
		// 使用区分 = 使用する 
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)){
			
			// get time zone 
			Timezone timezone =  predetemineTimeSet.getPrescribedTimezoneSetting().getTimezone(WORK_NO_ONE);
			
			// 開始 = 勤務NO = 1の場合の 所定時間帯設定.時間帯.開始
			int startTime = timezone.getStart().valueAsMinutes();
			
			// 終了 = 勤務NO = 1の場合の 所定時間帯設定.時間帯.終了
			int endTime = timezone.getEnd().valueAsMinutes();
			
			// NOT (コアタイム時間帯.開始時刻 >= 開始  &&  コアタイム時間帯.終了時刻 <= 終了) => Msg_773
			if (!(flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime().valueAsMinutes() >= startTime
					&& flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime()
							.valueAsMinutes() <= endTime)) {
				throw new BusinessException("Msg_773");
			}
		}
		
		// 使用区分 = 使用する
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)) {
			this.service.validateOneDay(predetemineTimeSet,
					flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime(),
					flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime());
		}
		// Msg_781 DesignatedTime
		this.service.compareWithOneDayRange(predetemineTimeSet,
				flexWorkSetting.getCommonSetting().getSubHolTimeSet().getSubHolTimeSet().getDesignatedTime());
		
		// Msg_516
		flexWorkSetting.getLstStampReflectTimezone().forEach(setting -> {
			this.service.validateOneDay(predetemineTimeSet, setting.getStartTime(), setting.getEndTime());
		});
		
	}
	

}
