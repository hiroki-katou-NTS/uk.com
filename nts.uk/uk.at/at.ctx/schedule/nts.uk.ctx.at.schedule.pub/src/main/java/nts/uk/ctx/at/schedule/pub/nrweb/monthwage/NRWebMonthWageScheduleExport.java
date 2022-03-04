package nts.uk.ctx.at.schedule.pub.nrweb.monthwage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         月間年間賃金予定Export
 */
@Getter
@AllArgsConstructor
public class NRWebMonthWageScheduleExport {

	//期間
	private DatePeriod period;

	//目安(未来)
	private ItemValueExport measure;

	//計画勤務(未来)
	private ItemValueExport scheduleWork;

	//計画勤務残業(未来)
	private ItemValueExport scheduleOvertime;

}
