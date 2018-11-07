package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;

import java.util.Optional;

public interface DepartmentAdapter {
    Optional<Department> getDepartmentByBaseDate(String employeeId, GeneralDate baseDate);
}
