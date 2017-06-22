/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.classification.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.classification.history.dto.ClassificationHistoryDto;
import nts.uk.ctx.basic.app.find.company.organization.classification.history.dto.ClassificationHistoryInDto;
import nts.uk.ctx.basic.dom.company.organization.classification.history.ClassificationHistoryRepository;

/**
 * The Class ClassificationHistoryFinder.
 */
@Stateless
public class ClassificationHistoryFinder {

	/** The repository. */
	@Inject
	private ClassificationHistoryRepository repository;
	
	/**
	 * Find by classification.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<ClassificationHistoryDto> findByClassification(ClassificationHistoryInDto input) {
		return this.repository
				.searchClassification(input.getBaseDate(), input.getClassificationCodes()).stream()
				.map(item -> {
					ClassificationHistoryDto dto = new ClassificationHistoryDto();
					item.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
	}

}
