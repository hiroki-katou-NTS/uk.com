package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

@AllArgsConstructor
@Getter
public class AgreePeriodYMDExport {
	
	//ๆ้
	DatePeriod dateperiod;
	
	//ๅนดๆ
	YearMonth dateTime;
}
