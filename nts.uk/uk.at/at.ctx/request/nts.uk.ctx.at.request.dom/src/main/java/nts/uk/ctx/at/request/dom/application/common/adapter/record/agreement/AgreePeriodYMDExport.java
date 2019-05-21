package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Getter
public class AgreePeriodYMDExport {
	
	//期間
	DatePeriod dateperiod;
	
	//年月
	YearMonth dateTime;
}
