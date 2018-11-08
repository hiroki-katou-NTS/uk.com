package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.IEmploymentHistoryAdapter;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class IEmploymentHistoryImple implements IEmploymentHistoryAdapter {
    @Override
    public Optional<EmploymentHisExport> getEmploymentHistory(String historyId, String employmentCode) {
        return Optional.empty();
    }
}
