package nts.uk.ctx.at.record.dom.monthlyclosureupdateprocess.remainnumberprocess.annualleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlycommon.aggrperiod.AggrPeriodEachActualClosure;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnAndRsvRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.DailyInterimRemainMngData;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveMngs;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
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
	 */
	public static AtomTask annualHolidayProcess(Require require, CacheCarrier cacheCarrier, String cid,
			AggrPeriodEachActualClosure period, String empId, List<DailyInterimRemainMngData> interimRemainMngMap) {

		/** 年休残数計算 */
		val output = calculateRemainAnnualHoliday(require, cacheCarrier, period, empId, interimRemainMngMap);

		/** 年休残数更新 */
		return AtomTask.of(RemainAnnualLeaveUpdating.updateRemainAnnualLeave(require, cid, output.getAnnualLeave(), period, empId))
						/** 年休暫定データ削除 */
						.then(deleteTempAnnualLeave(require, empId, period.getPeriod().end()))
						/** 積立年休残数更新 */
						.then(RemainReserveAnnualLeaveUpdating.updateReservedAnnualLeaveRemainNumber(require, output.getReserveLeave(), period, empId))
						/** 積立年休暫定データ削除 */
						.then(deleteTempResereLeave(require, empId, period.getPeriod().end()));
	}

	/**
	 * 年休残数計算
	 * @param period 実締め毎集計期間
	 * @param empId 社員ID
	 * @param interimRemainMngMap 暫定管理データリスト
	 * @return 年休積立年休の集計結果
	 */
	public static AggrResultOfAnnAndRsvLeave calculateRemainAnnualHoliday(RequireM1 require, CacheCarrier cacheCarrier,
			AggrPeriodEachActualClosure period, String empId,
			List<DailyInterimRemainMngData> interimRemainMngMap) {

		String companyId = AppContexts.user().companyId();

		// 暫定残数データを年休・積立年休に絞り込む
		List<TempAnnualLeaveMngs> tmpAnnualLeaveMngs = new ArrayList<>();
		List<TmpResereLeaveMng> tmpReserveLeaveMngs = new ArrayList<>();
		for (val interimRemainMng : interimRemainMngMap){
			if (interimRemainMng.getRecAbsData().size() <= 0) continue;

			// 年休
			interimRemainMng.getAnnualHolidayData().forEach(c->tmpAnnualLeaveMngs.add(c));
			// 積立年休
			if (interimRemainMng.getResereData().isPresent()){
				tmpReserveLeaveMngs.add(interimRemainMng.getResereData().get());
			}
		}


		// 「期間中の年休積休残数を取得」を実行する　→　「年休積立年休の集計結果」を返す
		return GetAnnAndRsvRemNumWithinPeriod.algorithm(require,
				cacheCarrier,
				companyId,
				empId, period.getPeriod(), InterimRemainMngMode.MONTHLY,
				period.getPeriod().end(), true, true,
				Optional.of(true),
				Optional.of(tmpAnnualLeaveMngs), Optional.of(tmpReserveLeaveMngs),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),Optional.of(period.getPeriod()));
	}
	
	
	/**
	 * 年休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempAnnualLeave(Require require, String employeeId, GeneralDate ymd){
		return AtomTask.of(() -> require.deleteTempAnnualBySidBeforeTheYmd(employeeId, ymd));
	}
	
	/**
	 * 積立年休暫定データ削除
	 * @param require
	 * @param employeeId
	 * @param period
	 * @return
	 */
	public static AtomTask deleteTempResereLeave(Require require, String employeeId, GeneralDate ymd){
		return AtomTask.of(() -> require.deleteTempResereBySidBeforeTheYmd(employeeId, ymd));
	}

	public static interface RequireM1 extends GetAnnAndRsvRemNumWithinPeriod.RequireM2 {

	}

	public static interface Require extends RequireM1,
		RemainAnnualLeaveUpdating.RequireM5, RemainReserveAnnualLeaveUpdating.RequireM5{
		
		void deleteTempAnnualBySidBeforeTheYmd(String sid, GeneralDate ymd);
		void deleteTempResereBySidBeforeTheYmd(String sid, GeneralDate ymd);	
	}
}
