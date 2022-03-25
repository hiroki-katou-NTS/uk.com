package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			日別実績データが存在する期間を取得するAdapter
*
*/
public interface DailyRecordPeriodAdapter {

	public Optional<DatePeriod> get(String employeeId, DatePeriod period);
}
