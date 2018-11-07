package nts.uk.ctx.pr.core.ac.wageprovision.statementbindingsetting;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.AffDepartHistory;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.AffDepartHistoryAdapter;

import java.util.Optional;

public class AffDepartHisAdapterImple implements AffDepartHistoryAdapter {
    @Override
    public Optional<AffDepartHistory> getDepartmentByBaseDate(String employeeId, GeneralDate baseDate) {
        return Optional.empty();
    }
}
