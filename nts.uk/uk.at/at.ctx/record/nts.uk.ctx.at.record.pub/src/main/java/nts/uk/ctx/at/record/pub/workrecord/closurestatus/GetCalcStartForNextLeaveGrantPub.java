package nts.uk.ctx.at.record.pub.workrecord.closurestatus;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 次回年休付与を計算する開始日を取得する
 * @author shuichi_ishida
 */
public interface GetCalcStartForNextLeaveGrantPub {

	/**
	 * 次回年休付与を計算する開始日を取得する
	 * @param employeeId 社員ID
	 * @return 年月日
	 */
	GeneralDate algorithm(String employeeId);
	/**
	 * 「締め状態管理」.期間
	 * @param sid　社員ID
	 * @return
	 */
	Optional<DatePeriod> closureDatePeriod(String sid);
}
