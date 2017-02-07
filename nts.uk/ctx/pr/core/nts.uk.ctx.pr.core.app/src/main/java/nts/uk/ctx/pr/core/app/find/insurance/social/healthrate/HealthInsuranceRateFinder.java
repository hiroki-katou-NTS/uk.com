package nts.uk.ctx.pr.core.app.find.insurance.social.healthrate;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

@Stateless
public class HealthInsuranceRateFinder {

	@Inject
	private HealthInsuranceRateRepository repository;

	public HealthInsuranceRateDto find(String id) {
		return null;
	}
}
