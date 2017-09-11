/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceInfoDto;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;

@Stateless
public class WorkplaceInfoFinder {

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;
	
	//find workplace information by companyId & wkpId & historyId
	public WorkplaceInfoDto find(String companyId,String wkpId,String historyId)
	{
		Optional<WorkplaceInfo> opWkpInfo = workplaceInfoRepository.find(companyId,wkpId,historyId);
		if(opWkpInfo.isPresent())
		{
			WorkplaceInfoDto dto = new WorkplaceInfoDto();
			opWkpInfo.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
}
