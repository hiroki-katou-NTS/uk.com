package nts.uk.ctx.at.record.dom.adapter.eligibleemployees;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.employmentrules.organizationmanagement.WorkPlaceHist;

import java.util.List;

public interface WorkPlaceHistAdapter {
    List<WorkPlaceHist> GetWorkHistory(List<String> sids, DatePeriod datePeriod);
}
