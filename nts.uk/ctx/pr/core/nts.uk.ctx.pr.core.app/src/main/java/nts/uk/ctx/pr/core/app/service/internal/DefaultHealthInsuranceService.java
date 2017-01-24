package nts.uk.ctx.pr.core.app.service.internal;

import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.service.healthinsurance.HealthInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

public class DefaultHealthInsuranceService implements HealthInsuranceService  {

	@Inject
	private HealthInsuranceRateRepository officeRepository;
	
	@Override
	public void add(HealthInsuranceRate healthInsuranceRate) {
		// TODO Auto-generated method stub
		officeRepository.add(healthInsuranceRate);
	}

	@Override
	public void update(HealthInsuranceRate healthInsuranceRate) {
		// TODO Auto-generated method stub
		officeRepository.update(healthInsuranceRate);
	}

}
