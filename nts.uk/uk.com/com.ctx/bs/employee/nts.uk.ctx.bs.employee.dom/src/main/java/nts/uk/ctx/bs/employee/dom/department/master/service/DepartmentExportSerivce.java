package nts.uk.ctx.bs.employee.dom.department.master.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentConfiguration;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformationRepository;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class DepartmentExportSerivce {

	@Inject
	private DepartmentConfigurationRepository depConfigRepo;

	@Inject
	private DepartmentInformationRepository depInforRepo;

	/**
	 * [No.562]運用している部門の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param baseDate
	 * @return
	 */
	public List<DepartmentInformation> getAllActiveDepartment(String companyId, GeneralDate baseDate) {
		Optional<DepartmentConfiguration> optDepConfig = depConfigRepo.getDepConfig(companyId);
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DepartmentConfiguration depConfig = optDepConfig.get();
		Optional<DateHistoryItem> optDepHist = depConfig.items().stream().filter(i -> i.contains(baseDate)).findFirst();
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DateHistoryItem depHist = optDepHist.get();
		List<DepartmentInformation> result = depInforRepo.getAllActiveDepartmentByCompany(companyId,
				depHist.identifier());
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

	/**
	 * [No.563]部門IDから部門の情報をすべて取得する
	 * 
	 * @param companyId
	 * @param listDepartmentId
	 * @param baseDate
	 * @return
	 */
	public List<DepartmentInformation> getDepartmentInforFromDepIds(String companyId, List<String> listDepartmentId,
			GeneralDate baseDate) {
		Optional<DepartmentConfiguration> optDepConfig = depConfigRepo.getDepConfig(companyId);
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DepartmentConfiguration depConfig = optDepConfig.get();
		Optional<DateHistoryItem> optDepHist = depConfig.items().stream().filter(i -> i.contains(baseDate)).findFirst();
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DateHistoryItem depHist = optDepHist.get();
		List<DepartmentInformation> result = depInforRepo.getDepartmentByDepIds(companyId, depHist.identifier(),
				listDepartmentId);
		List<String> listAccquiredId = result.stream().map(i -> i.getDepartmentId()).collect(Collectors.toList());
		List<String> listDepIdNoResult = listDepartmentId.stream().filter(i -> !listAccquiredId.contains(i))
				.collect(Collectors.toList());
		if (!listDepIdNoResult.isEmpty()) {
			result.addAll(this.getPastDepartmentInfor(companyId, depHist.identifier(), listDepIdNoResult));
		}
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

	/**
	 * [No.564]過去の部門の情報を取得する
	 * 
	 * @param companyId
	 * @param depHistId
	 * @param listDepartmentId
	 * @return
	 */
	public List<DepartmentInformation> getPastDepartmentInfor(String companyId, String depHistId,
			List<String> listDepartmentId) {
		Optional<DepartmentConfiguration> optDepConfig = depConfigRepo.getDepConfig(companyId);
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DepartmentConfiguration depConfig = optDepConfig.get();
		Optional<DateHistoryItem> optDepHist = depConfig.items().stream().filter(i -> i.identifier().equals(depHistId))
				.findFirst();
		if (!optDepConfig.isPresent())
			return Collections.emptyList();
		DateHistoryItem depHist = optDepHist.get();
		int currentIndex = depConfig.items().indexOf(depHist);
		int size = depConfig.items().size();
		List<DepartmentInformation> result = new ArrayList<>();
		for (int i = currentIndex + 1; i < size; i++) {
			result.addAll(depInforRepo.getDepartmentByDepIds(companyId, depHist.identifier(), listDepartmentId));
			List<String> listAccquiredId = result.stream().map(d -> d.getDepartmentId()).collect(Collectors.toList());
			listDepartmentId = listDepartmentId.stream().filter(id -> !listAccquiredId.contains(id))
					.collect(Collectors.toList());
			if (listDepartmentId.isEmpty())
				break;
		}
		result.sort((e1, e2) -> {
			return e1.getHierarchyCode().v().compareTo(e2.getHierarchyCode().v());
		});
		return result;
	}

}
