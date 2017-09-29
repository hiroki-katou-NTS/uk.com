/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.bs.employee.dom.workplace.HistoryId;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.WorkplaceConfigInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;

/**
 * The Class WkpConfigInfoServiceImpl.
 */
@Stateless
public class WkpConfigInfoServiceImpl implements WkpConfigInfoService {

	@Inject
	WorkplaceConfigInfoRepository workplaceConfigInfoRepository;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService#copyWkpConfigInfoHist(java.lang.String, java.lang.String)
	 */
	@Override
	public void copyWkpConfigInfoHist(String companyId, String latestHistIdCurrent, String newHistId) {
		//get all WorkplaceConfigInfo of old hist
		Optional<WorkplaceConfigInfo> wkpConfigInfo = workplaceConfigInfoRepository.find(companyId, latestHistIdCurrent);
		if (!wkpConfigInfo.isPresent()) {
		    return;
		}
		WorkplaceConfigInfo wkp = wkpConfigInfo.get();
		if (!wkp.getWkpHierarchy().isEmpty()) {
			// convert new list
			wkp.setHistoryId(new HistoryId(newHistId));
//			WorkplaceConfigInfo result = this.convertList(wkpConfigInfo.get(), addNewHistId);
			// add list with new historyId
			workplaceConfigInfoRepository.addList(wkp);
		}
	}

}
