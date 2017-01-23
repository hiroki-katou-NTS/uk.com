package nts.uk.ctx.pr.core.app.service.internal;

import javax.inject.Inject;

import nts.uk.ctx.pr.core.app.service.insurance.social.SocialInsuranceService;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

public class DefaultSocialInsuranceService implements SocialInsuranceService {
	@Inject
	private SocialInsuranceOfficeRepository officeRepository;
	@Override
	public void add(SocialInsuranceOffice socialInsuranceOffice) {
		// TODO 
		officeRepository.add(socialInsuranceOffice);
	}

	@Override
	public void update(SocialInsuranceOffice socialInsuranceOffice) {
		// TODO
		officeRepository.update(socialInsuranceOffice);
	}

	@Override
	public void remove(SocialInsuranceOffice socialInsuranceOffice) {
		// TODO 
		officeRepository.remove(socialInsuranceOffice.getCode().toString(),socialInsuranceOffice.getVersion());
	}

}
