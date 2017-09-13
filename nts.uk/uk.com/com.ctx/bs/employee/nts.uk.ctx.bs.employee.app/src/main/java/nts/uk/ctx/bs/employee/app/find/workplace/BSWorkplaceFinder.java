/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceDto;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;

/**
 * The Class WorkplaceFinder.
 */
@Stateless
public class BSWorkplaceFinder {

	/** The workplace repository. */
	@Inject
	private WorkplaceRepository workplaceRepository;

	/**
	 * Find by start date.
	 *
	 * @param companyId the company id
	 * @param date the date
	 * @return the list
	 */
	public List<WorkplaceDto> findByStartDate(String companyId, GeneralDate date) {
		List<Workplace> lstWkp = workplaceRepository.findByStartDate(companyId, date);
		if (lstWkp.isEmpty()) {
			return null;
		}
		return lstWkp.stream().map(item -> {
			WorkplaceDto wkpDto = new WorkplaceDto();
			item.saveToMemento(wkpDto);
			return wkpDto;
		}).collect(Collectors.toList());
	}
}
