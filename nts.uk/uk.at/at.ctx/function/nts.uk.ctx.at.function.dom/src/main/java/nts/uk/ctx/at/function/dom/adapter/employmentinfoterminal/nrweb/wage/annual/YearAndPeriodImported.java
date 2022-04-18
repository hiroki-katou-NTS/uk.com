package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			年期間Imported
*         
*/
@Getter
@AllArgsConstructor
public class YearAndPeriodImported {

	// 年度
	private Year year;

	// 期間
	private DatePeriod period;

}
