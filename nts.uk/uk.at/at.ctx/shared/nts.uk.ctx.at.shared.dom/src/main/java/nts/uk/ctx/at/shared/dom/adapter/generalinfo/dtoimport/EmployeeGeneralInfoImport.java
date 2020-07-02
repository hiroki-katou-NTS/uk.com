package nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class EmployeeGeneralInfoImport {
	
	/** 雇用履歴一覧 */
	List<ExEmploymentHistoryImport> employmentHistoryImports;
	
	/** 分類履歴一覧 */
	List<ExClassificationHistoryImport> exClassificationHistoryImports;
	
	/** 職位履歴一覧 */
	List<ExJobTitleHistoryImport> exJobTitleHistoryImports;
	
	/** 職場履歴一覧 */
	List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports;

	/** 勤務種別一覧 */
	List<ExWorkTypeHistoryImport> exWorkTypeHistoryImports;
	
	/** 部門履歴一覧 */
	List<ExDepartmentHistoryImport> exDepartmentHistoryImports;

	public EmployeeGeneralInfoImport(List<ExEmploymentHistoryImport> employmentHistoryImports,
			List<ExClassificationHistoryImport> exClassificationHistoryImports,
			List<ExJobTitleHistoryImport> exJobTitleHistoryImports,
			List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports) {
		super();
		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;
	}

	public EmployeeGeneralInfoImport(List<ExEmploymentHistoryImport> employmentHistoryImports,
			List<ExClassificationHistoryImport> exClassificationHistoryImports,
			List<ExJobTitleHistoryImport> exJobTitleHistoryImports,
			List<ExWorkPlaceHistoryImport> exWorkPlaceHistoryImports,
			List<ExWorkTypeHistoryImport> exWorkTypeHistoryImports) {
		super();
		this.employmentHistoryImports = employmentHistoryImports;
		this.exClassificationHistoryImports = exClassificationHistoryImports;
		this.exJobTitleHistoryImports = exJobTitleHistoryImports;
		this.exWorkPlaceHistoryImports = exWorkPlaceHistoryImports;
		this.exWorkTypeHistoryImports = exWorkTypeHistoryImports;
	}
	
}
