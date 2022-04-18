package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*			月間年間賃金実績Export
*         
*/
@Getter
@AllArgsConstructor
public class NRWebMonthWageRecordExport {

	//期間
	private DatePeriod period;

	//目安
	private ItemValueExport measure;

	//現在勤務
	private ItemValueExport currentWork;

	//現在勤務残業
	private ItemValueExport currentOvertime;

}
