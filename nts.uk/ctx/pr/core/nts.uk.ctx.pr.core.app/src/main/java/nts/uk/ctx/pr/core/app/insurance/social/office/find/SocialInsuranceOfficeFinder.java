package nts.uk.ctx.pr.core.app.insurance.social.office.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOffice;
import nts.uk.ctx.pr.core.dom.insurance.social.SocialInsuranceOfficeRepository;

@Stateless
public class SocialInsuranceOfficeFinder {

	@Inject
	SocialInsuranceOfficeRepository socialInsuranceOfficeRepository;

	public Optional<SocialInsuranceOfficeDto> find(String officeCode) {
		Optional<SocialInsuranceOffice> socialInsuranceOffice = socialInsuranceOfficeRepository
				.findByOfficeCode(officeCode);
		SocialInsuranceOfficeDto dto = SocialInsuranceOfficeDto.builder().build();
		if (socialInsuranceOffice.isPresent()) {
			socialInsuranceOffice.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	public List<SocialInsuranceOfficeItemDto> findAll(String companyCode) {
		return socialInsuranceOfficeRepository.findAll(companyCode).stream().map(domain -> {
			SocialInsuranceOfficeItemDto dto = SocialInsuranceOfficeItemDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
