/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace.info;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.workplace.dto.WkpInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WorkplaceInfoFinder.
 */
@Stateless
public class WorkplaceInfoFinder {

	/** The workplace info repository. */
	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;

	/**
	 * Find.
	 *
	 * @param findObj the find obj
	 * @return the workplace info dto
	 */
	// find workplace information by companyId & wkpId & historyId
	public WorkplaceInfoDto find(WkpInfoFindObject findObj) {
		String companyId = AppContexts.user().companyId();
		//find
		Optional<WorkplaceInfo> opWkpInfo = workplaceInfoRepository.find(companyId, findObj.getWorkplaceId(), findObj.getHistoryId());
		//convert to Dto
		if (opWkpInfo.isPresent()) {
			WorkplaceInfoDto dto = new WorkplaceInfoDto();
			opWkpInfo.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
}
