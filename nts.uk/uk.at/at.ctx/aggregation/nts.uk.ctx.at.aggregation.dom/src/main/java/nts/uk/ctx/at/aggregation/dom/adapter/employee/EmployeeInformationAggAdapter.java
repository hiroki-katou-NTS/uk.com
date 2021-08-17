package nts.uk.ctx.at.aggregation.dom.adapter.employee;

import java.util.List;

public interface EmployeeInformationAggAdapter {
    List<EmployeeInformationImport> getEmployeeInfo(EmployeeInformationQueryDtoImport param);
}
