package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface ExtBudgetDailyPub {

    List<ExtBudgetDailyExport> getAllExtBudgetDailyByPeriod(TargetOrgIdenInforExport targetOrg, DatePeriod datePeriod);

    List<ExtBudgetDailyExport> getByLstTargetOrgAndPeriod (List<TargetOrgIdenInforExport> lstTargetOrg , DatePeriod datePeriod );
}
