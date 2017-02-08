package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

@Stateless
public class HealthInsuranceRateFinder {

	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;
	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public Optional<HealthInsuranceRateDto> find(String id) {
		Optional<HealthInsuranceRateDto> dto = this.healthInsuranceRateRepository.findById(id)
				.map(domain -> HealthInsuranceRateDto.fromDomain(domain));
		// dto.get().setOfficeName(socialInsuranceOfficeRepository.findByOfficeCode(dto.get().getOfficeCode()).getName().v());
		// mock data
		dto.get().setOfficeName("Ｃ 事業所");
		return dto;
	}
}
