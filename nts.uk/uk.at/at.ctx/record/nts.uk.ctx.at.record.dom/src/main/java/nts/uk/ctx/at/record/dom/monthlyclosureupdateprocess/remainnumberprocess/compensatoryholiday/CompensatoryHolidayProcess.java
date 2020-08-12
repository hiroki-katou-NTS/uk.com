package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.calculateremainnum.RemainCompensatoryHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.deletetempdata.CompensatoryTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.compensatoryholiday.updateremainnum.RemainCompensatoryHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;

/**
 * 
 * @author HungTT - <<Work>> 代休処理
 *
 */
public class CompensatoryHolidayProcess {
	
	/**
	 * 代休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public static AtomTask compensatoryHolidayProcess(RequireM1 require, CacheCarrier cacheCarrier, 
			AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		// 代休計算処理
		BreakDayOffRemainMngOfInPeriod output = RemainCompensatoryHolidayCalculation.calculateRemainCompensatory(
				require, cacheCarrier, period, empId, interimRemainMngMap);
		
		return RemainCompensatoryHolidayUpdating.updateRemainCompensatoryHoliday(require, output.getLstDetailData(), period, empId)	// 代休残数更新
				.then(CompensatoryTempDataDeleting.deleteTempDataProcess(require, period, empId)); // 代休暫定データ削除
	}
	
	public static interface RequireM1 extends RemainCompensatoryHolidayCalculation.RequireM1,
		RemainCompensatoryHolidayUpdating.RequireM5, CompensatoryTempDataDeleting.RequireM3 {
		
	}
}
