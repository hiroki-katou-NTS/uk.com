package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.publicHoliday;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

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
	public static AtomTask delete(Require require, String employeeId, GeneralDate ymd){
		return AtomTask.of(() -> require.deleteTempPublicHolidayBySidBeforeTheYmd(employeeId, ymd));
	}
	
	public static interface Require{
		void deleteTempPublicHolidayBySidBeforeTheYmd(String sid, GeneralDate ymd);
		
	}
}
