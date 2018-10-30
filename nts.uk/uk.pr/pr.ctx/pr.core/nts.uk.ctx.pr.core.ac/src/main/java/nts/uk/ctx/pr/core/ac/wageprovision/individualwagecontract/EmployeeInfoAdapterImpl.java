package nts.uk.ctx.pr.core.ac.wageprovision.individualwagecontract;

import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoImport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class EmployeeInfoAdapterImpl implements EmployeeInfoAdapter {
    @Inject
    private SyEmployeePub syEmployeePub;

    @Override
    public List<EmployeeInfoImport> getByListSid(List<String> sIds) {
        return syEmployeePub.getByListSid(sIds).stream().map(x -> new EmployeeInfoImport(x.getSid(), x.getScd(), x.getBussinessName())).collect(Collectors.toList());
    }
}