package nts.uk.ctx.at.record.pub.remainnumber.annualleave;

import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
/*
 * 年月毎締め処理期間
 */
@Getter
public class ClosurePeriodEachYear {
	private YearMonth yearMonth;
	private DatePeriod datePeriod;
	
	public ClosurePeriodEachYear(YearMonth yearMonth, DatePeriod datePeriod) {
		super();
		this.yearMonth = yearMonth;
		this.datePeriod = datePeriod;
	}
}
