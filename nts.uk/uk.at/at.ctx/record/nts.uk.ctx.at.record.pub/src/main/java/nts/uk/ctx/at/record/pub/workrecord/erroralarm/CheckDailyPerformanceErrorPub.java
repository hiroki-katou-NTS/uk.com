package nts.uk.ctx.at.record.pub.workrecord.erroralarm;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface CheckDailyPerformanceErrorPub {

	/**
	 * 指定期間内に社員のエラーがあるかチェックする
	 * @param employeeID
	 * @param strDate
	 * @param endDate
	 * @return
	 */
	List<DailyPerformanceErrorExport> check(String employeeID, GeneralDate strDate, GeneralDate endDate);
	
	/**
	 * Add by ThanhPV
	 * @param employeeID
	 * @param strDate
	 * @param endDate
	 * @return
	 */
	boolean checksDailyPerformanceError(String employeeID, GeneralDate strDate, GeneralDate endDate);
}
