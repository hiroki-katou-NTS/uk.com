package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday;

import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.calculateremainnum.RemainSubstitutionHolidayCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.deletetempdata.SubstitutionTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.substitutionholiday.updateremainnum.RemainSubstitutionHolidayUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;

/**
 * 
 * @author HungTT - <<Work>> 振休処理
 *
 */
public class SubstitutionHolidayProcess {
	 
	/**
	 * 振休処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 */
	public static AtomTask substitutionHolidayProcess(RequireM1 require, CacheCarrier cacheCarrier, 
			AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap) {
		
		// 振休残数計算
		AbsRecRemainMngOfInPeriod output = RemainSubstitutionHolidayCalculation.calculateRemainHoliday(
				require, cacheCarrier, period, empId, interimRemainMngMap);
		
		return AtomTask.of(() -> {})
				.then(RemainSubstitutionHolidayUpdating.updateRemainSubstitutionHoliday(require, output.getLstAbsRecMng(),period, empId))// 振休残数更新
				.then(SubstitutionTempDataDeleting.deleteTempSubstitutionData(require, period, empId));		// 振休暫定データ削除
	}
	
	public static interface RequireM1 extends RemainSubstitutionHolidayCalculation.RequireM1,
		SubstitutionTempDataDeleting.RequireM3, RemainSubstitutionHolidayUpdating.RequireM5 {
		
	}
}
