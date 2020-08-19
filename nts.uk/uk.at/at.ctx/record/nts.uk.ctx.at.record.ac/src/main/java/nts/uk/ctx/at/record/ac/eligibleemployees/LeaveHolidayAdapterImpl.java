package nts.uk.ctx.at.record.ac.eligibleemployees;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.eligibleemployees.LeaveHolidayAdapter;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeaveHolidayPeriod;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import java.util.List;
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class LeaveHolidayAdapterImpl implements LeaveHolidayAdapter {
    @Override
    public List<LeaveHolidayPeriod> GetLeaveHolidayPeriod(List<String> sids, DatePeriod datePeriod) {
        return null;
    }
}
