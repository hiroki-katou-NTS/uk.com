package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class SocialInsuranceOfficeFinder {

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public Optional<SocialInsuranceOfficeDto> find(String code) {
		Optional<SocialInsuranceOfficeDto> dto = this.socialInsuranceOfficeRepository.findById(code)
				.map(domain -> SocialInsuranceOfficeDto.fromDomain(domain));
		// mock data
		return dto;
	}
}
