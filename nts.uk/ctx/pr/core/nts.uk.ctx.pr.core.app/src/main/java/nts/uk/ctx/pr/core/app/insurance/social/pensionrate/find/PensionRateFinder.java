package nts.uk.ctx.pr.core.app.insurance.social.pensionrate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRate;
import nts.uk.ctx.pr.core.dom.insurance.social.pensionrate.PensionRateRepository;

@Stateless
public class PensionRateFinder {
	@Inject
	private PensionRateRepository pensionRateRepository;

	public Optional<PensionRateDto> find(String id) {
		Optional<PensionRate> pensionRate = this.pensionRateRepository.findById(id);
		PensionRateDto dto = PensionRateDto.builder().build();
		if (pensionRate.isPresent()) {
			pensionRate.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	public List<PensionRateDto> findAll(String companyCode) {
		return pensionRateRepository.findAll(companyCode).stream().map(domain -> {
			PensionRateDto dto = PensionRateDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
