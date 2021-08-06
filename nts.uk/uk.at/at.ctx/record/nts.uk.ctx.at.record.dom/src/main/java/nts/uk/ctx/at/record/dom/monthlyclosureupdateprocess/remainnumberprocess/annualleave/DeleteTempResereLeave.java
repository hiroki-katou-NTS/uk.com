package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

/**
 * 積立年休暫定データ削除
 * @author hayata_maekawa
 *
 */
public class DeleteTempResereLeave {

	/**
	 * 積立年休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempResereLeaveManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteTempResereSidPeriod(employeeId, period));
	}
	
	public static interface Require{
		void deleteTempResereSidPeriod(String sid, DatePeriod period);		
	}
}
