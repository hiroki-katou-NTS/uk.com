package nts.uk.ctx.at.record.dom.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         月間年間賃金実績
 */
@Getter
@AllArgsConstructor
public class NRWebMonthWageRecord {

	// 期間
	private DatePeriod period;

	// 目安
	private ItemValue measure;

	// 現在勤務
	private ItemValue currentWork;

	// 現在勤務残業
	private ItemValue currentOvertime;
}
