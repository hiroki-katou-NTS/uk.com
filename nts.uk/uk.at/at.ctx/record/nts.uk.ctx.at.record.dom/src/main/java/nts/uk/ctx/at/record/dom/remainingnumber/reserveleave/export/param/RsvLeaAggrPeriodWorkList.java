package nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.ReserveLeaveInfo.RequireM1;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.GrantBeforeAfterAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 積立年休集計期間WORKリスト
 * @author hayata_maekawa
 *
 */
@Getter
@AllArgsConstructor
public class RsvLeaAggrPeriodWorkList {

	private List<RsvLeaAggrPeriodWork> periodWorkList;
	
	
	/**
	 * 残数処理
	 * @param require
	 * @param cacheCarrier
	 * @param companyId
	 * @param employeeId
	 * @param tmpReserveLeaveMngs
	 * @param annualPaidLeaveSet
	 * @param limit
	 * @param reserveLeaveInfo
	 * @return
	 */
	public AggrResultOfReserveLeave remainNumberProcess(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId,	List<TmpResereLeaveMng> tmpReserveLeaveMngs, AnnualPaidLeaveSetting annualPaidLeaveSet, UpperLimitSetting limit,
			ReserveLeaveInfo reserveLeaveInfo) {
		
		AggrResultOfReserveLeave aggrResult = new AggrResultOfReserveLeave();
		
		for (val aggrPeriodWork : this.periodWorkList) {
			//残数処理
			aggrResult = reserveLeaveInfo.remainNumberProcess(require, cacheCarrier, companyId, employeeId,
				 aggrPeriodWork, isNextGrantPeriodAtr(aggrPeriodWork), tmpReserveLeaveMngs, aggrResult, annualPaidLeaveSet, limit);
			
		}
		
		return aggrResult;
	}
	
	
	
	/**
	 * 次の期間の付与前後を判断
	 * @param periodWorkList
	 * @return
	 */
	private GrantBeforeAfterAtr isNextGrantPeriodAtr(RsvLeaAggrPeriodWork periodWork){
		if(periodWork.getEndWork().isNextPeriodEndAtr()){
			return periodWork.getGrantWork().judgeGrantPeriodAtr();
		}
		
		return  periodWorkList.get(periodWorkList.indexOf(periodWork)+1).getGrantWork().judgeGrantPeriodAtr();
	}
}
