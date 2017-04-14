/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.healthavgearn.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.healthavgearn.HealthInsuranceAvgearnRepository;

/**
 * The Class HealthInsuranceAvgearnFinder.
 */
@Stateless
public class HealthInsuranceAvgearnFinder {

	/** The repository. */
	@Inject
	private HealthInsuranceAvgearnRepository repository;

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<HealthInsuranceAvgearnDto> find(String id) {
		return repository.findById(id).stream().map(domain -> {
			HealthInsuranceAvgearnDto dto = HealthInsuranceAvgearnDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
