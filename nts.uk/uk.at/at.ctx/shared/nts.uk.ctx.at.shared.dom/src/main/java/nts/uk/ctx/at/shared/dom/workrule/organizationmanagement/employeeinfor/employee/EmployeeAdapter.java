package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime.ReferEmployeeInformation;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.EmployeeCodeAndDisplayNameImport;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmployeeInformationImport;

import java.util.List;
import java.util.Map;

/**
 * 社員Adapter
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.shared.就業規則.組織管理.社員情報.社員.Imported.社員Adapter
 *
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
     * 社員IDリストから社員コードと表示名を取得する
     *
     * @param employeeIds 社員IDリスト
     * @return
     */
    List<EmployeeCodeAndDisplayNameImport> getEmployeeCodeAndDisplayNameImportByEmployeeIds(List<String> employeeIds);

    /**
     * Find.
     *
     * @param param the param
     * @return the list
     */
    // <<Public>> 社員の情報を取得する
    public List<EmployeeInformationImport> find(EmployeeInformationQueryDto param);

}
