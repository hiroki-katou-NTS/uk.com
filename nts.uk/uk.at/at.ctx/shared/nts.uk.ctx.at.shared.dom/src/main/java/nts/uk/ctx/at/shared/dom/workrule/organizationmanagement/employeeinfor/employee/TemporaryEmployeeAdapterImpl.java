package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employee.importeddto.EmployeeInfoImported;

import javax.ejb.Stateless;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dummy implementation, waiting for real implementation
 */
@Stateless
public class TemporaryEmployeeAdapterImpl implements EmployeeAdapter {
    @Override
    public List<EmployeeInfoImported> getEmployeeInfo(List<String> employeeIds, GeneralDate baseDate, EmployeeInfoWantToBeGet param) {
        return employeeIds.stream().map(id -> {
            EmployeeInfoImported info = new EmployeeInfoImported(
                    id,
                    "code",
                    "business name",
                    "business name kana",
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty(),
                    Optional.empty()
            );
            return info;
        }).collect(Collectors.toList());
    }
}
