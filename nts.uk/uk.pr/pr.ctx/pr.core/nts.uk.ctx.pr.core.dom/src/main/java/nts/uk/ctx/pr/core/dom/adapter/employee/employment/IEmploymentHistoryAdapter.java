package nts.uk.ctx.pr.core.dom.adapter.employee.employment;

import java.util.Optional;


public interface IEmploymentHistoryAdapter {
    Optional<EmploymentHisExport> getEmploymentHistory(String historyId, String employmentCode);
}
