package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;

/**
 * 社員Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.Imported.社員Adapter
 * @author dan_pv
 */
public interface EmployeeAdapter {
	
	/**
	 * 社員IDリストから社員コードと表示名を取得する	
	 * @param employeeIds 社員IDリスト
	 * @return
	 */
	List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds);

}
