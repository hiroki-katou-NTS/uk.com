package nts.uk.ctx.pr.core.app.service.internal;

import javax.ejb.Stateless;

import nts.uk.ctx.pr.core.app.service.pension.PensionService;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;

@Stateless
public class DefaultPensionService implements PensionService {
	
	PensionRateRepository pensionRateRepository;
	@Override
	public void add(PensionRate pensionRate) {
		pensionRateRepository.add(pensionRate);
	}

	@Override
	public void update(PensionRate pensionRate) {
		pensionRateRepository.update(pensionRate);
	}

}
