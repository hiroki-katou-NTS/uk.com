package nts.uk.ctx.bs.employee.pubimp.department.master;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.department.affiliate.AffDepartmentHistoryItemRepository;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentConfiguration;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformation;
import nts.uk.ctx.bs.employee.dom.department.master.DepartmentInformationRepository;
import nts.uk.ctx.bs.employee.dom.department.master.service.DepartmentExportSerivce;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentExport;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentInforExport;
import nts.uk.ctx.bs.employee.pub.department.master.DepartmentPub;

@Stateless
public class DepartmentPubImpl implements DepartmentPub {

	@Inject
	private DepartmentExportSerivce depExpService;
	@Inject
	private DepartmentInformationRepository depInforRepo;
	
	@Inject
	private AffDepartmentHistoryItemRepository affDepartmentHistoryItemRepository;
	
	@Inject
	private DepartmentConfigurationRepository departmentConfigurationRepository;
	
	@Inject
	private DepartmentInformationRepository departmentInformationRepository;

	@Override
	public List<DepartmentInforExport> getDepartmentInforByDepIds(String companyId, List<String> listDepartmentId,
			GeneralDate baseDate) {
		return depExpService.getDepartmentInforFromDepIds(companyId, listDepartmentId, baseDate).stream()
				.map(i -> new DepartmentInforExport(i.getDepartmentId(), i.getHierarchyCode(), i.getDepartmentCode(),
						i.getDepartmentName(), i.getDisplayName(), i.getGenericName(), i.getExternalCode()))
				.collect(Collectors.toList());
	}

	@Override
	public List<DepartmentInforExport> getAllActiveDepartment(String companyId, GeneralDate baseDate) {
		return depExpService.getAllActiveDepartment(companyId, baseDate).stream()
				.map(i -> new DepartmentInforExport(i.getDepartmentId(), i.getHierarchyCode().v(),
						i.getDepartmentCode().v(), i.getDepartmentName().v(), i.getDepartmentDisplayName().v(),
						i.getDepartmentGeneric().v(),
						i.getDepartmentExternalCode().isPresent() ? i.getDepartmentExternalCode().get().v() : null))
				.collect(Collectors.toList());
	}

	@Override
	public List<DepartmentInforExport> getPastDepartmentInfor(String companyId, String depHistId,
			List<String> listDepartmentId) {
		return depExpService.getPastDepartmentInfor(companyId, depHistId, listDepartmentId).stream()
				.map(i -> new DepartmentInforExport(i.getDepartmentId(), i.getHierarchyCode(), i.getDepartmentCode(),
						i.getDepartmentName(), i.getDisplayName(), i.getGenericName(), i.getExternalCode()))
				.collect(Collectors.toList());
	}
	
    // for salary qmm016, 017
	@Override
	public List<DepartmentExport> getDepartmentByCompanyIdAndBaseDate(String companyId, GeneralDate baseDate) {
		return depExpService.getAllActiveDepartment(companyId, baseDate).stream().map(item -> {
			return DepartmentExport.builder().companyId(item.getCompanyId()).depHistoryId(item.getDepartmentHistoryId())
					.departmentId(item.getDepartmentId()).departmentCode(item.getDepartmentCode().v())
					.departmentName(item.getDepartmentName().v()).depDisplayName(item.getDepartmentDisplayName().v())
					.depGenericName(item.getDepartmentGeneric().v())
					.outsideDepCode(item.getDepartmentExternalCode().map(ec -> ec.v())).build();
		}).collect(Collectors.toList());
	}

	@Override
	public List<String> getAllChildDepartmentId(String companyId, GeneralDate baseDate, String parentDepartmentId) {
		return depExpService.getAllChildDepartmentId(companyId, baseDate, parentDepartmentId);
	}

	@Override
	public List<String> getDepartmentIdAndChildren(String companyId, GeneralDate baseDate, String departmentId) {
		return depExpService.getDepartmentIdAndChildren(companyId, baseDate, departmentId);
	}

	@Override
	public Optional<DepartmentExport> getInfoDep(String companyId, String depId) {
		return depInforRepo.getInfoDep(companyId, depId).
				map(item -> DepartmentExport.builder()
				.companyId(item.getCompanyId()).depHistoryId(item.getDepartmentHistoryId())
				.departmentId(item.getDepartmentId()).departmentCode(item.getDepartmentCode().v())
				.departmentName(item.getDepartmentName().v()).depDisplayName(item.getDepartmentDisplayName().v())
				.depGenericName(item.getDepartmentGeneric().v())
				.outsideDepCode(item.getDepartmentExternalCode().map(ec -> ec.v())).build());
		
	}

	@Override
	public String getDepartmentIDByEmpDate(String employeeID, GeneralDate date) {
		return affDepartmentHistoryItemRepository.findByEmpDate(employeeID, date).get().getDepartmentId();
	}

	@Override
	public List<String> getUpperDepartment(String companyID, String departmentID, GeneralDate date) {
		// ドメインモデル「部門構成」を取得する(lấy domain 「WorkplaceConfig」)
		Optional<DepartmentConfiguration> opDepartmentConfig = departmentConfigurationRepository.findByDate(companyID, date);
		if(!opDepartmentConfig.isPresent()) {
			throw new RuntimeException("error department config");
		}
		// ドメインモデル「部門情報」を取得する
		DepartmentInformation departmentInfor = departmentInformationRepository.getActiveDepartmentByDepIds(
				companyID, 
				opDepartmentConfig.get().items().get(0).identifier(), 
				Arrays.asList(departmentID)).get(0);
		// 取得した階層コードの上位階層コードを求める(Tìm upperHierarchyCode của HierarchyCode đã lấy)
		List<String> hierachyCDLst = new ArrayList<>();
		String sumCD = departmentInfor.getHierarchyCode().toString();
		sumCD = sumCD.substring(0, sumCD.length() - 3);
		hierachyCDLst.add(sumCD.substring(0, 3));
		sumCD = sumCD.substring(3, sumCD.length());
		while(sumCD.length() > 6) {
			hierachyCDLst.add(sumCD.substring(0, 6));
			sumCD = sumCD.substring(6, sumCD.length()); 
		}
		hierachyCDLst.add(sumCD);
		Collections.reverse(hierachyCDLst);
		return hierachyCDLst;
	}

}
