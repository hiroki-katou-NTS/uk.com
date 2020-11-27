package nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface AffJobTitleAdapter {
    List<JobTitleInfoImport> findByDatePeriod(String companyId, DatePeriod datePeriod);
    JobTitleHistoryImport getJobTitleHist(List<String> employeeIds, DatePeriod period);
}
