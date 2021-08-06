package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 年休暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempAnnualLeave {
	
	/**
	 * 年休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempAnnualLeaveManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteTempAnnualSidPeriod(employeeId, period));
	}
	
	public static interface Require{
		void deleteTempAnnualSidPeriod(String sid, DatePeriod period);
		
	}
}
