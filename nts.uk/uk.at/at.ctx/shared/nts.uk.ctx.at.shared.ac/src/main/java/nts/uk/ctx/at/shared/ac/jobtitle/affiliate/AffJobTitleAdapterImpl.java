package nts.uk.ctx.at.shared.ac.jobtitle.affiliate;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.affiliate.*;
import nts.uk.ctx.bs.employee.pub.jobtitle.SyJobTitlePub;
import nts.uk.ctx.bs.employee.pub.jobtitle.affiliate.JobTitleHistoryExport;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
public class AffJobTitleAdapterImpl implements AffJobTitleAdapter {

    @Inject
    private SyJobTitlePub syJobTitlePub;

    @Override
    public List<JobTitleInfoImport> findByDatePeriod(String companyId, DatePeriod datePeriod) {
        return syJobTitlePub.findByDatePeriod(companyId, datePeriod).stream().map(x -> new JobTitleInfoImport(
                x.getCompanyId(),
                x.getJobTitleHistoryId(),
                x.isManager(),
                x.getJobTitleId(),
                x.getJobTitleCode(),
                x.getJobTitleName(),
                x.getSequenceCode()))
                .collect(Collectors.toList());
    }

    @Override
    public JobTitleHistoryImport getJobTitleHist(List<String> employeeIds, DatePeriod period) {
        JobTitleHistoryExport data = syJobTitlePub.getJobTitleHist(employeeIds, period);
        return new JobTitleHistoryImport(
                data.getHistories().stream().map(x -> new AffJobTitleHistoryImport(
                        x.getCompanyId(), x.getEmployeeId(), x.getHistoryItems().stream().map(c ->
                        new DateHistoryItemImport(
                                c.getHistoryId(),
                                c.getPeriod()))
                        .collect(Collectors.toList())
                )).collect(Collectors.toList()),
                data.getHistoryItems().stream().map(x -> new AffJobTitleHistoryItemImport(
                        x.getHistoryId(),
                        x.getEmployeeId(),
                        x.getJobTitleId(),
                        x.getNote()
                )).collect(Collectors.toList()));
    }
}
