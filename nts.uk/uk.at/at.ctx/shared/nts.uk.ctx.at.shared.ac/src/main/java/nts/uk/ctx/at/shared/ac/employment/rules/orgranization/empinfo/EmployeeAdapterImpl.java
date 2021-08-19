
package nts.uk.ctx.at.shared.ac.employment.rules.orgranization.empinfo;

import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInformationImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.rules.orgranization.empinfo.EmployeeInformationQueryDto;
import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.bs.employee.pub.employment.IEmploymentHistoryPub;
import nts.uk.ctx.bs.employee.pub.employment.SyEmploymentPub;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class EmployeeAdapterImpl implements EmployeeAdapter {

    /**
     * The employment.
     */
    @Inject
    public SyEmploymentPub employment;

    @Inject
    private IEmploymentHistoryPub employmentHistoryPub;

    @Inject
    private SyEmployeePub employeePub;
    @Inject
    nts.uk.query.pub.employee.EmployeeInformationPub employeeInformationPub;


    /**
     * [1] 社員コードから社員IDを取得する
     *
     * @param companyId 会社ID
     * @param empCodes  List<社員コード>
     * @return Map<社員コード       ,       社員ID>	                                                               ,                               S               t               r               ing>
     */
    @Override
    public Map<String, String> getEmploymentMapCodeName(String companyId, List<String> empCodes) {
        return employment.getEmploymentMapCodeName(companyId, empCodes);
    }

    /**
     * [2] 社員IDリストから社員コードと表示名を取得する
     *
     * @param sIds List<社員ID>
     * @return List<社員コードと表示名Imported        >
     */
    @Override
    public List<EmployeeInfoImport> getByListSid(List<String> sIds) {
        return employeePub.getByListSid(sIds).stream()
                .map(x -> new EmployeeInfoImport(
                        x.getSid(),
                        x.getScd(),
                        x.getBussinessName()
                )).collect(Collectors.toList());
    }

    /**
     * Find.
     *
     * @param param the param
     * @return the list
     */
    @Override
    public List<EmployeeInformationImport> find(EmployeeInformationQueryDto param) {
        return null;
    }
}
