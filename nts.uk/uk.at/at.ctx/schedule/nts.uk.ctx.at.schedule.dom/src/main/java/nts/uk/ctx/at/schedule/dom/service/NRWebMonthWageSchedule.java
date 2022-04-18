package nts.uk.ctx.at.schedule.dom.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         月間年間賃金予定
 */
@Getter
@AllArgsConstructor
public class NRWebMonthWageSchedule {

	// 期間
	private DatePeriod period;

	// 目安
	private ItemValue measure;

	// 計画勤務
	private ItemValue scheduleWork;

	// 計画勤務残業
	private ItemValue scheduleOvertime;
}
