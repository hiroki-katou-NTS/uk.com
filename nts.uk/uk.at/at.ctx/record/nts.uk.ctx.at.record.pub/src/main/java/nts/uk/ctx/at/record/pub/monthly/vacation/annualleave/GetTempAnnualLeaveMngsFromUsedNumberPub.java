package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave;

import java.util.List;

import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 使用数を暫定年休管理データに変換する
 */
public interface GetTempAnnualLeaveMngsFromUsedNumberPub {
	/**
	 * 使用数を暫定年休管理データに変換する
	 * @param employeeId 社員ID
	 * @param usedNumber 使用数
	 * @return 暫定年休管理データ(List)
	 */
	// RequestList678
	List<TempAnnualLeaveMngs> getTempAnnualLeaveData(
			String employeeId,
			LeaveUsedNumber usedNumber);
}
