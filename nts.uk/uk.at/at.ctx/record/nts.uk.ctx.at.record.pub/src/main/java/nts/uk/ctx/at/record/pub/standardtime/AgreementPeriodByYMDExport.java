package nts.uk.ctx.at.record.pub.standardtime;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

@Getter
public class AgreementPeriodByYMDExport {

	//ๆ้
	DatePeriod dateperiod;
	
	//ๅนดๆ
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
