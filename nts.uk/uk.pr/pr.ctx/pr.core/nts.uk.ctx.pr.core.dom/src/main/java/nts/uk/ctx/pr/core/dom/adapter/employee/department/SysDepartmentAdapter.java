package nts.uk.ctx.pr.core.dom.adapter.employee.department;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface SysDepartmentAdapter {
    List<DepartmentImport> getDepartmentByCompanyIdAndBaseDate(String companyId, GeneralDate baseDate);
}
