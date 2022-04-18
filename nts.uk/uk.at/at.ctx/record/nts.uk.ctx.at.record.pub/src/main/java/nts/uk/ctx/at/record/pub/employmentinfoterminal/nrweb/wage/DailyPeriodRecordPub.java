package nts.uk.ctx.at.record.pub.employmentinfoterminal.nrweb.wage;

import java.util.Optional;

import nts.arc.time.calendar.period.DatePeriod;

/**
* @author sakuratani
* 
* 			日別実績データが存在する期間を取得するPub
*
*/
public interface DailyPeriodRecordPub {

	//RequestList700 日別実績データが存在する期間を取得する
	public Optional<DatePeriod> get(String employeeId, DatePeriod period);
}
