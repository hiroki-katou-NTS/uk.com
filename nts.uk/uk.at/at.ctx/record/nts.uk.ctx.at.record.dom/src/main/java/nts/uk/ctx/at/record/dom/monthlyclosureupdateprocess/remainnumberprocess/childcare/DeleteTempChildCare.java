package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.childcare;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;

/**
 * 子の看護休暇暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempChildCare {
	
	/**
	 * 子の看護休暇暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask delete(Require require, String employeeId, GeneralDate ymd){
		return AtomTask.of(() -> require.deleteTempChildCareBySidBeforeTheYmd(employeeId, ymd));
	}

	
	public static interface Require{
		void deleteTempChildCareBySidBeforeTheYmd(String sid, GeneralDate ymd);
	}
}
