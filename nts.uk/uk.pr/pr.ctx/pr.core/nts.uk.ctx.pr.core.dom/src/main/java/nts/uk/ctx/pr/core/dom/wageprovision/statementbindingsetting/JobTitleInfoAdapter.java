package nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;

import java.util.List;
import java.util.Optional;

public interface JobTitleInfoAdapter {
    List<JobTitle> getJobTitleInfoByBaseDate(String cid, GeneralDate baseDate);
    Optional<EmployeeJobHistExport> findSJobHistBySId(String employeeId, GeneralDate baseDate);
}
