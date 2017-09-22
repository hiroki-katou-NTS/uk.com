/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.dom.workplace.config.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigHistory;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService;

/**
 * The Class WkpConfigServiceImpl.
 */
@Stateless
public class WkpConfigServiceImpl implements WkpConfigService {

	/** The workplace config repository. */
	@Inject
	private WorkplaceConfigRepository workplaceConfigRepository;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService#validateAddHistory(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public String validateAddHistory(String companyId, GeneralDate addHistStart) {
		// get first hist
		Optional<WorkplaceConfig> optional = workplaceConfigRepository.findLastestByCompanyId(companyId);
		if (!optional.isPresent()) {
		    return null;
		}
		WorkplaceConfigHistory latestWkpConfigHist = optional.get().getWkpConfigHistoryLatest();
		// check validate start date
		if (latestWkpConfigHist.getPeriod().getStartDate().after(addHistStart)) {
			throw new BusinessException("Msg_102");
		}
		//return first histId 
		return latestWkpConfigHist.getHistoryId();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService#updatePrevHistory(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public void updatePrevHistory(String companyId,String prevHistId, GeneralDate endĐate) {
		Optional<WorkplaceConfig> wkpConfig = workplaceConfigRepository.findByHistId(companyId,prevHistId);
		if (wkpConfig.isPresent()) {
			workplaceConfigRepository.update(wkpConfig.get(), endĐate);
		}
	}

}
