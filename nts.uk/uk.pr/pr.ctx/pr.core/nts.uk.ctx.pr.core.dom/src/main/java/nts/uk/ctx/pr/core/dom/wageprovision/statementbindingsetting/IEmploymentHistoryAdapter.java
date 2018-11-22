package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import java.util.Optional;


public interface IEmploymentHistoryAdapter {
    Optional<EmploymentHisExport> getEmploymentHistory(String historyId, String employmentCode);
}
