/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.OtherEmTimezoneLateEarlyPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class WorkTimezoneLateEarlySetPolicyImpl.
 */
@Stateless
public class WorkTimezoneLateEarlySetPolicyImpl implements WorkTimezoneLateEarlySetPolicy {

	/** The other em policy. */
	@Inject
	private OtherEmTimezoneLateEarlyPolicy otherEmPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySetPolicy#
	 * validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneLateEarlySet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, WorkTimezoneLateEarlySet wtzLateEarly) {
		// validate list OtherEmTimezoneLateEarlySet
		wtzLateEarly.getOtherClassSets().forEach(item -> this.otherEmPolicy.validLateTime(be, predTime, item));
	}

}
