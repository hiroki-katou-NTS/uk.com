package nts.uk.ctx.bs.employee.dom.jobtitle.affiliate;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.calendar.period.DatePeriod;

/*
 *期間付き職位履歴項目 
 */
@Data
@AllArgsConstructor
public class AffJobTitleHistoryItemWithPeriod {
	
	//期間
	private DatePeriod datePeriod;
	
	//履歴項目
	private AffJobTitleHistoryItem jobTitleHistoryItem;

}
