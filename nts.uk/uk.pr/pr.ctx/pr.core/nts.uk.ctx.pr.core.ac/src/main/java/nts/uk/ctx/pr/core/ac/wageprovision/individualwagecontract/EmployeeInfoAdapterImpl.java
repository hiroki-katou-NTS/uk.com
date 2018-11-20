package nts.uk.ctx.pr.core.ac.wageprovision.individualwagecontract;

//import nts.uk.query.pub.employee.EmployeeInformationPub;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmployeeInformationAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmployeeInformationImport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmployeeInformationQueryDtoImport;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class EmployeeInfoAdapterImpl implements EmployeeInformationAdapter {
    @Override
    public List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param) {
        return null;
    }
//    @Inject
//    EmployeeInformationPub employeeInformationPub;
//
//    @Override
//    public List<EmployeeInfoImport> getByListSid(List<String> sIds) {
//        return syEmployeePub.getByListSid(sIds).stream().map(x -> new EmployeeInfoImport(x.getSid(), x.getScd(), x.getBussinessName())).collect(Collectors.toList());
//    }
}
