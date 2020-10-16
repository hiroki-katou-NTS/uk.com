package nts.uk.ctx.sys.auth.dom.adapter.employee.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInfoAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.employee.employeeinfo.EmployeeInformationImport;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class EmployeeService {

    @Inject
    EmployeeInfoAdapter employeeInfoAdapter;

    public EmployeeInformationImport getEmployeeInformation(String employeeId, GeneralDate baseDate) {
        return employeeInfoAdapter.findEmployeeInformation(employeeId, baseDate);
    }
}
