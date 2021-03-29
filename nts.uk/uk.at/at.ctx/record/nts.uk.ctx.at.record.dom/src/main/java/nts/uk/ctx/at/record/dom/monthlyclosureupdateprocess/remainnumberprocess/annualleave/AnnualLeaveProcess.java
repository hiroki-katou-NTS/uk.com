package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.AttendanceTimeOfMonthly;
import nts.uk.shr.com.context.AppContexts;

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
	public static AtomTask annualHolidayProcess(Require require, CacheCarrier cacheCarrier, String cid, 
			AggrPeriodEachActualClosure period, String empId,
			List<DailyInterimRemainMngData> interimRemainMngMap, AttendanceTimeOfMonthly attTimeMonthly) {
		
		/** 年休残数計算 */
		val output = calculateRemainAnnualHoliday(require, cacheCarrier, period, empId, interimRemainMngMap, attTimeMonthly);
		
		/** 年休残数更新 */
		return AtomTask.of(RemainAnnualLeaveUpdating.updateRemainAnnualLeave(require, cid, output.getAnnualLeave(), period, empId))
						/** 年休暫定データ削除 */
						.then(() -> require.deleteInterim(empId, period.getPeriod(), RemainType.ANNUAL))
						/** 積立年休残数更新 */
						.then(RemainReserveAnnualLeaveUpdating.updateReservedAnnualLeaveRemainNumber(require, output.getReserveLeave(), period, empId))
						/** 積立年休暫定データ削除 */
						.then(() -> require.deleteInterim(empId, period.getPeriod(), RemainType.FUNDINGANNUAL));
	}
	
	/**
	 * 年休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @param attTimeMonthly 月別実績の勤怠時間
	 * @return 年休積立年休の集計結果
	 */
	public static AggrResultOfAnnAndRsvLeave calculateRemainAnnualHoliday(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			List<DailyInterimRemainMngData> interimRemainMngMap, AttendanceTimeOfMonthly attTimeMonthly) {
		
		String companyId = AppContexts.user().companyId();
		
		// 暫定残数データを年休・積立年休に絞り込む
		List<TmpAnnualLeaveMngWork> tmpAnnualLeaveMngs = new ArrayList<>();
		List<TmpReserveLeaveMngWork> tmpReserveLeaveMngs = new ArrayList<>();
		for (val interimRemainMng : interimRemainMngMap){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;
			val master = interimRemainMng.getRecAbsData().get(0);
			
			// 年休
			if (interimRemainMng.getAnnualHolidayData().isPresent()){
				val data = interimRemainMng.getAnnualHolidayData().get();
				tmpAnnualLeaveMngs.add(TmpAnnualLeaveMngWork.of(data));
			}
			
			// 積立年休
			if (interimRemainMng.getResereData().isPresent()){
				val data = interimRemainMng.getResereData().get();
				tmpReserveLeaveMngs.add(TmpReserveLeaveMngWork.of(master, data));
			}
		}

		// 月別実績の計算結果が存在するかチェック
//		if (attTimeMonthly != null){
//			
//			// 年休控除日数分の年休暫定残数データを作成する
//			val compensFlexWorkOpt = CreateInterimAnnualMngData.ofCompensFlexToWork(
//					attTimeMonthly, period.getPeriod().end());
//			if (compensFlexWorkOpt.isPresent()){
//				tmpAnnualLeaveMngs.add(compensFlexWorkOpt.get());
//			}
//		}
		
		// 「期間中の年休積休残数を取得」を実行する　→　「年休積立年休の集計結果」を返す
		return GetAnnAndRsvRemNumWithinPeriod.algorithm(require, cacheCarrier, companyId, 
				empId, period.getPeriod(), InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), true, true,
				Optional.of(true),
				Optional.of(tmpAnnualLeaveMngs), Optional.of(tmpReserveLeaveMngs),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	public static interface RequireM1 extends GetAnnAndRsvRemNumWithinPeriod.RequireM2 {
		
	}
	
	public static interface Require extends RequireM1,
		RemainAnnualLeaveUpdating.RequireM5, RemainReserveAnnualLeaveUpdating.RequireM5 {
		
		void deleteInterim(String sid, DatePeriod period, RemainType type);
	}
}
