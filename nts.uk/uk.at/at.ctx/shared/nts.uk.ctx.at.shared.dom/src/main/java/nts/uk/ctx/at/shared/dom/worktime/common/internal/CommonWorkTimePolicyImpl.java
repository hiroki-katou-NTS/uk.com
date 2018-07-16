/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.CommonWorkTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class CommonWorkTimePolicyImpl.
 */
@Stateless
public class CommonWorkTimePolicyImpl implements CommonWorkTimePolicy {

	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting pred, WorkTimezoneCommonSet workTimezoneCommonSet) {
		//validate LateEarlySet
		this.validateWorkTimezoneLateEarlySet(be, pred, workTimezoneCommonSet.getLateEarlySet());
		//validate SubHolTransferSet
		workTimezoneCommonSet.getSubHolTimeSet().forEach(subHolTimeSet -> {
			this.validateSubHolTransferSet(be, pred, subHolTimeSet.getSubHolTimeSet());
		});
	}

	// validate 就業時間帯の遅刻・早退設定
	public void validateWorkTimezoneLateEarlySet(BundledBusinessException be, PredetemineTimeSetting pred,
			WorkTimezoneLateEarlySet workTimezoneLateEarlySet) {

	}

	// validate 代休振替設定
	public void validateSubHolTransferSet(BundledBusinessException be, PredetemineTimeSetting pred, SubHolTransferSet subHolTransferSet) {

		// validate Msg_781 for certainTime
		if (subHolTransferSet.getCertainTime().valueAsMinutes() >= pred.getRangeTimeDay().valueAsMinutes()) {
			be.addMessage("Msg_781");
		}

		// validate Msg_781 for Designated time

		if (subHolTransferSet.getDesignatedTime().getHalfDayTime().valueAsMinutes() >= pred.getRangeTimeDay()
				.valueAsMinutes()
				|| subHolTransferSet.getDesignatedTime().getHalfDayTime().valueAsMinutes() >= pred.getRangeTimeDay()
						.valueAsMinutes()) {
			be.addMessage("Msg_781");
		}
	}
}
