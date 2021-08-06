package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.publicHoliday;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 公休暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempPublicHoliday {

	/**
	 * 公休暫定データ削除
	 * @param require
	 * @param employeeId 社員ID
	 * @param period　期間
	 * @return
	 */
	public static AtomTask deleteTempPublicHolidayManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteTempPublicHolidayByPeriod(employeeId, period));
	}
	
	public static interface Require{
		void deleteTempPublicHolidayByPeriod(String employeeId, DatePeriod period);
		
	}
}
