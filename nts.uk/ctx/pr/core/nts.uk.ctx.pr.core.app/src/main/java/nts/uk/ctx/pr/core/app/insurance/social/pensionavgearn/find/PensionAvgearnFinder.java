/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.insurance.social.pensionavgearn.find;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.insurance.social.pensionavgearn.PensionAvgearnRepository;

/**
 * The Class PensionAvgearnFinder.
 */
@Stateless
public class PensionAvgearnFinder {

	/** The repository. */
	@Inject
	private PensionAvgearnRepository repository;

	/**
	 * Find.
	 *
	 * @param id the id
	 * @return the list
	 */
	public List<PensionAvgearnDto> find(String id) {
		return repository.find(id).stream().map(domain -> {
			PensionAvgearnDto dto = PensionAvgearnDto.builder().build();
			domain.saveToMemento(dto);
			return dto;
		}).collect(Collectors.toList());
	}
}
