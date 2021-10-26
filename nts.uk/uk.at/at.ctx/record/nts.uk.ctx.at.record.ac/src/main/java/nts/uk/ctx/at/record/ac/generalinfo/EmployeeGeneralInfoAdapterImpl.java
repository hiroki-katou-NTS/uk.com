package nts.uk.ctx.at.record.ac.generalinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.generalinfo.EmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExClassificationHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistItemImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExJobTitleHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkPlaceHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.ExWorkplaceHistItemImport;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpAffiliationInforAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeGeneralInfoAdapterImpl implements EmployeeGeneralInfoAdapter {

	@Inject
	private EmployeeGeneralInfoPub employeeGeneralInfoPub;
	
	@Inject
	private EmpAffiliationInforAdapter empAffInforAdapter;
	
	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
	
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;

	@Override
	public EmployeeGeneralInfoImport getEmployeeGeneralInfo(List<String> employeeIds, DatePeriod period,
			boolean checkEmployment, boolean checkClassification, boolean checkJobTitle, boolean checkWorkplace,
			boolean checkDepartment) {
		EmployeeGeneralInfoDto dto = this.employeeGeneralInfoPub.getPerEmpInfo(employeeIds, period, checkEmployment,
				checkClassification, checkJobTitle, checkWorkplace, checkDepartment);

		List<ExEmploymentHistoryImport> employmentHistoryImports = dto.getEmploymentDto().stream()
				.map(employmentHistory -> new ExEmploymentHistoryImport(employmentHistory.getEmployeeId(),
						employmentHistory.getEmploymentItems().stream().map(exEmpHistItem -> {
							return new ExEmploymentHistItemImport(exEmpHistItem.getHistoryId(),
									exEmpHistItem.getPeriod(), exEmpHistItem.getEmploymentCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExClassificationHistoryImport> exClassificationHistoryImports = dto.getClassificationDto().stream()
				.map(classificationHistory -> new ExClassificationHistoryImport(classificationHistory.getEmployeeId(),
						classificationHistory.getClassificationItems().stream().map(classificationHistItem -> {
							return new ExClassificationHistItemImport(classificationHistItem.getHistoryId(),
									classificationHistItem.getPeriod(), classificationHistItem.getClassificationCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExJobTitleHistoryImport> exJobTitleHistoryImports = dto.getJobTitleDto().stream()
				.map(jobTitle -> new ExJobTitleHistoryImport(jobTitle.getEmployeeId(),
						jobTitle.getJobTitleItems().stream().map(jobTitleItem -> {
							return new ExJobTitleHistItemImport(jobTitleItem.getHistoryId(), jobTitleItem.getPeriod(),
									jobTitleItem.getJobTitleId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports = dto.getWorkplaceDto().stream()
				.map(workPlaceHistory -> new ExWorkPlaceHistoryImport(workPlaceHistory.getEmployeeId(),
						workPlaceHistory.getWorkplaceItems().stream().map(workPlaceHistoryItem -> {
							return new ExWorkplaceHistItemImport(workPlaceHistoryItem.getHistoryId(),
									workPlaceHistoryItem.getPeriod(), workPlaceHistoryItem.getWorkplaceId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());
		
		// 社員の職場グループIDを取得する
		Map<GeneralDate, List<EmpOrganizationImport>>  exWkpGrImported = getWorkplaceGroupInfo(employeeIds, period);
		
		// 社員の免許区分の取得する
		Map<GeneralDate, List<EmpLicenseClassification>> exLicenseCls =  getEmpicenseCls(employeeIds, period);

		return new EmployeeGeneralInfoImport(employmentHistoryImports, exClassificationHistoryImports,
				exJobTitleHistoryImports, exWorkPlaceHistoryImports, exWkpGrImported, exLicenseCls);
	}
	
	// 社員の職場グループIDを取得する
	private Map<GeneralDate, List<EmpOrganizationImport>> getWorkplaceGroupInfo(List<String> employeeIds,
			DatePeriod period) {

		Map<GeneralDate, List<EmpOrganizationImport>> result = new HashMap<>();

		period.datesBetween().stream().forEach(date -> {
			
			List<EmpOrganizationImport> info = empAffInforAdapter.getEmpOrganization(date, employeeIds);
			
			result.put(date, info);
			
		});
		
		return result;
	}
	
	// 社員の免許区分の取得する
	private Map<GeneralDate, List<EmpLicenseClassification>> getEmpicenseCls(List<String> employeeIds, DatePeriod period) {
		
		Require require= new Require(empMedicalWorkStyleHistoryRepo, nurseClassificationRepo);
		
		Map<GeneralDate, List<EmpLicenseClassification>> result = new HashMap<>();
		
		period.datesBetween().stream().forEach(date -> {
			
			List<EmpLicenseClassification> info = GetEmpLicenseClassificationService.get(require, date, employeeIds);
			
			result.put(date, info);
			
		});
		
		return result;
	}
	
	@AllArgsConstructor
	private static class Require implements GetEmpLicenseClassificationService.Require {
		@Inject
		private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
		@Inject
		private NurseClassificationRepository nurseClassificationRepo;

		@Override
		public List<EmpMedicalWorkStyleHistoryItem> getEmpMedicalWorkStyleHistoryItem(List<String> listEmp, GeneralDate referenceDate) {
			return empMedicalWorkStyleHistoryRepo.get(listEmp, referenceDate);
		}

		@Override
		public List<NurseClassification> getListCompanyNurseCategory() {
			return nurseClassificationRepo.getListCompanyNurseCategory(AppContexts.user().companyId());
		}
	}

}
