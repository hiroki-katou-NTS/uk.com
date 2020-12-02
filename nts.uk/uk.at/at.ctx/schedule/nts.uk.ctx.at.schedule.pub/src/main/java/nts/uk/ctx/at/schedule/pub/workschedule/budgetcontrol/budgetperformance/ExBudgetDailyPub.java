package nts.uk.ctx.at.schedule.pub.workschedule.budgetcontrol.budgetperformance;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface ExBudgetDailyPub {

    public List<ExBudgetDailyExport> getAllExtBudgetDailyByPeriod(int unit, String workplaceId, String workplaceGroupId, DatePeriod datePeriod);
}
