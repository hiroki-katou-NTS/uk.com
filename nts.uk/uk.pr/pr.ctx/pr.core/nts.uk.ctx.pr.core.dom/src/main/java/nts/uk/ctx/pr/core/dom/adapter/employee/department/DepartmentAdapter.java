package nts.uk.ctx.pr.core.dom.adapter.employee.department;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface DepartmentAdapter {
    Optional<Department> getDepartmentByBaseDate(String employeeId, GeneralDate baseDate);
    Optional<Department> getDepartmentByDepartmentId(String departmentId);
}
