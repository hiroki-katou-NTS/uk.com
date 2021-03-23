package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.calculateremainnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.NumberRemainVacationLeaveRangeQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.BreakDayOffRemainMngRefactParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.FixedManagementDataMonth;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.param.SubstituteHolidayAggrResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.InterimRemain;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - <<Work>> 代休残数計算
 *
 */
public class RemainCompensatoryHolidayCalculation {

	/**
	 * 代休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @return 代休の集計結果
	 */
	public static SubstituteHolidayAggrResult calculateRemainCompensatory(RequireM1 require, 
			CacheCarrier cacheCarrier, AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		String companyId = AppContexts.user().companyId();

		// 暫定残数データを休出・代休に絞り込む
		List<InterimRemain> interimMng = new ArrayList<>();
		List<InterimBreakMng> breakMng = new ArrayList<>();
		List<InterimDayOffMng> dayOffMng = new ArrayList<>();
		for (val interimRemainMng : interimRemainMngMap.values()){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			interimMng.addAll(interimRemainMng.getRecAbsData());

			// 休出
			if (interimRemainMng.getBreakData().isPresent()){
				breakMng.add(interimRemainMng.getBreakData().get());
			}
			
			// 代休
			if (interimRemainMng.getDayOffData().isPresent()){
				dayOffMng.add(interimRemainMng.getDayOffData().get());
			}
		}
		
		BreakDayOffRemainMngRefactParam inputParam = new BreakDayOffRemainMngRefactParam(
				companyId, empId, period.getPeriod(), true,
				period.getPeriod().end(), true,
				interimMng, 
				Optional.empty(), 
				Optional.empty(), 
				breakMng, 
				dayOffMng,
				Optional.empty(), new FixedManagementDataMonth());
		// 期間内の休出代休残数を取得する
		return NumberRemainVacationLeaveRangeQuery.getBreakDayOffMngInPeriod(require, inputParam);
	}
	
	public static interface RequireM1 extends NumberRemainVacationLeaveRangeQuery.Require {
		
	}
}
