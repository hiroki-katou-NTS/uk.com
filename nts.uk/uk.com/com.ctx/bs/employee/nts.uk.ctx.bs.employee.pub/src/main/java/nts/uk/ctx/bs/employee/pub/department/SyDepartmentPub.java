package nts.uk.ctx.bs.employee.pub.department;

import nts.arc.time.GeneralDate;

import java.util.List;

public interface SyDepartmentPub {

    // for salary qmm016, 017
    List<DepartmentExport> getDepartmentByCompanyIdAndBaseDate(String companyId, GeneralDate baseDate);

}
