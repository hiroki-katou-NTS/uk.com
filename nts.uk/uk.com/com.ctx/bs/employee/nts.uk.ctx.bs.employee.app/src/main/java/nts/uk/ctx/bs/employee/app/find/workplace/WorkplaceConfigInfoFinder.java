/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.find.workplace;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.app.find.workplace.dto.WorkplaceConfigInfoDto;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceConfigInfoRepository;

@Stateless
public class WorkplaceConfigInfoFinder {

	@Inject
	private WorkplaceConfigInfoRepository workplaceConfigInfoRepository;
	
	public List<WorkplaceConfigInfoDto> findAll(String companyId,String historyId){
		List<WorkplaceConfigInfo> lstWorkplaceConfigInfo = workplaceConfigInfoRepository.findAll(companyId, historyId);
		//TODO convert to tree data
		return null;
	}
}
