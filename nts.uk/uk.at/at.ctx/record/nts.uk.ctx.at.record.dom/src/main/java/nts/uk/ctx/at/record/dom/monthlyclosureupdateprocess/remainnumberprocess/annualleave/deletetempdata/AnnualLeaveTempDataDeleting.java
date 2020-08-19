package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempdata;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 
 * @author HungTT - <<Work>> 年休暫定データ削除
 *
 */
public class AnnualLeaveTempDataDeleting {

	/**
	 * 年休暫定データ削除
	 * 
	 * @param employeeId
	 * @param period
	 */
	public static AtomTask deleteTempAnnualLeaveData(RequireM1 require, String employeeId, DatePeriod period) {
		return AtomTask.of(() -> require.removeInterimRemain(employeeId, period));
	}
	
	public static interface RequireM1 {
		
		void removeInterimRemain(String sId, DatePeriod period);
	}
}
