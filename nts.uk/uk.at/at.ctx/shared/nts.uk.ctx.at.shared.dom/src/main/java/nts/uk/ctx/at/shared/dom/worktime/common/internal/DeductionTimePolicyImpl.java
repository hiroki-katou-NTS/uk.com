/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common.internal;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimePolicy;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.service.PredeteminePolicyService;

/**
 * The Class DeductionTimePolicyImpl.
 */
@Stateless
public class DeductionTimePolicyImpl implements DeductionTimePolicy {

	/** The pred service. */
	@Inject
	private PredeteminePolicyService predService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTimePolicy#validate(nts
	 * .uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting,
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime)
	 */
	@Override
	public void validate(PredetemineTimeSetting predTime, DeductionTime dedTime) {
		// validate Msg_516 DeductionTime
		if (this.predService.validateOneDay(predTime, dedTime.getStart(), dedTime.getEnd())){
			throw new BusinessException("Msg_516", "KMK003_21");
		}
	}

}
