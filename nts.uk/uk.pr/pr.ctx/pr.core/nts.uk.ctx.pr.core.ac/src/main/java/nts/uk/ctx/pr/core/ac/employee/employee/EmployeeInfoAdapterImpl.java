package nts.uk.ctx.pr.core.ac.employee.employee;

import nts.uk.ctx.bs.employee.pub.employee.SyEmployeePub;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoAdapter;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
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

    @Override
    public List<EmployeeInfoEx> findBySIds(List<String> sIds) {
        return syEmployeePub.findBySIds(sIds).stream().map(i -> new EmployeeInfoEx(i.getPId(), i.getEmployeeId(), i.getPName(), i.getGender(), i.getBirthDay(), i.getEmployeeCode())).collect(Collectors.toList());
    }
}