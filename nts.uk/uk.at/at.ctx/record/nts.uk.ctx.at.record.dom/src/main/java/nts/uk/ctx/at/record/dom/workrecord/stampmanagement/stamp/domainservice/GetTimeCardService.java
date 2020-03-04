package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;


/**
 * DS : タイムカードを取得する
 * @author tutk
 *
 */
@Stateless
public class GetTimeCardService {

	public static TimeCard getTimeCard(Require required, String employeeId, YearMonth yearMonth ) {

		
		
		return null;
	}
	
	//	[prv-1] 実績を取得する期間を求める
	private DatePeriod calculatePeriod(YearMonth yearMonth) {
		return new DatePeriod(GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 1) , yearMonth.lastGeneralDate());
	}
	
	//	[prv-2] 日々の実績を作成する
	
	//	[prv-3] 1日の出退勤を作成する
	
	
	public static interface Require {

	}
}
