package nts.uk.ctx.bs.employee.app.find.wkpdep;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentConfiguration;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.department_new.DepartmentInformationRepository;
import nts.uk.ctx.bs.employee.dom.operationrule.OperationRuleRepository;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace_new.WorkplaceInformationRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WkpDepFinder {

	private static final int WORKPLACE_MODE = 0;
	private static final int DEPARTMENT_MODE = 1;

	@Inject
	private OperationRuleRepository operationRepo;

	@Inject
	private WorkplaceConfigurationRepository wkpConfigRepo;

	@Inject
	private DepartmentConfigurationRepository depConfigRepo;

	@Inject
	private WorkplaceInformationRepository wkpInforRepo;

	@Inject
	private DepartmentInformationRepository depInforRepo;

	public ConfigurationDto getWkpDepConfig(int mode, GeneralDate baseDate) {
		String companyId = AppContexts.user().companyId();
		switch (mode) {
		case WORKPLACE_MODE:
			Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
			if (optWkpConfig.isPresent()) {
				WorkplaceConfiguration wkpConfig = optWkpConfig.get();
				Optional<DateHistoryItem> optWkpHistory = wkpConfig.items().stream().filter(i -> i.contains(baseDate))
						.findFirst();
				if (optWkpHistory.isPresent()) {
					DateHistoryItem wkpHistory = optWkpHistory.get();
					return new ConfigurationDto(wkpHistory.identifier(), wkpHistory.start(), wkpHistory.end());
				}
			}
			return null;
		case DEPARTMENT_MODE:
			Optional<DepartmentConfiguration> optDepConfig = depConfigRepo.getDepConfig(companyId);
			if (optDepConfig.isPresent()) {
				DepartmentConfiguration depConfig = optDepConfig.get();
				Optional<DateHistoryItem> optDepHistory = depConfig.items().stream().filter(i -> i.contains(baseDate))
						.findFirst();
				if (optDepHistory.isPresent()) {
					DateHistoryItem depHistory = optDepHistory.get();
					return new ConfigurationDto(depHistory.identifier(), depHistory.start(), depHistory.end());
				}
			}
			return null;
		default:
			return null;
		}
	}

	public List<ConfigurationDto> getAllWkpDepConfig(int mode) {
		String companyId = AppContexts.user().companyId();
		switch (mode) {
		case WORKPLACE_MODE:
			Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
			if (!optWkpConfig.isPresent())
				return null;
			WorkplaceConfiguration wkpConfig = optWkpConfig.get();
			return wkpConfig.items().stream().map(
					wkpHistory -> new ConfigurationDto(wkpHistory.identifier(), wkpHistory.start(), wkpHistory.end()))
					.collect(Collectors.toList());
		case DEPARTMENT_MODE:
			Optional<DepartmentConfiguration> optDepConfig = depConfigRepo.getDepConfig(companyId);
			if (optDepConfig.isPresent())
				return null;
			DepartmentConfiguration depConfig = optDepConfig.get();
			return depConfig.items().stream().map(
					depHistory -> new ConfigurationDto(depHistory.identifier(), depHistory.start(), depHistory.end()))
					.collect(Collectors.toList());
		default:
			return null;
		}
	}

	public List<InformationDto> getWkpDepInfor(int mode, String historyId) {
		String companyId = AppContexts.user().companyId();
		switch (mode) {
		case WORKPLACE_MODE:
			List<WorkplaceInformation> listWkp = wkpInforRepo.getAllActiveWorkplaceByCompany(companyId, historyId);
			return listWkp.stream().map(i -> new InformationDto(i)).collect(Collectors.toList());
		case DEPARTMENT_MODE:
			List<DepartmentInformation> listDep = depInforRepo.getAllActiveDepartmentByCompany(companyId, historyId);
			return listDep.stream().map(i -> new InformationDto(i)).collect(Collectors.toList());
		default:
			return null;
		}
	}

}
