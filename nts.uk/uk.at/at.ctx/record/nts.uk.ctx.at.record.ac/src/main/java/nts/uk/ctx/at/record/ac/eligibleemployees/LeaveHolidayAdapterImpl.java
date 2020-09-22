package nts.uk.ctx.at.record.ac.eligibleemployees;

import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeaveHolidayAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeaveHolidayPeriod;
import nts.uk.ctx.bs.employee.pub.temporaryabsence.EmployeeLeaveHistoryPublish;
import nts.uk.ctx.bs.employee.pub.workplace.master.WorkplacePub;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LeaveHolidayAdapterImpl implements LeaveHolidayAdapter {
    @Inject
    private EmployeeLeaveHistoryPublish employeeLeaveHistoryPublish;
    @Override
    public List<LeaveHolidayPeriod> GetLeaveHolidayPeriod(List<String> sids, DatePeriod datePeriod) {
        val rs = employeeLeaveHistoryPublish.getHolidayPeriod(sids,datePeriod);
        val result = new ArrayList<LeaveHolidayPeriod>();
        rs.stream().forEach(e->{
            result.add(new LeaveHolidayPeriod(e.getDatePeriod(),e.getEmpID(),e.getTempAbsenceFrameNo()));
        });
        return result;
    }
}
