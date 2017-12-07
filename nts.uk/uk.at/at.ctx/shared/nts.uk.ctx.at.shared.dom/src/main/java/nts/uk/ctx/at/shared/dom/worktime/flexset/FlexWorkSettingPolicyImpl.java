/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.common.usecls.ApplyAtr;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSet;

/**
 * The Class FlexWorkSettingPolicyImpl.
 */
@Stateless
public class FlexWorkSettingPolicyImpl implements FlexWorkSettingPolicy {

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
		
		// 使用区分 = 使用する AND  午前終了時刻 >=  コア開始 AND 午後開始時刻 <= コア終了 => Msg_777
		if (flexWorkSetting.getCoreTimeSetting().getTimesheet().equals(ApplyAtr.USE)
				&& predetemineTimeSet.getPrescribedTimezoneSetting().getMorningEndTime()
						.valueAsMinutes() >= flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getStartTime()
								.valueAsMinutes()
				&& predetemineTimeSet.getPrescribedTimezoneSetting().getAfternoonStartTime()
						.valueAsMinutes() <= flexWorkSetting.getCoreTimeSetting().getCoreTimeSheet().getEndTime()
								.valueAsMinutes()) {
			throw new BusinessException("Msg_777");
		}
		
		
	}

}
