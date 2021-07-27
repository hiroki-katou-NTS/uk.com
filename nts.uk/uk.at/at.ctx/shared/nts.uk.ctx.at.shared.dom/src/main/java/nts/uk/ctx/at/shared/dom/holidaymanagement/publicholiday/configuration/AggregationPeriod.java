package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;



import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 集計期間
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class AggregationPeriod {

	/*
	 * 年月
	 */
	private YearMonth yearMonth; 
	
	/*
	 * 期間
	 */
	private DatePeriod period;
	
	
}
