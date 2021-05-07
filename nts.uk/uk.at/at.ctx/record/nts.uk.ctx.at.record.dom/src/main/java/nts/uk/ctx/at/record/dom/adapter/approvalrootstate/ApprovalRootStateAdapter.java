package nts.uk.ctx.at.record.dom.adapter.approvalrootstate;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface ApprovalRootStateAdapter {
    List<ApprovalRootStateImport> findByEmployeesAndPeriod(List<String> empIds, DatePeriod period, int rootType);
}
