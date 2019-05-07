package nts.uk.ctx.hr.develop.dom.careermgmt.careerpath;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**年月日期間の汎用履歴項目*/
@AllArgsConstructor
@Getter
public class DateHistoryItem {

	private DatePeriod period;
	
	private String historyId;

}
