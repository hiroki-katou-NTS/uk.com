/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet;
import nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetPolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;

/**
 * The Class TimezoneOfFixedRestTimeSetPolicyImpl.
 */
@Stateless
public class TimezoneOfFixedRestTimeSetPolicyImpl implements TimezoneOfFixedRestTimeSetPolicy {

	/** The ded time policy. */
	@Inject
	private DeductionTimePolicy dedTimePolicy;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSetPolicy
	 * #validate(nts.uk.ctx.at.shared.dom.worktime.predset.
	 * PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.TimezoneOfFixedRestTimeSet)
	 */
	@Override
	public void validate(BundledBusinessException be, PredetemineTimeSetting predTime, TimezoneOfFixedRestTimeSet fixedRest) {
		// validate list DeductionTime
		fixedRest.getTimezones().forEach(dedTime -> this.dedTimePolicy.validate(be, predTime, dedTime));
	}

}
