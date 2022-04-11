package nts.uk.ctx.bs.company.pub.company;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
* 
* 			指定した締め期間の年期間を算出するPub
*
*/
public interface YearAndPeriodPub {

	//RequestList704 指定した締め期間の年期間を算出する
	public YearAndPeriodExport get(String cid, DatePeriod period);
}
