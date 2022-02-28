package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import java.util.List;
import java.util.Map;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;

/**
 * 社員Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.Imported.社員Adapter
 * @author dan_pv
 */
public interface EmployeeAdapter {

	/**
	 * 社員コードから社員IDを取得する
	 * @param companyId 会社ID
	 * @param employeeCodes 社員コードリスト
	 * @return Map<社員コード, 社員ID>
	 */
	Map<String, String> getEmployeeIdFromCode(String companyId, List<String> employeeCodes);

	/**
	 * 社員IDリストから社員コードと表示名を取得する
	 * @param employeeIds 社員IDリスト
	 * @return List<社員コードと表示名Imported>
	 */
	List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds);

	/**
	 * 社員の情報を取得する
	 * ※ UKDesign.クエリ.社員の情報を取得.アルゴリズム.社員の情報を取得する.<<Public>> 社員の情報を取得する
	 * @param employeeIds 社員IDリスト
	 * @param baseDate 年月日
	 * @param param 取得したい社員情報
	 * @return List<社員情報Imported>
	 */
	List<EmployeeInfoImported> getEmployeeInfo(List<String> employeeIds, GeneralDate baseDate, EmployeeInfoWantToBeGet param);

}
