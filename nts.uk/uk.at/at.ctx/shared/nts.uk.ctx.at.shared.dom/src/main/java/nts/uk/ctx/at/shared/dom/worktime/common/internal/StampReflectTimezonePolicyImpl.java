/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone;
import nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezonePolicy;

/**
 * The Class StampReflectTimezonePolicyImpl.
 */
@Stateless
public class StampReflectTimezonePolicyImpl implements StampReflectTimezonePolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezonePolicy#
	 * validate(nts.arc.error.BundledBusinessException, boolean,
	 * nts.uk.ctx.at.shared.dom.worktime.common.StampReflectTimezone)
	 */
	@Override
	public void validate(BundledBusinessException bundledBusinessExceptions, boolean isFlow,
			StampReflectTimezone timezone) {
		if (timezone.getStartTime().v().equals(0) && timezone.getEndTime().v().equals(0)) {
			return;
		}

		// Validate Msg_770
		if (timezone.getStartTime().greaterThanOrEqualTo(timezone.getEndTime())) {
			if (isFlow) {
				// J2_2, J2_4
				if (timezone.getWorkNo().v() == 1 && timezone.getClassification().value == 0) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_270");
				}
				// J2_9, J2_11
				if (timezone.getWorkNo().v() == 1 && timezone.getClassification().value == 1) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_273");
				}
			} else {
				// J1_3, J1_5
				if (timezone.getWorkNo().v() == 1 && timezone.getClassification().value == 0) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_271");
				}
				// J1_7, J1_9
				if (timezone.getWorkNo().v() == 2 && timezone.getClassification().value == 0) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_272");
				}
				// J1_12, J1_14
				if (timezone.getWorkNo().v() == 1 && timezone.getClassification().value == 1) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_274");
				}
				// J1_16, J1_18
				if (timezone.getWorkNo().v() == 2 && timezone.getClassification().value == 1) {
					bundledBusinessExceptions.addMessage("Msg_770", "KMK003_275");
				}
			}
		}
	}

}
