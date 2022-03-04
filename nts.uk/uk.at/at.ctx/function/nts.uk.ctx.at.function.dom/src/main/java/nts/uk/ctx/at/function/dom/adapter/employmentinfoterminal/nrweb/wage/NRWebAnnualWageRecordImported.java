package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * @author sakuratani
 *
 *         月間年間賃金予定Imported
 */
@Getter
@AllArgsConstructor
public class NRWebAnnualWageRecordImported {

	//期間
	private DatePeriod period;

	//目安
	private ItemValueImported measure;

	//現在勤務
	private ItemValueImported currentWork;

	//現在勤務残業
	private ItemValueImported currentOvertime;

}
