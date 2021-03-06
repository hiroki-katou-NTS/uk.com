package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			月間年間賃金予定Imported
*         
*/
@Getter
@AllArgsConstructor
public class NRWebMonthWageScheduleImported {

	//期間
	private DatePeriod period;

	//目安(未来)
	private ItemValueImported measure;

	//計画勤務(未来)
	private ItemValueImported scheduleWork;

	//計画勤務残業(未来)
	private ItemValueImported scheduleOvertime;

}
