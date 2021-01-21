package nts.uk.ctx.at.record.ac.eligibleemployees;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeavePeriodAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeaveHolidayPeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeavePeriod;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LeavePeriodAdapterImpl implements LeavePeriodAdapter {
    @Inject
    private EmployeeLeaveHistoryPublish employeeLeaveHistoryPublish;
    @Override
    public List<LeavePeriod> GetLeavePeriod(List<String> sids, DatePeriod datePeriod) {

        val rs = employeeLeaveHistoryPublish.getBySpecifyingPeriod(sids,datePeriod);
        val result = new ArrayList<LeavePeriod>();
        rs.stream().forEach(e->{
            result.add(new LeavePeriod(e.getDatePeriod(),e.getEmpID()));
        });
        return result;
    }
}
