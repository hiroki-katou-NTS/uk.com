/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.service.internal;

import javax.ejb.Stateless;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.service.PensionAvgearnService;

/**
 * The Class PensionRateServiceImpl.
 */
@Stateless
public class PensionAvgearnServiceImpl implements PensionAvgearnService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.service.
	 * PensionRateService#validateRequiredItem(nts.uk.ctx.pr.core.dom.insurance.
	 * social.pensionrate.PensionRate)
	 */
	@Override
	public void validateRequiredItem(PensionAvgearn pensionAvgearn) {
		// Validate required item
		if (pensionAvgearn.getChildContributionAmount() == null 
				|| pensionAvgearn.getCompanyFund() == null
				|| pensionAvgearn.getCompanyFundExemption() == null 
				|| pensionAvgearn.getCompanyPension() == null
				|| pensionAvgearn.getPersonalFund() == null 
				|| pensionAvgearn.getPersonalFundExemption() == null
				|| pensionAvgearn.getPersonalPension() == null) {
			throw new BusinessException("ER001");
		}
	}
}
