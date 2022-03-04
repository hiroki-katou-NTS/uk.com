package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         月間賃金
 */
@Getter
@AllArgsConstructor
public class NRWebMonthWage {

	//期間
	private DatePeriod period;

	//目安
	private ItemValue measure;

	//現在勤務
	private ItemValue currentWork;

	//現在勤務残業
	private ItemValue currentOvertime;

	//計画勤務
	private ItemValue scheduleWork;

	//計画勤務残業
	private ItemValue scheduleOvertime;

}
