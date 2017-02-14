package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;

@Stateless
public class HealthInsuranceRateFinder {

	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;

	public Optional<HealthInsuranceRateDto> find(String id) {
		Optional<HealthInsuranceRate> healthInsuranceRate = healthInsuranceRateRepository.findById(id);
		HealthInsuranceRateDto dto = HealthInsuranceRateDto.builder().officeName("Ｃ 事業所").build();
		if (healthInsuranceRate.isPresent()) {
			healthInsuranceRate.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	public List<HealthInsuranceRateDto> findByOfficeCode(String officeCode) {
		return healthInsuranceRateRepository.findAll(officeCode).stream().map(domain -> {
			HealthInsuranceRateDto dto = HealthInsuranceRateDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
