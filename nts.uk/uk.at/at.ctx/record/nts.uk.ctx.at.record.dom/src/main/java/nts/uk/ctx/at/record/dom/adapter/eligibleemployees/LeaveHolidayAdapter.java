package nts.uk.ctx.at.record.dom.adapter.eligibleemployees;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeaveHolidayPeriod;

import java.util.List;

public interface LeaveHolidayAdapter {
    public List<LeaveHolidayPeriod> GetLeaveHolidayPeriod(List<String> sids, DatePeriod datePeriod);
}
