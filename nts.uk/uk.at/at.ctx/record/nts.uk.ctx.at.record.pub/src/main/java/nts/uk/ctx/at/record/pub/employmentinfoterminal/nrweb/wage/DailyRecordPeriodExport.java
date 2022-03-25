package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			日別実績期間Export
*         
*/
@Getter
@AllArgsConstructor
public class DailyRecordPeriodExport {

	//日別実績期間
	private Optional<DatePeriod> periodRecordDaily;

}
