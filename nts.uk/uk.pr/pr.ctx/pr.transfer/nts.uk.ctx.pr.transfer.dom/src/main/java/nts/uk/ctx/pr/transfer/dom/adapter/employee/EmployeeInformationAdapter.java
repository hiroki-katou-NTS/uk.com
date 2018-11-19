package nts.uk.ctx.pr.transfer.dom.adapter.employee;

import java.util.List;

public interface EmployeeInformationAdapter {

    List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param);
}
