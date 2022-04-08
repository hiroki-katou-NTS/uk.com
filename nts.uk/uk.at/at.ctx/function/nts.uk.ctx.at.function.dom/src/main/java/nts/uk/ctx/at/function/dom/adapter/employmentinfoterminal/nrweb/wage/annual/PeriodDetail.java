package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			期間詳細
*         
*/
@Getter
@AllArgsConstructor
public class PeriodDetail {

	// 月間実績期間
	private Optional<DatePeriod> monthPeriod;

	// 日別実績期間と予定期間
	private Optional<DatePeriod> dailyRecordSchedulePeriod;

}
