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

import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceConfigDto;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;

/**
 * The Class WorkplaceConfigFinder.
 */
@Stateless
public class WorkplaceConfigFinder {

	/** The workplace config repository. */
	@Inject
	private WorkplaceConfigRepository workplaceConfigRepository;

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public List<WorkplaceConfigDto> findAllByCompanyId(String companyId) {
		List<WorkplaceConfig> lstWorkplaceConfig = workplaceConfigRepository.findAllByCompanyId(companyId);

		return lstWorkplaceConfig.stream().map(item -> {
			WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
			item.saveToMemento(wkpConfigDto);
			return wkpConfigDto;
		}).collect(Collectors.toList());
	}

	/**
	 * Find lastest by company id.
	 *
	 * @param companyId the company id
	 * @return the workplace config dto
	 */
	public WorkplaceConfigDto findLastestByCompanyId(String companyId) {
		Optional<WorkplaceConfig> lstWorkplaceConfig = workplaceConfigRepository.findLastestByCompanyId(companyId);
		if (lstWorkplaceConfig.isPresent()) {
			WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
			lstWorkplaceConfig.get().saveToMemento(wkpConfigDto);
			return wkpConfigDto;
		}
		return null;
	}
}
