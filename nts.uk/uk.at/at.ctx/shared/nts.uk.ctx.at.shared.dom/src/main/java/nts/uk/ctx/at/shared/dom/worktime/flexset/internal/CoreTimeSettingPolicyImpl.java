/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.flexset.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.policy.CoreTimeSettingPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

/**
 * The Class CoreTimeSettingPolicyImpl.
 */
@Stateless
public class CoreTimeSettingPolicyImpl implements CoreTimeSettingPolicy {

	/** The predetemine policy service. */
	@Inject
	private PredeteminePolicyService predeteminePolicyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.flexset.CoreTimeSettingPolicy#validate(
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting)
	 */
	@Override
	public void validate(BundledBusinessException be, CoreTimeSetting coreTimeSetting, PredetemineTimeSetting predTime) {
		// 使用区分 = 使用しない AND 最低勤務時間 > 所定時間.1日 => Msg_775
		if (coreTimeSetting.getMinWorkTime() != null && !coreTimeSetting.isUseTimeSheet()
				&& coreTimeSetting.getMinWorkTime().greaterThan(predTime.getPredTime().getPredTime().getOneDay())) {
			be.addMessage("Msg_775");
		}

		// 使用区分 = 使用する
		if (coreTimeSetting.isUseTimeSheet()) {
			// 使用区分 = 使用する AND NOT( 午前終了時刻 >= コア開始 AND 午後開始時刻 <= コア終了) =>
			// Msg_777
			if (!(predTime.getPrescribedTimezoneSetting().getMorningEndTime()
					.greaterThanOrEqualTo(coreTimeSetting.getCoreTimeSheet().getStartTime())
					&& predTime.getPrescribedTimezoneSetting().getAfternoonStartTime()
							.lessThanOrEqualTo(coreTimeSetting.getCoreTimeSheet().getEndTime()))) {
				be.addMessage("Msg_777", "KMK003_157");
			}

			// get time zone
			TimezoneUse timezone = predTime.getPrescribedTimezoneSetting().getTimezoneShiftOne();

			// NOT (コアタイム時間帯.開始時刻 >= 開始 && コアタイム時間帯.終了時刻 <= 終了) => Msg_773
			if (!(coreTimeSetting.getCoreTimeSheet().getStartTime().greaterThanOrEqualTo(timezone.getStart())
					&& coreTimeSetting.getCoreTimeSheet().getEndTime().lessThanOrEqualTo(timezone.getEnd()))) {
				be.addMessage("Msg_773", "KMK003_157");
			}

			// validate Msg_516 CoreTimeSetting
			if (this.predeteminePolicyService.validateOneDay(predTime,
					coreTimeSetting.getCoreTimeSheet().getStartTime(),
					coreTimeSetting.getCoreTimeSheet().getEndTime())) {
				be.addMessage("Msg_516", "KMK003_157");
			}
		}

	}
}
