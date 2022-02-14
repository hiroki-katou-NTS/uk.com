package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmployeeInformationImport;

/**
 * 社員Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.Imported.社員Adapter
 * @author dan_pv
 */
public interface EmployeeAdapter {

	/**
	 * 社員コードから社員IDを取得する
	 *
	 * @param companyId     会社ID
	 * @param employeeCodes List<社員コード>
	 * @return
	 */
	Map<String, String> getEmployeeIdFromCode(String companyId, List<String> employeeCodes);
	/**
	 * [1] 社員コードから社員IDを取得する
	 *
	 * @param companyId the company id
	 * @param empCodes  the emp codes
	 * @return Map<String, String>
	 */
	Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes);

	/**
	 * 社員IDリストから社員コードと表示名を取得する
	 *
	 * @param employeeIds 社員IDリスト
	 * @return
	 */
	List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds);
	/**
	 * [2] 社員IDリストから社員コードと表示名を取得する
	 *
	 * @param sIds List<社員ID>
	 * @return List<社員コードと表示名Imported 	>
	 */
	List<EmployeeInfoImport> getByListSid(List<String> sIds);

	/**
	 * Find.
	 *
	 * @param param the param
	 * @return the list
	 */
	// <<Public>> 社員の情報を取得する
	public List<EmployeeInformationImport> find(EmployeeInformationQueryDto param);
	/**
	 * [3] 社員の情報を取得する[アルゴリズム.<<Public>> 社員の情報を取得する( 社員IDリスト, 基準日, 取得したい社員情報 )]
	 *
	 * @param employeeIds   List<社員ID>
	 * @param baseDate 年月日
	 * @param param         取得したい社員情報
	 * @return List<EmployeeInformationImport>
	 */
	List<EmployeeInfoImported> getEmployeeInfo(
			List<String> employeeIds,
			GeneralDate baseDate,
			EmployeeInfoWantToBeGet param);

	Optional<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndNameByEmployeeId(String employeeId);

}
