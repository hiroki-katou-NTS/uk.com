package nts.uk.ctx.bs.employee.dom.workplace.master.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class WorkplaceCommandService {

	@Inject
	private WorkplaceConfigurationRepository wkpConfigRepo;

	@Inject
	private WorkplaceInformationRepository wkpInforRepo;

	/**
	 * 職場構成を追加する
	 * 
	 * @param param
	 * @return historyId
	 */
	public String addWorkplaceConfig(AddWorkplaceConfigParam param) {
		if (param.getNewHistoryId() == null)
			param.setNewHistoryId(IdentifierUtil.randomUniqueId());
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(param.getCompanyId());
		if (optWkpConfig.isPresent()) {
			WorkplaceConfiguration wkpConfig = optWkpConfig.get();
			wkpConfig.add(new DateHistoryItem(param.getNewHistoryId(),
					new DatePeriod(param.getStartDate(), param.getEndDate())));
			wkpConfigRepo.updateWorkplaceConfig(wkpConfig);
		} else {
			WorkplaceConfiguration wkpConfig = new WorkplaceConfiguration(param.getCompanyId(),
					Arrays.asList(new DateHistoryItem(param.getNewHistoryId(),
							new DatePeriod(param.getStartDate(), param.getEndDate()))));
			wkpConfigRepo.addWorkplaceConfig(wkpConfig);
		}
		if (param.isCopyPreviousConfig()) {
			List<WorkplaceInformation> listWorkplacePrevHist = wkpInforRepo
					.getAllActiveWorkplaceByCompany(param.getCompanyId(), param.getPrevHistoryId());
			List<WorkplaceInformation> listWorkplaceNewHist = listWorkplacePrevHist.stream()
					.map(d -> new WorkplaceInformation(d.getCompanyId(), d.isDeleteFlag(), param.getNewHistoryId(),
							d.getWorkplaceId(), d.getWorkplaceCode(), d.getWorkplaceName(), d.getWorkplaceGeneric(),
							d.getWorkplaceDisplayName(), d.getHierarchyCode(), d.getWorkplaceExternalCode()))
					.collect(Collectors.toList());
			wkpInforRepo.addWorkplaces(listWorkplaceNewHist);
		}
		return param.getNewHistoryId();
	}

	/**
	 * 職場構成を更新する
	 * 
	 * @param param
	 */
	public void updateWorkplaceConfig(UpdateWorkplaceConfigParam param) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(param.getCompanyId());
		if (optWkpConfig.isPresent()) {
			WorkplaceConfiguration wkpConfig = optWkpConfig.get();
			wkpConfig.items().stream().filter(i -> i.identifier().equals(param.getHistoryId())).findFirst()
					.ifPresent(itemToBeChanged -> {
						wkpConfig.changeSpan(itemToBeChanged, new DatePeriod(param.getStartDate(), param.getEndDate()));
						wkpConfigRepo.updateWorkplaceConfig(wkpConfig);
					});
		}
	}

	/**
	 * 職場構成を削除する
	 * 
	 * @param companyId
	 * @param historyId
	 */
	public void deleteWorkplaceConfig(String companyId, String historyId) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
		if (optWkpConfig.isPresent()) {
			WorkplaceConfiguration wkpConfig = optWkpConfig.get();
			wkpConfig.items().stream().filter(i -> i.identifier().equals(historyId)).findFirst()
					.ifPresent(itemToBeRemove -> {
						wkpConfig.remove(itemToBeRemove);
						wkpConfigRepo.updateWorkplaceConfig(wkpConfig);
					});
		}
		wkpInforRepo.deleteWorkplaceInforOfHistory(companyId, historyId);
	}

}
