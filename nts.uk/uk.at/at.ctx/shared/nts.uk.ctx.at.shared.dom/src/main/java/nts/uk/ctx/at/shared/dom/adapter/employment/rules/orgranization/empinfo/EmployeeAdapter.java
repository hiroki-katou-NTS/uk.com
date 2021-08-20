
package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Map;

/**
 * 社員Adapter
 *
 * @author rafiqul.islam
 */
public interface EmployeeAdapter {
    /**
     * [1] 社員コードから社員IDを取得する
     *
     * @param companyId the company id
     * @param empCodes  the emp codes
     * @return Map<String       ,   String>
     */
    Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes);

    /**
     * [2] 社員IDリストから社員コードと表示名を取得する
     *
     * @param sIds List<社員ID>
     * @return List<社員コードと表示名Imported 	>
     */
    List<EmployeeInfoImport> getByListSid(List<String> sIds);

    /**
     * [3] 社員の情報を取得する[アルゴリズム.<<Public>> 社員の情報を取得する( 社員IDリスト, 基準日, 取得したい社員情報 )]
     *
     * @param employeeIds   List<社員ID>
     * @param referenceDate 年月日
     * @param param         取得したい社員情報
     * @return List<EmployeeInformationImport>
     */
    public List<EmployeeInformationImport> find(List<String> employeeIds, GeneralDate referenceDate, EmployeeInfoQueryDto param);
}
