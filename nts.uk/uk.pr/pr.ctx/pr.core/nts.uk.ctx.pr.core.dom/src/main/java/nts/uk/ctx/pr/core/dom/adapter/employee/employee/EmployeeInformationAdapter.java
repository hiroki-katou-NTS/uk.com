package nts.uk.ctx.pr.core.dom.adapter.employee.employee;

import java.util.List;

public interface EmployeeInformationAdapter {
    List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param);
}
