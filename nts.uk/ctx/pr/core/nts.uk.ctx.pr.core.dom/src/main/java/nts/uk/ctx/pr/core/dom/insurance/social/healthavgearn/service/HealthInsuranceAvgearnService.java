package nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.service;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearn;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;

public interface HealthInsuranceAvgearnService {

	/**
	 * Validate required item.
	 *
	 * @param healthInsuranceAvgearn the health insurance avgearn
	 */
	void validateRequiredItem(HealthInsuranceAvgearn healthInsuranceAvgearn);
	
	/**
	 * Update health insurance rate avg earn.
	 *
	 * @param healthInsuranceRate the health insurance rate
	 */
	void updateHealthInsuranceRateAvgEarn(HealthInsuranceRate healthInsuranceRate);
}
