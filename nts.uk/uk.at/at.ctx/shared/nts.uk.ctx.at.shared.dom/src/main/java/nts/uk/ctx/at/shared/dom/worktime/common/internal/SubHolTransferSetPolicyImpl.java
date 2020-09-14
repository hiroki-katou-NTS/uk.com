/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class SubHolTransferSetPolicyImpl.
 */
@Stateless
public class SubHolTransferSetPolicyImpl implements SubHolTransferSetPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetPolicy#validate
	 * (nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predSet, SubHolTransferSet subHdSet) {
		AttendanceTime oneDayRange = predSet.getRangeTimeDay();
		boolean isCertainDayInvalid = subHdSet.getCertainTime().greaterThan(oneDayRange.v());
		boolean isHalfDayInvalid = subHdSet.getDesignatedTime().getHalfDayTime().greaterThan(oneDayRange.v());
		boolean isOneDayInvalid = subHdSet.getDesignatedTime().getOneDayTime().greaterThan(oneDayRange.v());

		if (isHalfDayInvalid || isOneDayInvalid || isCertainDayInvalid) {
			be.addMessage("Msg_781");
		}
	}
}
