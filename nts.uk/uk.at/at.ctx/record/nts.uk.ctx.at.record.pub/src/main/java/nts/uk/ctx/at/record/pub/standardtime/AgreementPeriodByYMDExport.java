package nts.uk.ctx.at.record.pub.standardtime;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
public class AgreementPeriodByYMDExport {

	//期間
	DatePeriod dateperiod;
	
	//年月
	YearMonth dateTime;

	/**
	 * Constructor 
	 */
	public AgreementPeriodByYMDExport(DatePeriod dateperiod, YearMonth dateTime) {
		super();
		this.dateperiod = dateperiod;
		this.dateTime = dateTime;
	}
}
