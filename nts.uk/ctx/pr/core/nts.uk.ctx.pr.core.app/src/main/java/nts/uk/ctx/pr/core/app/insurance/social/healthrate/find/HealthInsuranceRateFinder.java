/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthrate.find;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRate;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.HealthInsuranceRateRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class HealthInsuranceRateFinder.
 */
@Stateless
public class HealthInsuranceRateFinder {

	/** The health insurance rate repository. */
	@Inject
	private HealthInsuranceRateRepository healthInsuranceRateRepository;

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the optional
	 */
	public Optional<HealthInsuranceRateDto> find(String id) {
		Optional<HealthInsuranceRate> healthInsuranceRate = healthInsuranceRateRepository.findById(id);
		HealthInsuranceRateDto dto = HealthInsuranceRateDto.builder().officeName("Ｃ 事業所").build();
		if (healthInsuranceRate.isPresent()) {
			healthInsuranceRate.get().saveToMemento(dto);
		}
		return Optional.ofNullable(dto);
	}

	/**
	 * Find by office code.
	 *
	 * @param officeCode the office code
	 * @return the list
	 */
	public List<HealthInsuranceRateDto> findByOfficeCode(String officeCode) {
		String companyCode = AppContexts.user().companyCode();
		return healthInsuranceRateRepository.findAll(companyCode,officeCode).stream().map(domain -> {
			HealthInsuranceRateDto dto = HealthInsuranceRateDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
