package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 振休暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempSubstitution {
	
	/**
	 * 振休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempSubstitutionManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteInterimAbsMngBySidDatePeriod(employeeId, period))
				.then(AtomTask.of(() -> require.deleteInterimRecMngBySidDatePeriod(employeeId, period)));
	}
	
	public static interface Require{
		void deleteInterimAbsMngBySidDatePeriod(String sId, DatePeriod period);
		void deleteInterimRecMngBySidDatePeriod(String sId, DatePeriod period);
	}
}
