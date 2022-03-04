package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         日別実績期間Imported
 */
@Getter
@AllArgsConstructor
public class DailyRecordPeriodImported {

	//日別実績期間
	private Optional<DatePeriod> periodRecordDaily;

}
