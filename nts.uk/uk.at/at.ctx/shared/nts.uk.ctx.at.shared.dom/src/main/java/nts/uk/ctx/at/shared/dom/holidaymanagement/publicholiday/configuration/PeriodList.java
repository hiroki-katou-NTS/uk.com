package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;



import lombok.Getter;
import lombok.Setter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 期間(List)
 * @author hayata_maekawa
 *
 */
@Getter
@Setter
public class PeriodList {

	/*
	 * 年月
	 */
	private YearMonth yearMonth; 
	
	/*
	 * 期間
	 */
	private DatePeriod period;
	
	
	
	public PeriodList(YearMonth yearMonth, DatePeriod period){
		this.yearMonth = yearMonth;
		this.period = period;
	}
}
