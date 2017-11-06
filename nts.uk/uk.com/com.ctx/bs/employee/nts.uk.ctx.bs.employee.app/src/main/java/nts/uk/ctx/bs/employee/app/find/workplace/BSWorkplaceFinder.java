/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

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
	 * Find by workplace id.
	 *
	 * @param workplaceId
	 *            the workplace id
	 * @return the workplace dto
	 */
	public WorkplaceDto findByWorkplaceId(String workplaceId) {
        String companyId = AppContexts.user().companyId();
        Optional<Workplace> wkp = workplaceRepository.findByWorkplaceId(companyId, workplaceId);
        if (!wkp.isPresent()) {
            return null;
        }
        WorkplaceDto wkpDto = new WorkplaceDto();
        wkp.get().saveToMemento(wkpDto);
        return wkpDto;
	}
}
