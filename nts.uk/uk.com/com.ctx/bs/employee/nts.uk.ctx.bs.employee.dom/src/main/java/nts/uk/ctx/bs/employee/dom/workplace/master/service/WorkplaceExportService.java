package nts.uk.ctx.bs.employee.dom.workplace.master.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	 * [No.559]運用している職場の情報をすべて取得する (follow EA)
	 * 
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInformation> getAllActiveWorkplace(String companyId, GeneralDate baseDate) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
		if (!optWkpConfig.isPresent())
			return Collections.emptyList();
		WorkplaceConfiguration wkpConfig = optWkpConfig.get();
		Optional<DateHistoryItem> optWkpHistory = wkpConfig.items().stream().filter(i -> i.contains(baseDate))
				.findFirst();
		if (!optWkpHistory.isPresent())
			return Collections.emptyList();
		DateHistoryItem wkpHistory = optWkpHistory.get();
		List<WorkplaceInformation> result = wkpInforRepo.getAllActiveWorkplaceByCompany(companyId,
				wkpHistory.identifier());
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

	/**
	 * [No.560]職場IDから職場の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listWorkplaceId
	 * @param baseDate
	 * @return
	 */
	public List<WorkplaceInformation> getWorkplaceInforFromWkpIds(String companyId, List<String> listWorkplaceId,
			GeneralDate baseDate) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
		if (!optWkpConfig.isPresent())
			return Collections.emptyList();
		WorkplaceConfiguration wkpConfig = optWkpConfig.get();
		Optional<DateHistoryItem> optWkpHistory = wkpConfig.items().stream().filter(i -> i.contains(baseDate))
				.findFirst();
		if (!optWkpHistory.isPresent())
			return Collections.emptyList();
		DateHistoryItem wkpHistory = optWkpHistory.get();
		List<WorkplaceInformation> result = wkpInforRepo.getWorkplaceByWkpIds(companyId, wkpHistory.identifier(),
				listWorkplaceId);
		List<String> listAccquiredWkpId = result.stream().map(w -> w.getWorkplaceId()).collect(Collectors.toList());
		List<String> listWkpIdNoResult = listWorkplaceId.stream().filter(i -> !listAccquiredWkpId.contains(i))
				.collect(Collectors.toList());
		if (!listWkpIdNoResult.isEmpty()) {
			List<WorkplaceInformation> listPastWkpInfor = this.getPastWorkplaceInfor(companyId, wkpHistory.identifier(),
					listWkpIdNoResult);
			result.addAll(listPastWkpInfor);
		}
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

	/**
	 * [No.561]過去の職場の情報を取得する
	 * 
	 * @param companyId
	 * @param historyId
	 * @param listWorkplaceId
	 * @return
	 */
	public List<WorkplaceInformation> getPastWorkplaceInfor(String companyId, String historyId,
			List<String> listWorkplaceId) {
		Optional<WorkplaceConfiguration> optWkpConfig = wkpConfigRepo.getWkpConfig(companyId);
		if (!optWkpConfig.isPresent())
			return Collections.emptyList();
		WorkplaceConfiguration wkpConfig = optWkpConfig.get();
		Optional<DateHistoryItem> optWkpHistory = wkpConfig.items().stream()
				.filter(i -> i.identifier().equals(historyId)).findFirst();
		if (!optWkpHistory.isPresent())
			return Collections.emptyList();
		DateHistoryItem wkpHistory = optWkpHistory.get();
		int currentIndex = wkpConfig.items().indexOf(wkpHistory);
		int size = wkpConfig.items().size();
		List<WorkplaceInformation> result = new ArrayList<>();
		for (int i = currentIndex + 1; i < size; i++) {
			result.addAll(wkpInforRepo.getWorkplaceByWkpIds(companyId, wkpConfig.items().get(i).identifier(),
					listWorkplaceId));
			List<String> listAccquiredWkpId = result.stream().map(w -> w.getWorkplaceId()).collect(Collectors.toList());
			listWorkplaceId = listWorkplaceId.stream().filter(id -> !listAccquiredWkpId.contains(id))
					.collect(Collectors.toList());
			if (listWorkplaceId.isEmpty())
				break;
		}
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

}
