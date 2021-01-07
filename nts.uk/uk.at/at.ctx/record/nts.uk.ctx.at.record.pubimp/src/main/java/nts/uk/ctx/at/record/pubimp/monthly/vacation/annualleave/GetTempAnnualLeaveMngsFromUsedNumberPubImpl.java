package nts.uk.ctx.at.record.pubimp.monthly.vacation.annualleave;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetTempAnnualLeaveMngsFromUsedNumberService;
import nts.uk.ctx.at.record.pub.monthly.vacation.annualleave.GetTempAnnualLeaveMngsFromUsedNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveUsedNumber;

@Stateless
public class GetTempAnnualLeaveMngsFromUsedNumberImpl implements GetTempAnnualLeaveMngsFromUsedNumber{

	/**
	 * 使用数を暫定年休管理データに変換する
	 * @param employeeId 社員ID
	 * @param usedNumber 使用数
	 * @return 暫定年休管理データ(List)
	 */
	@Override
	public List<TempAnnualLeaveMngs> getTempAnnualLeaveData(
			String employeeId,
			LeaveUsedNumber usedNumber) {

		List<TempAnnualLeaveMngs> result = GetTempAnnualLeaveMngsFromUsedNumberService.tempAnnualLeaveMngs(employeeId, usedNumber);

		return result;
	}
}
