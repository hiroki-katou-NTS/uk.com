package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.care;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;

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
	public static AtomTask deleteTempCareManagement(Require require, String employeeId, DatePeriod period){
		return AtomTask.of(() -> require.deleteTempCareByPeriod(employeeId, period));
	}

	
	public static interface Require{
		void deleteTempCareByPeriod(String sid, DatePeriod period);
	}
}
