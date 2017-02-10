package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class SocialInsuranceOfficeFinder {

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public Optional<SocialInsuranceOfficeDto> find(String code) {
		Optional<SocialInsuranceOffice> socialInsuranceOffice = socialInsuranceOfficeRepository.findById(code);
		SocialInsuranceOfficeDto dto = SocialInsuranceOfficeDto.builder().build();
		if (socialInsuranceOffice.isPresent()) {
			socialInsuranceOffice.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}
}
