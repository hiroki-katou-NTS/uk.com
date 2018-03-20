package nts.uk.ctx.at.record.dom.standardtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 集計期間
 * @author shuichu_ishida
 */
@Getter
@Setter
@AllArgsConstructor
public class AggregatePeriod {

	/** 期間 */
	private DatePeriod period;
	/** 月度 */
	private YearMonth yearMonth;
	/** 年度 */
	private Year year;
	
	/**
	 * コンストラクタ
	 */
	public AggregatePeriod(){
		this.period = new DatePeriod(GeneralDate.today(), GeneralDate.today());
		this.yearMonth = YearMonth.of(GeneralDate.today().year(), GeneralDate.today().month());
		this.year = new Year(GeneralDate.today().year());
	}
}
