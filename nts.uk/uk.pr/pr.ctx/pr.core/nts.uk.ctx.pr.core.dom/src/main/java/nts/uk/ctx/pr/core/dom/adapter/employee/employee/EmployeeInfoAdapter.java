package nts.uk.ctx.pr.core.dom.adapter.employee.employee;

import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.EmployeeInfoImport;

import java.util.List;

public interface EmployeeInfoAdapter {
    List<EmployeeInfoImport> getByListSid(List<String> sIds);

    List<EmployeeInfoEx> findBySIds(List<String> sIds);
}