/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.config;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceConfigDto;
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
		
		Optional<WorkplaceConfig> optional = workplaceConfigRepository.findAllByCompanyId(companyId);
        if (!optional.isPresent()) {
            return null;
        }
		WorkplaceConfigDto wkpConfigDto = new WorkplaceConfigDto();
		optional.get().saveToMemento(wkpConfigDto);
		return wkpConfigDto;
	}
	
}
