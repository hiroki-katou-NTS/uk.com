package nts.uk.ctx.at.record.dom.adapter.workschedule.budgetcontrol.budgetperformance;

import nts.arc.time.calendar.period.DatePeriod;

import java.util.List;

public interface ExBudgetDailyAdapter {

    public List<ExBudgetDailyImport> getAllExtBudgetDailyByPeriod(int unit, String workplaceId, String workplaceGroupId, DatePeriod datePeriod);

}
