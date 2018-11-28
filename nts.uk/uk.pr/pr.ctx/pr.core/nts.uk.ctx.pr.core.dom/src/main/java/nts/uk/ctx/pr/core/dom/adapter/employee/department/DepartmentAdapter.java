package nts.uk.ctx.pr.core.dom.adapter.employee.department;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

public interface DepartmentAdapter {
    List<WkpConfigAtTimeExport>  getDepartmentByBaseDate(String companyId, GeneralDate baseDate, List<String> wkpIds);
    Optional<Department> getDepartmentByDepartmentId(String departmentId);
}
