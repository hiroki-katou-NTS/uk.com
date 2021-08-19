
package nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo;

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
     * @return Map<String   , String>
     */
    Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes);

    /**
     * [2] 社員IDリストから社員コードと表示名を取得する
     *
     * @param sIds List<社員ID>
     * @return List<社員コードと表示名Imported	>
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
}
