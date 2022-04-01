package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingNumber;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;

/**
 * 年休集計期間WORKリスト
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class AggregatePeriodWorkList {

	private List<AggregatePeriodWork> periodWorkList;
	
	/**
	 * 残数処理
	 * @param require
	 * @param companyId
	 * @param employeeId
	 * @param tempAnnualLeaveMngs
	 * @param annualPaidLeaveSet
	 * @param annualLeaveInfo
	 * @return
	 */
	public AggrResultOfAnnualLeave remainNumberProcess(LeaveRemainingNumber.RequireM3 require, String companyId, String employeeId,
			List<TempAnnualLeaveMngs> tempAnnualLeaveMngs, AnnualPaidLeaveSetting annualPaidLeaveSet,
			AnnualLeaveInfo annualLeaveInfo) {

		AggrResultOfAnnualLeave aggrResult = new AggrResultOfAnnualLeave();

		for (val aggregatePeriodWork : this.periodWorkList) {
			// 残数処理
			aggrResult = annualLeaveInfo.remainNumberProcess(require, companyId, employeeId,
					isNextGrantPeriodAtr(aggregatePeriodWork), aggregatePeriodWork, tempAnnualLeaveMngs, aggrResult,
					annualPaidLeaveSet);
		}

		return aggrResult;
	}
	
	
	
	
	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @return
	 */
	private GrantBeforeAfterAtr isNextGrantPeriodAtr(AggregatePeriodWork periodWork){
		if(periodWork.getEndWork().isNextPeriodEndAtr()){
			return periodWork.getGrantWork().judgeGrantPeriodAtr();
		}
		
		return  periodWorkList.get(periodWorkList.indexOf(periodWork)+1).getGrantWork().judgeGrantPeriodAtr();
	}
}
