package nts.uk.ctx.pr.core.ac.employee.employment;

import nts.uk.ctx.pr.core.dom.adapter.employee.employment.EmploymentHisExport;
import nts.uk.ctx.pr.core.dom.adapter.employee.employment.IEmploymentHistoryAdapter;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class IEmploymentHistoryImple implements IEmploymentHistoryAdapter {
    @Override
    public Optional<EmploymentHisExport> getEmploymentHistory(String historyId, String employmentCode) {
        return Optional.empty();
    }
}
