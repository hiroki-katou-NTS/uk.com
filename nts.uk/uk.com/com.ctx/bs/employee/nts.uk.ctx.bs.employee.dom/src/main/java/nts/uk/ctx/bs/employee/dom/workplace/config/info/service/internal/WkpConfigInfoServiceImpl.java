/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.info.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository;

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
	public void copyWkpConfigInfoHist(String companyId, String firstHistoryId, String addNewHistId) {
		//get all WorkplaceConfigInfo of old hist
		Optional<WorkplaceConfigInfo> wkpConfigInfo = workplaceConfigInfoRepository.find(companyId, firstHistoryId);
		
		if (wkpConfigInfo.isPresent()) {
			// convert new list
			WorkplaceConfigInfo result = this.convertList(wkpConfigInfo.get(), addNewHistId);
			// add list with new historyId
			workplaceConfigInfoRepository.addList(result);
		}
	}
	
	/**
	 * Convert list.
	 *
	 * @param convert the convert
	 * @param addNewHistId the add new hist id
	 * @return the workplace config info
	 */
	private WorkplaceConfigInfo convertList(WorkplaceConfigInfo convert, String addNewHistId) {
		return new WorkplaceConfigInfo(convert, addNewHistId);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService#updatePrevious(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public void updatePrevious(String prevHistId, GeneralDate addHistStart) {
		// TODO Auto-generated method stub
		
	}
}
