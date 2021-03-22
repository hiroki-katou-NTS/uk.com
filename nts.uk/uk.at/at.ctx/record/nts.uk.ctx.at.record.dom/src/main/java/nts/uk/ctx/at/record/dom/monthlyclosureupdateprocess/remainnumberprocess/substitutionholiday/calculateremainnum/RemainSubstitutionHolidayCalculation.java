package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.calculateremainnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.NumberCompensatoryLeavePeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.AbsRecMngInPeriodRefactParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.algorithm.param.CompenLeaveAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 振休残数計算
 *
 */
public class RemainSubstitutionHolidayCalculation {

	/**
	 * 振休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @return 振休の集計結果
	 */
	public static CompenLeaveAggrResult calculateRemainHoliday(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		String companyId = AppContexts.user().companyId();
		
		// 暫定残数データを振休・振出に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimAbsMng> useAbsMng = new ArrayList<>();
		List<InterimRecMng> useRecMng = new ArrayList<>();
		for (val interimRemainMng : interimRemainMngMap.values()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			interimMng.addAll(interimRemainMng.getRecAbsData());

			// 振休
			if (interimRemainMng.getInterimAbsData().isPresent()){
				useAbsMng.add(interimRemainMng.getInterimAbsData().get());
			}
			
			// 振出
			if (interimRemainMng.getRecData().isPresent()){
				useRecMng.add(interimRemainMng.getRecData().get());
			}
		}
		
		// 「期間内の振出振休残数を取得する」を実行する
		val mngParam = new AbsRecMngInPeriodRefactParamInput(companyId, empId, period.getPeriod(),
				period.getPeriod().end(), true, true, useAbsMng, interimMng, useRecMng, Optional.empty(), Optional.empty(), Optional.empty(), 
				new FixedManagementDataMonth());
		return NumberCompensatoryLeavePeriodQuery.process(require, mngParam);
	}
	
	public static interface RequireM1 extends NumberCompensatoryLeavePeriodQuery.Require {
		
	}
}
