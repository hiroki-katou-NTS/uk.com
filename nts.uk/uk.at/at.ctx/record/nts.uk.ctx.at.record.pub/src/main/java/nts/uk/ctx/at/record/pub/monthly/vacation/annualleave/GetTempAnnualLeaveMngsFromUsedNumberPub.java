package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave;

import java.util.List;

import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.annual.LeaveUsedNumberExport;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.dtoexport.temp.TempAnnualLeaveMngsExport;

/**
 * 使用数を暫定年休管理データに変換する
 */
public interface GetTempAnnualLeaveMngsFromUsedNumberPub {
	/**
	 * 使用数を暫定年休管理データに変換する
	 * 
	 * @param employeeId 社員ID
	 * @param usedNumber 使用数
	 * @return 暫定年休管理データ(List)
	 */
	// RequestList678
	List<TempAnnualLeaveMngsExport> getTempAnnualLeaveData(String employeeId, LeaveUsedNumberExport usedNumber);
}
