package nts.uk.ctx.at.schedule.ac.generalinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.EmployeeGeneralInfoImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.ScEmployeeGeneralInfoAdapter;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.classification.ExClassificationHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.employment.ExEmploymentHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistItemImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.jobtitle.ExJobTitleHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkPlaceHistoryImported;
import nts.uk.ctx.at.schedule.dom.adapter.generalinfo.workplace.ExWorkplaceHistItemImported;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpMedicalWorkStyleHistoryRepository;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.GetEmpLicenseClassificationService;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassification;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.NurseClassificationRepository;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoDto;
import nts.uk.ctx.bs.employee.pub.generalinfo.EmployeeGeneralInfoPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class ScEmployeeGeneralInfoImpl implements ScEmployeeGeneralInfoAdapter {

	@Inject
	private EmployeeGeneralInfoPub employeeGeneralInfoPub;

	@Inject
	private EmpMedicalWorkStyleHistoryRepository empMedicalWorkStyleHistoryRepo;
	@Inject
	private NurseClassificationRepository nurseClassificationRepo;

	@Override
	public EmployeeGeneralInfoImported getPerEmpInfo(List<String> employeeIds, DatePeriod period) {
		EmployeeGeneralInfoDto employeeGeneralInfoDto = this.employeeGeneralInfoPub.getPerEmpInfo(employeeIds, period,true,true,true,true,true);

		List<ExEmploymentHistoryImported> employmentImported = employeeGeneralInfoDto.getEmploymentDto().stream()
				.map(employmentHistory -> new ExEmploymentHistoryImported(employmentHistory.getEmployeeId(),
						employmentHistory.getEmploymentItems().stream().map(exEmpHistItem -> {
							return new ExEmploymentHistItemImported(exEmpHistItem.getHistoryId(),
									exEmpHistItem.getPeriod(), exEmpHistItem.getEmploymentCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExClassificationHistoryImported> exClassificationHistoryImported = employeeGeneralInfoDto
				.getClassificationDto().stream()
				.map(classificationHistory -> new ExClassificationHistoryImported(classificationHistory.getEmployeeId(),
						classificationHistory.getClassificationItems().stream().map(classificationHistItem -> {
							return new ExClassificationHistItemImported(classificationHistItem.getHistoryId(),
									classificationHistItem.getPeriod(), classificationHistItem.getClassificationCode());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExJobTitleHistoryImported> exJobTitleHistoryImported = employeeGeneralInfoDto.getJobTitleDto().stream()
				.map(jobTitle -> new ExJobTitleHistoryImported(jobTitle.getEmployeeId(),
						jobTitle.getJobTitleItems().stream().map(jobTitleItem -> {
							return new ExJobTitleHistItemImported(jobTitleItem.getHistoryId(), jobTitleItem.getPeriod(),
									jobTitleItem.getJobTitleId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		List<ExWorkPlaceHistoryImported> exWorkPlaceHistoryImported = employeeGeneralInfoDto.getWorkplaceDto().stream()
				.map(workPlaceHistory -> new ExWorkPlaceHistoryImported(workPlaceHistory.getEmployeeId(),
						workPlaceHistory.getWorkplaceItems().stream().map(workPlaceHistoryItem -> {
							return new ExWorkplaceHistItemImported(workPlaceHistoryItem.getHistoryId(),
									workPlaceHistoryItem.getPeriod(), workPlaceHistoryItem.getWorkplaceId());
						}).collect(Collectors.toList())))
				.collect(Collectors.toList());

		// 社員の免許区分の取得する
		Map<GeneralDate, List<EmpLicenseClassification>> exLicenseCls =  getEmpicenseCls(employeeIds, period);

		return new EmployeeGeneralInfoImported(employmentImported, exClassificationHistoryImported,
				exJobTitleHistoryImported, exWorkPlaceHistoryImported, exLicenseCls);

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
