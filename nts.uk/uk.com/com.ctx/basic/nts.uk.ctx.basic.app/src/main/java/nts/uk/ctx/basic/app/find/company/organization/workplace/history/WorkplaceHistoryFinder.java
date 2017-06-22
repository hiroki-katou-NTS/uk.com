/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.basic.app.find.company.organization.workplace.history;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.basic.app.find.company.organization.workplace.history.dto.WorkplaceHistoryDto;
import nts.uk.ctx.basic.app.find.company.organization.workplace.history.dto.WorkplaceHistoryInDto;
import nts.uk.ctx.basic.dom.company.organization.workplace.history.WorkplaceHistoryRepository;

/**
 * The Class WorkplaceHistoryFinder.
 */
@Stateless
public class WorkplaceHistoryFinder {

	/** The repository. */
	@Inject
	private WorkplaceHistoryRepository repository;
	
	/**
	 * Find by work place.
	 *
	 * @param input the input
	 * @return the list
	 */
	public List<WorkplaceHistoryDto> findByWorkplace(WorkplaceHistoryInDto input) {
		return this.repository.searchEmployee(input.getBaseDate(), input.getWorkplaceIds()).stream()
				.map(item -> {
					WorkplaceHistoryDto dto = new WorkplaceHistoryDto();
					item.saveToMemento(dto);
					return dto;
				}).collect(Collectors.toList());
	}

}
