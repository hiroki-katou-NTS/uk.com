package nts.uk.ctx.at.record.dom.adapter.eligibleemployees;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.LeavePeriod;

import java.util.List;

public interface LeavePeriodAdapter {
    public List<LeavePeriod> GetLeavePeriod(List<String> sids, DatePeriod datePeriod);
}
