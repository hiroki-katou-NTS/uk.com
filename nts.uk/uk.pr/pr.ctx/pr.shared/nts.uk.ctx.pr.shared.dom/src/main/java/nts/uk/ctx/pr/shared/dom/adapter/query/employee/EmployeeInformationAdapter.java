package nts.uk.ctx.pr.shared.dom.adapter.query.employee;

import java.util.List;

public interface EmployeeInformationAdapter {
    List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param);
}
