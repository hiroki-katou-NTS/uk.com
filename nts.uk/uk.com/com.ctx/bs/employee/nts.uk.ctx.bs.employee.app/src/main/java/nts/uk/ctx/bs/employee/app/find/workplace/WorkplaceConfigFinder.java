/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceConfigDto;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.shr.com.context.AppContexts;

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
	public WorkplaceConfigDto findAllByCompanyId() {
		String companyId = AppContexts.user().companyId();
		
		WorkplaceConfig workplaceConfig = workplaceConfigRepository.findAllByCompanyId(companyId);

		WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
		workplaceConfig.saveToMemento(wkpConfigDto);
		return wkpConfigDto;
	}

	/**
	 * Find lastest by company id.
	 *
	 * @param companyId the company id
	 * @return the workplace config dto
	 */
	public WorkplaceConfigDto findLastestByCompanyId() {
		String companyId = AppContexts.user().companyId();
		
		Optional<WorkplaceConfig> lstWorkplaceConfig = workplaceConfigRepository.findLastestByCompanyId(companyId);
		if (lstWorkplaceConfig.isPresent()) {
			WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
			lstWorkplaceConfig.get().saveToMemento(wkpConfigDto);
			return wkpConfigDto;
		}
		return null;
	}
}
