/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

@Stateless
public class CommonWorkTimePolicyImpl implements CommonWorkTimePolicy {

	@Override
	public void validate(PredetemineTimeSetting pred, WorkTimezoneCommonSet workTimezoneCommonSet) {
		//validate LateEarlySet
		this.validateWorkTimezoneLateEarlySet(pred, workTimezoneCommonSet.getLateEarlySet());
		//validate SubHolTransferSet
		workTimezoneCommonSet.getSubHolTimeSet().forEach(subHolTimeSet -> {
			this.validateSubHolTransferSet(pred, subHolTimeSet.getSubHolTimeSet());
		});
	}

	// validate 就業時間帯の遅刻・早退設定
	public void validateWorkTimezoneLateEarlySet(PredetemineTimeSetting pred,
			WorkTimezoneLateEarlySet workTimezoneLateEarlySet) {

		// validate Msg_517
		workTimezoneLateEarlySet.getOtherClassSets().stream().forEach(item -> {
			if (item.getGraceTimeSet().getGraceTime().valueAsMinutes() > pred.getRangeTimeDay().valueAsMinutes()) {
				throw new BusinessException("Msg_517");
			}
		});

		// TODO Auto-generated method stub
		// validate

	}

	// validate 代休振替設定
	public void validateSubHolTransferSet(PredetemineTimeSetting pred, SubHolTransferSet subHolTransferSet) {

		// validate Msg_781 for certainTime
		if (subHolTransferSet.getCertainTime().valueAsMinutes() >= pred.getRangeTimeDay().valueAsMinutes()) {
			throw new BusinessException("Msg_781");
		}

		// validate Msg_781 for Designated time

		if (subHolTransferSet.getDesignatedTime().getHalfDayTime().valueAsMinutes() >= pred.getRangeTimeDay()
				.valueAsMinutes()
				|| subHolTransferSet.getDesignatedTime().getHalfDayTime().valueAsMinutes() >= pred.getRangeTimeDay()
						.valueAsMinutes()) {
			throw new BusinessException("Msg_781");
		}

		// TODO case no msg
	}
}
