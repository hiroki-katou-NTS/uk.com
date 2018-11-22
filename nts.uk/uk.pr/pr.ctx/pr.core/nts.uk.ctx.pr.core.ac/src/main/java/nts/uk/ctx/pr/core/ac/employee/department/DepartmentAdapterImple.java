package nts.uk.ctx.pr.core.ac.employee.department;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.Department;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.DepartmentAdapter;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class DepartmentAdapterImple implements DepartmentAdapter {
    @Override
    public Optional<Department> getDepartmentByBaseDate(String employeeId, GeneralDate baseDate) {
        return Optional.empty();
    }

    @Override
    public Optional<Department> getDepartmentByDepartmentId(String departmentId) {
        return Optional.empty();
    }
}
