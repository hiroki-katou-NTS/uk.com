package nts.uk.ctx.at.record.pub.monthly.vacation.annualleave;

import java.util.List;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualLeaveUsedNumberFromRemDataService.RequireM3;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

/**
 * 付与残数データから使用数を消化する
 */
public interface GetAnnualLeaveUsedNumberFromRemDataPub {


	/**
	 * 付与残数データから使用数を消化する
	 * @param employeeId 社員ID
	 * @param remainingData 年休付与残数データ
	 * @param usedNumber 使用数
	 * @return 年休付与残数データ(List)
	 */
	// RequestList678
	List<LeaveGrantRemainingData> getAnnualLeaveGrantRemData(
			String companyId,
			String employeeId,
			List<LeaveGrantRemainingData> remainingData,
			LeaveUsedNumber usedNumber,
			RequireM3 require);
}
