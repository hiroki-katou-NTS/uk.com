package nts.uk.ctx.bs.employee.dom.workplace.master.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WorkplaceExportService {

	@Inject
	private WorkplaceConfigurationRepository wkpConfigRepo;
	
	@Inject
	private WorkplaceInformationRepository wkpInforRepo;
	
	/**
	 * [No.559]運用している職場の情報をすべて取得する
	 * (follow EA)
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInformation> getAllActiveWorkplace(String companyId, GeneralDate baseDate) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
		if (!optWkpConfig.isPresent()) 
			return null;
		WorkplaceConfiguration wkpConfig = optWkpConfig.get();
		Optional<DateHistoryItem> optWkpHistory = wkpConfig.items().stream().filter(i -> i.contains(baseDate))
				.findFirst();
		if (!optWkpHistory.isPresent()) 
			return null;
		DateHistoryItem wkpHistory = optWkpHistory.get();
		return wkpInforRepo.getAllActiveWorkplaceByCompany(companyId, wkpHistory.identifier());
	}
}
