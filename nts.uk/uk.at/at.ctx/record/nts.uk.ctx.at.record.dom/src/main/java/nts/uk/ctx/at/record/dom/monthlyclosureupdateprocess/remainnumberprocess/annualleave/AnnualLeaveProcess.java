package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.calculateremainnum.RemainAnnualLeaveCalculation;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempdata.AnnualLeaveTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.deletetempreserveannual.RsvAnnualLeaveTempDataDeleting;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updateremainnum.RemainAnnualLeaveUpdating;
import nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave.updatereserveannual.RemainReserveAnnualLeaveUpdating;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;

/**
 * 
 * @author HungTT - <<Work>> 年休処理
 *
 */
public class AnnualLeaveProcess {
	/**
	 * 年休残数処理
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @param attTimeMonthly 月別実績の勤怠時間
	 */
	public static AtomTask annualHolidayProcess(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			Map<GeneralDate, DailyInterimRemainMngData> interimRemainMngMap, AttendanceTimeOfMonthly attTimeMonthly) {
		
		List<AtomTask> atomTask = new ArrayList<>();
		
		// 年休残数計算
		AggrResultOfAnnAndRsvLeave output = RemainAnnualLeaveCalculation.calculateRemainAnnualHoliday(
				require, cacheCarrier, period, empId, interimRemainMngMap, attTimeMonthly);
		
//		List<InterimRemain> interimRemain = require.interimRemain(empId);
//		String mngId = interimRemain.get(0).getRemainManaID();
		
		// 年休残数更新
		if (output.getAnnualLeave().isPresent())
			atomTask.add(RemainAnnualLeaveUpdating.updateRemainAnnualLeave(require, output.getAnnualLeave().get(), period, empId));
		
//		// Fix bug 109524
//		atomTask.add(AtomTask.of(() -> require.deleteTmpAnnualHolidayMng(mngId)));
		
		// 積立年休残数更新
		if (output.getReserveLeave().isPresent())
			atomTask.add(RemainReserveAnnualLeaveUpdating.updateReservedAnnualLeaveRemainNumber(require, output.getReserveLeave().get(), period, empId));
		// 年休暫定データ削除
		atomTask.add(AnnualLeaveTempDataDeleting.deleteTempAnnualLeaveData(require, empId, period.getPeriod()));
				
		// 積立年休暫定データ削除
		atomTask.add(RsvAnnualLeaveTempDataDeleting.deleteTempRsvAnnualLeaveData(require, empId, period.getPeriod()));
		
//		// Fix bug 109524
//		atomTask.add(AtomTask.of(() -> require.deleteTmpResereLeaveMng(mngId)));
		
		return AtomTask.bundle(atomTask);
	}
	
	public static interface RequireM1 extends RemainAnnualLeaveCalculation.RequireM1,
		RemainAnnualLeaveUpdating.RequireM5, AnnualLeaveTempDataDeleting.RequireM1,
		RemainReserveAnnualLeaveUpdating.RequireM5, RsvAnnualLeaveTempDataDeleting.RequireM1 {
		
//		List<InterimRemain> interimRemain(String sId);
//		
//		void deleteTmpAnnualHolidayMng(String mngId);
//		
//		void deleteTmpResereLeaveMng(String mngId);
	}
}
