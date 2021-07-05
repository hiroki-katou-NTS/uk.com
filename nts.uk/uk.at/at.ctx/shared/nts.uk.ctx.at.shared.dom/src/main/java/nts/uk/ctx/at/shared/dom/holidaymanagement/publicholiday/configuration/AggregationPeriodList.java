package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;



import lombok.Getter;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 集計期間リスト
 * @author hayata_maekawa
 *
 */
@Getter
public class AggregationPeriodList {

	/*
	 * 年月
	 */
	private YearMonth yearMonth; 
	
	/*
	 * 期間
	 */
	private DatePeriod period;
	
	
	
	public AggregationPeriodList(YearMonth yearMonth, DatePeriod period){
		this.yearMonth = yearMonth;
		this.period = period;
	}
}
