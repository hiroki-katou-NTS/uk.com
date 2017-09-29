/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceDto;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;

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
	    // TODO: check again
//		List<Workplace> lstWkp = workplaceRepository.findByStartDate(companyId, date);
//		if (lstWkp.isEmpty()) {
//			return null;
//		}
//		return lstWkp.stream().map(item -> {
//			WorkplaceDto wkpDto = new WorkplaceDto();
//			item.saveToMemento(wkpDto);
//			return wkpDto;
//		}).collect(Collectors.toList());
		return null;
	}
	
	/**
	 * Find by workplace id.
	 *
	 * @param workplaceId the workplace id
	 * @return the workplace dto
	 */
	public WorkplaceDto findByWorkplaceId(String workplaceId) {
		String companyId = AppContexts.user().companyId();
		Optional<Workplace> wkp = workplaceRepository.findByWorkplaceId(companyId, workplaceId);
		if (wkp.isPresent()) {
			WorkplaceDto wkpDto = new WorkplaceDto();
			wkp.get().saveToMemento(wkpDto);
			return wkpDto;
		}
		return null;
	}
}
