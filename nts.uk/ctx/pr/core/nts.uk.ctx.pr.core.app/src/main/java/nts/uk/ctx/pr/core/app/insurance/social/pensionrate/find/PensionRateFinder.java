package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;
@Stateless
public class PensionRateFinder {
	@Inject
	private PensionRateRepository pensionRateRepository;

	public Optional<PensionRateDto> find(String id) {
		Optional<PensionRateDto> dto = this.pensionRateRepository.findById(id)
				.map(domain -> PensionRateDto.fromDomain(domain));
		// dto.get().setOfficeName(socialInsuranceOfficeRepository.findByOfficeCode(dto.get().getOfficeCode()).getName().v());
		// mock data
		dto.get().setOfficeCode("Ｃ 事業所");
		return dto;
	}
}
