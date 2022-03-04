package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         予定期間と実績期間
 */
@Getter
@AllArgsConstructor
public class SchedulePeriodAndRecordPeriod {

	//予定期間
	private Optional<DatePeriod> periodSchedule;

	//実績期間
	private Optional<DatePeriod> periodRecord;

}
