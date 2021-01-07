package nts.uk.ctx.at.record.pubimp.monthly.vacation.annualleave;
/**
 * 実装：付与残数データから使用数を消化する
 * @author yuri_tamakoshi
 */

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualLeaveUsedNumberFromRemDataService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnualLeaveUsedNumberFromRemDataService.RequireM3;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetAnnualLeaveUsedNumberFromRemData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.LeaveGrantRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

@Stateless
public class GetAnnualLeaveUsedNumberFromRemDataImpl implements GetAnnualLeaveUsedNumberFromRemData{

	/**
	 * 付与残数データから使用数を消化する
		 * @param companyId 会社ID
		 * @param employeeId 社員ID
		 * @param remainingData 年休付与残数データ
		 * @param usedNumber 使用数
		 * @return 年休付与残数データ(List)
	 */
	@Override
	public List<LeaveGrantRemainingData> getAnnualLeaveGrantRemData(
			String companyId,
			String employeeId,
			List<LeaveGrantRemainingData> remainingData,
			LeaveUsedNumber usedNumber,
			RequireM3 require) {

		List<LeaveGrantRemainingData> result =
				GetAnnualLeaveUsedNumberFromRemDataService.getAnnualLeaveGrantRemainingData(companyId, employeeId, remainingData, usedNumber, require);

		return result;
	}
}
