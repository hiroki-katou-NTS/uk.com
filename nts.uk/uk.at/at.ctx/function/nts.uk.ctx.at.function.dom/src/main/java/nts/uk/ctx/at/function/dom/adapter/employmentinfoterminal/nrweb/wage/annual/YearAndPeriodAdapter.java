package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.nrweb.wage.annual;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
*
*         	指定した締め期間の年期間を算出するAdapter
*         
*/
public interface YearAndPeriodAdapter {

	public YearAndPeriodImported get(String cid, DatePeriod datePeriod);

}
