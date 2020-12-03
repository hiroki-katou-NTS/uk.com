package nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface ExBudgetDailyAdapter {

    List<ExBudgetDailyImport> getByLstTargetOrgAndPeriod(List<TargetOrgIdenInforImport> targetOrgs, DatePeriod datePeriod);

}
