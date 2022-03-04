package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         日別実績期間Adapter
 */
public interface DailyRecordPeriodAdapter {
	public DatePeriod getPeriodDuringDailyDataExists(String employeeId, DatePeriod period);
}
