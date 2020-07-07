package nts.uk.ctx.bs.employee.dom.employment.history;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.shr.com.history.DateHistoryItem;
@Getter
@RequiredArgsConstructor
public class EmploymentHistoryTerm {
		/** 期間 **/ 
		private final DatePeriod  datePeriod;
		/** 履歴項目 **/
		private final DateHistoryItem dateHistoryItem;
		
}
