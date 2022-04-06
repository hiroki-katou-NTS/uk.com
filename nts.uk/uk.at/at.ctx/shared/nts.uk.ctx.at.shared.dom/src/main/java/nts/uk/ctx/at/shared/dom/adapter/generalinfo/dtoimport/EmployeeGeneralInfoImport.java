package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.employeeworkway.EmployeeWorkingStatus;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.employee.BusinessTypeOfEmployeeHis;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalcare.medicalworkstyle.EmpLicenseClassification;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;

/**
 * 特定期間の社員情報
 */
@NoArgsConstructor
@Getter
@Setter
public class EmployeeGeneralInfoImport {

	/** 雇用履歴一覧 */
	List<ExEmploymentHistoryImport> employmentHistoryImports = new ArrayList<>();

	/** 分類履歴一覧 */
	List<ExClassificationHistoryImport> exClassificationHistoryImports= new ArrayList<>();

	/** 職位履歴一覧 */
	List<ExJobTitleHistoryImport> exJobTitleHistoryImports= new ArrayList<>();

	/** 職場履歴一覧 */
	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports= new ArrayList<>();

	/** 勤務種別一覧 */
	List<BusinessTypeOfEmployeeHis> exWorkTypeHistoryImports= new ArrayList<>();

	/** 部門履歴一覧 */
	List<ExDepartmentHistoryImport> exDepartmentHistoryImports= new ArrayList<>();

	/** 職場グループ一覧 **/
	Map<GeneralDate, Map<String, String>> empWorkplaceGroup = Collections.emptyMap();

	/** 看護免許区分一覧 **/
	Map<GeneralDate, List<EmpLicenseClassification>> empLicense = Collections.emptyMap();

	public EmployeeGeneralInfoImport(List<ExEmploymentHistoryImport> employmentHistoryImports,
			List<ExClassificationHistoryImport> exClassificationHistoryImports,
			List<ExJobTitleHistoryImport> exJobTitleHistoryImports,
			List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports) {
		super();
		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;
		this.exWorkTypeHistoryImports = new ArrayList<>();
	}

	public EmployeeGeneralInfoImport(List<ExEmploymentHistoryImport> employmentHistoryImports,
			List<ExClassificationHistoryImport> exClassificationHistoryImports,
			List<ExJobTitleHistoryImport> exJobTitleHistoryImports,
			List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports,
			List<BusinessTypeOfEmployeeHis> exWorkTypeHistoryImports) {
		super();
		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;
		this.exWorkTypeHistoryImports = exWorkTypeHistoryImports;
	}

	public EmployeeGeneralInfoImport(
			List<ExEmploymentHistoryImport> employmentHistoryImports
		,	List<ExClassificationHistoryImport> exClassificationHistoryImports
		,	List<ExJobTitleHistoryImport> exJobTitleHistoryImports
		,	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports
		,	List<EmployeeWorkingStatus> empWorkingStatuses
		,	Map<GeneralDate, List<EmpLicenseClassification>> empLicense
	) {

		super();

		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;

		this.empWorkplaceGroup = convertToMapFrom(empWorkingStatuses);
		this.empLicense = empLicense;

	}

	public EmployeeGeneralInfoImport(
			List<ExEmploymentHistoryImport> employmentHistoryImports
		,	List<ExClassificationHistoryImport> exClassificationHistoryImports
		,	List<ExJobTitleHistoryImport> exJobTitleHistoryImports
		,	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports
		,	Map<GeneralDate, List<EmpOrganizationImport>> empWorkplaceGroup
		,	Map<GeneralDate, List<EmpLicenseClassification>> empLicense
	) {

		super();

		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;

		this.empWorkplaceGroup = convertToMapFrom(empWorkplaceGroup);
		this.empLicense = empLicense;

	}

	public EmployeeGeneralInfoImport(
			List<ExEmploymentHistoryImport> employmentHistoryImports
		,	List<ExClassificationHistoryImport> exClassificationHistoryImports
		,	List<ExJobTitleHistoryImport> exJobTitleHistoryImports
		,	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports
		,	List<BusinessTypeOfEmployeeHis> exWorkTypeHistoryImports
		,	List<EmployeeWorkingStatus> empWorkingStatuses
		,	Map<GeneralDate, List<EmpLicenseClassification>> empLicense
	) {

		super();

		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;

		this.exWorkTypeHistoryImports = exWorkTypeHistoryImports;

		this.empWorkplaceGroup = convertToMapFrom(empWorkingStatuses);
		this.empLicense = empLicense;

	}

	public EmployeeGeneralInfoImport(
			List<ExEmploymentHistoryImport> employmentHistoryImports
		,	List<ExClassificationHistoryImport> exClassificationHistoryImports
		,	List<ExJobTitleHistoryImport> exJobTitleHistoryImports
		,	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports
		,	List<BusinessTypeOfEmployeeHis> exWorkTypeHistoryImports
		,	Map<GeneralDate, List<EmpOrganizationImport>> empWorkplaceGroup
		,	Map<GeneralDate, List<EmpLicenseClassification>> empLicense
	) {

		super();

		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;

		this.exWorkTypeHistoryImports = exWorkTypeHistoryImports;

		this.empWorkplaceGroup = convertToMapFrom(empWorkplaceGroup);
		this.empLicense = empLicense;

	}



	private static Map<GeneralDate, Map<String, String>> convertToMapFrom(List<EmployeeWorkingStatus> statuses) {

		return statuses.stream()
				.filter( status -> status.getWorkplaceGroupId().isPresent() )
				.collect(Collectors.groupingBy( EmployeeWorkingStatus::getDate ))
			.entrySet().stream()
				.collect(Collectors.toMap(
						Map.Entry::getKey
					,	entry -> entry.getValue().stream().collect(Collectors.toMap(
											EmployeeWorkingStatus::getEmployeeID
										,	status -> (String)status.getWorkplaceGroupId().get()
									))
				));

	}

	private static Map<GeneralDate, Map<String, String>> convertToMapFrom(Map<GeneralDate, List<EmpOrganizationImport>> importsPerYmd) {

		return importsPerYmd.entrySet().stream()
			.collect(Collectors.toMap(
							Map.Entry::getKey
						,	entryPerYmd -> entryPerYmd.getValue().stream()
								.filter( imported -> imported.getWorkplaceGroupId().isPresent() )
								.collect(Collectors.toMap(
												imported -> imported.getEmpId().v()
											,	imported -> imported.getWorkplaceGroupId().get()
										))
					));

	}

}
