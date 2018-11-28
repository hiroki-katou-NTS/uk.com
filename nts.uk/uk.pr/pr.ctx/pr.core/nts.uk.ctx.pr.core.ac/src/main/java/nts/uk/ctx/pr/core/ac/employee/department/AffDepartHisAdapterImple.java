package nts.uk.ctx.pr.core.ac.employee.department;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistory;
import nts.uk.ctx.pr.core.dom.adapter.employee.department.AffDepartHistoryAdapter;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class AffDepartHisAdapterImple implements AffDepartHistoryAdapter {
    @Override
    public Optional<AffDepartHistory> getDepartmentByBaseDate(String employeeId, GeneralDate baseDate) {
        return Optional.empty();
    }

}
