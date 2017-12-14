/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class WorkTimezoneOtherSubHolTimeSetPolicyImpl.
 */
@Stateless
public class WorkTimezoneOtherSubHolTimeSetPolicyImpl implements WorkTimezoneOtherSubHolTimeSetPolicy {

	/** The sub hd trans policy. */
	@Inject
	private SubHolTransferSetPolicy subHdTransPolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetPolicy#validate(nts.uk.ctx.at.shared.dom.
	 * worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSet)
	 */
	@Override
	public void validate(PredetemineTimeSetting predSet, WorkTimezoneOtherSubHolTimeSet otherSubHdSet) {
		// validate SubHolTransferSet
		this.subHdTransPolicy.validate(predSet, otherSubHdSet.getSubHolTimeSet());
	}

}
