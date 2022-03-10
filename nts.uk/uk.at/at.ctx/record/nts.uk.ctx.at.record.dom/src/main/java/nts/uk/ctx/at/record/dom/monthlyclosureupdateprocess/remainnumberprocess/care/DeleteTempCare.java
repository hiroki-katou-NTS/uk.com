package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.care;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * 介護休暇暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempCare {
	
	
	/**
	 * 暫定介護管理データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask delete(Require require, String employeeId, GeneralDate ymd){
		return AtomTask.of(() -> require.deleteTempCareBySidBeforeTheYmd(employeeId, ymd));
	}

	
	public static interface Require{
		void deleteTempCareBySidBeforeTheYmd(String sid, GeneralDate ymd);
	}
}
