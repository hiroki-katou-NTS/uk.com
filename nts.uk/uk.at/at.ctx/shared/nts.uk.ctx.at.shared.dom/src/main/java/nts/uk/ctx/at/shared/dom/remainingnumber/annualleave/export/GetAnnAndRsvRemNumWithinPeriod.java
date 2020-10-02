package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriodParam;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;

/**
 * 期間中の年休積休残数を取得
 * @author shuichi_ishida
 */
public class GetAnnAndRsvRemNumWithinPeriod {

	/**
	 * 期間中の年休積休残数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWrite 上書きフラグ
	 * @param tempAnnDataforOverWriteList 上書き用の暫定年休管理データ
	 * @param tempRsvDataforOverWriteList 上書き用の暫定積休管理データ
	 * @param isOutputForShortage 不足分付与残数データ出力区分
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @param prevAnnualLeave 前回の年休の集計結果
	 * @param prevReserveLeave 前回の積立年休の集計結果
	 * @return 年休積立年休の集計結果
	 */
	public static AggrResultOfAnnAndRsvLeave algorithm(RequireM2 require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, DatePeriod aggrPeriod, InterimRemainMngMode mode, GeneralDate criteriaDate, 
			boolean isGetNextMonthData, boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave) {
		
		return algorithm(require, cacheCarrier, companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
				tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
				Optional.empty(), Optional.empty(), prevAnnualLeave, prevReserveLeave,
				Optional.empty(), Optional.empty(), Optional.empty());
	}

	/**
	 * 期間中の年休積休残数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param aggrPeriod 集計期間
	 * @param mode モード
	 * @param criteriaDate 基準日
	 * @param isGetNextMonthData 翌月管理データ取得フラグ
	 * @param isCalcAttendanceRate 出勤率計算フラグ
	 * @param isOverWrite 上書きフラグ
	 * @param tempAnnDataforOverWriteList 上書き用の暫定年休管理データ
	 * @param tempRsvDataforOverWriteList 上書き用の暫定積休管理データ
	 * @param isOutputForShortage 不足分付与残数データ出力区分
	 * @param noCheckStartDate 集計開始日を締め開始日とする　（締め開始日を確認しない）
	 * @param prevAnnualLeave 前回の年休の集計結果
	 * @param prevReserveLeave 前回の積立年休の集計結果
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @param monthlyCalcDailys 月の計算中の日別実績データ
	 * @return 年休積立年休の集計結果
	 */
	public static AggrResultOfAnnAndRsvLeave algorithm(RequireM2 require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, DatePeriod aggrPeriod, InterimRemainMngMode mode, GeneralDate criteriaDate, 
			boolean isGetNextMonthData, boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave,
			Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets,
			Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();

		// 集計開始日までの年休積立年休を取得
		{
			boolean isChangeParam = false;
			
			// 集計開始日時点の前回年休の集計結果が存在するかチェック
			boolean isExistPrevAnnual = false;
			if (prevAnnualLeave.isPresent()){
				if (prevAnnualLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
					isExistPrevAnnual = true;
				}
			}
			boolean isExistPrevReserve = false;
			if (isExistPrevAnnual){
				
				// 集計開始日時点の前回の積立年休の集計結果が存在するかチェック
				if (prevReserveLeave.isPresent()){
					if (prevReserveLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
						isExistPrevReserve = true;
					}
				}
			}
			if (!isExistPrevAnnual && !isExistPrevReserve){
				
				// 「集計開始日を締め開始日とする」をチェック
				boolean isCheck = false;
				if (noCheckStartDate.isPresent()) if (noCheckStartDate.get()) isCheck = true;
				if (!isCheck) isChangeParam = true;
			}

			if (isChangeParam){
				
				// 休暇残数を計算する締め開始日を取得する
				GeneralDate closureStart = null;	// 締め開始日
				{
					// 最新の締め終了日翌日を取得する
					Optional<ClosureStatusManagement> sttMng = require.latestClosureStatusManagement(employeeId);
					if (sttMng.isPresent()){
						closureStart = sttMng.get().getPeriod().end().addDays(1);
					}
					else {
						
						//　社員に対応する締め開始日を取得する
						val closureStartOpt = GetClosureStartForEmployee.algorithm(require, cacheCarrier, employeeId);
						if (closureStartOpt.isPresent()) closureStart = closureStartOpt.get();
					}
				}
				
				if (closureStart != null){
					if (closureStart.equals(aggrPeriod.start())){
						
						// 「集計開始日を締め開始日にする」をtrueにする
						noCheckStartDate = Optional.of(true);
					}
					if (closureStart.before(aggrPeriod.start())){
						
						// 「期間中の年休積立年休残数を取得」を実行する
						val prevResult = algorithm(require, cacheCarrier, companyId, employeeId,
								new DatePeriod(closureStart, aggrPeriod.start().addDays(-1)),
								mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
								tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
								isOutputForShortage, Optional.of(true), Optional.empty(), Optional.empty(),
								companySets, employeeSets, Optional.empty());
						
						// 受け取った結果をパラメータに反映する
						noCheckStartDate = Optional.of(false);
						prevAnnualLeave = prevResult.getAnnualLeave();
						prevReserveLeave = prevResult.getReserveLeave();
					}
				}
			}
		}
		
		// 期間中の年休残数を取得
		Boolean isNoCheckStartDate = false;
		if (noCheckStartDate.isPresent()) isNoCheckStartDate = noCheckStartDate.get();
		val aggrResultOfAnnualOpt = GetAnnLeaRemNumWithinPeriodProc.algorithm(require, cacheCarrier, companyId, employeeId, aggrPeriod,
					mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
					tempAnnDataforOverWriteList, prevAnnualLeave, isNoCheckStartDate,
					companySets, employeeSets, monthlyCalcDailys);

		// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
		aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
		
		// 期間中の積立年休残数を取得
		List<AnnualLeaveInfo> lapsedAnnualLeaveInfos = new ArrayList<>();
		if (aggrResultOfAnnualOpt.isPresent()){
			if (aggrResultOfAnnualOpt.get().getLapsed().isPresent()){
				lapsedAnnualLeaveInfos = aggrResultOfAnnualOpt.get().getLapsed().get();
			}
		}
		GetRsvLeaRemNumWithinPeriodParam rsvParam = new GetRsvLeaRemNumWithinPeriodParam(
				companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, lapsedAnnualLeaveInfos, isOverWrite, tempRsvDataforOverWriteList,
				isOutputForShortage, noCheckStartDate, prevReserveLeave);
		val aggrResultOfreserveOpt = GetRsvLeaRemNumWithinPeriod.algorithm(require, cacheCarrier,
				rsvParam, companySets, monthlyCalcDailys);
		
		// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
		aggrResult.setReserveLeave(aggrResultOfreserveOpt);
		
		// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}

	public static AggrResultOfAnnAndRsvLeave getRemainAnnRscByPeriod(RequireM1 require, CacheCarrier cacheCarrier, 
			String cID, String sID, DatePeriod aggrPeriod, InterimRemainMngMode mode, GeneralDate criteriaDate, 
			boolean isGetNextMonthData, boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite, 
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList, Optional<Boolean> isOutputForShortage,
			Optional<Boolean> noCheckStartDate, Optional<AggrResultOfAnnualLeave> prevAnnualLeave,
			Optional<AggrResultOfReserveLeave> prevReserveLeave, Optional<MonAggrCompanySettings> companySets,
			Optional<MonAggrEmployeeSettings> employeeSets, Optional<MonthlyCalculatingDailys> monthlyCalcDailys,
			Optional<ClosureStatusManagement> sttMng, Optional<GeneralDate> clsStrOpt) {
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();
			// 集計開始日までの年休積立年休を取得
			{
				boolean isChangeParam = false;
				// 集計開始日時点の前回年休の集計結果が存在するかチェック
				boolean isExistPrevAnnual = false;
				if (prevAnnualLeave.isPresent()){
					if (prevAnnualLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
						isExistPrevAnnual = true;
					}
				}
				boolean isExistPrevReserve = false;
				if (isExistPrevAnnual){
					// 集計開始日時点の前回の積立年休の集計結果が存在するかチェック
					if (prevReserveLeave.isPresent()){
						if (prevReserveLeave.get().getAsOfStartNextDayOfPeriodEnd().getYmd().equals(aggrPeriod.start())){
							isExistPrevReserve = true;
						}
					}
				}
				if (!isExistPrevAnnual && !isExistPrevReserve){
					// 「集計開始日を締め開始日とする」をチェック
					boolean isCheck = false;
					if (noCheckStartDate.isPresent()) if (noCheckStartDate.get()) isCheck = true;
					if (!isCheck) isChangeParam = true;
				}
	
				if (isChangeParam){
					// 休暇残数を計算する締め開始日を取得する
					GeneralDate closureStart = null;	// 締め開始日
					{
						if (sttMng.isPresent()){
							closureStart = sttMng.get().getPeriod().end().addDays(1);
						}else {
							if (clsStrOpt.isPresent()) closureStart = clsStrOpt.get();
						}
					}
					if (closureStart != null){
						if (closureStart.equals(aggrPeriod.start())){
							
							// 「集計開始日を締め開始日にする」をtrueにする
							noCheckStartDate = Optional.of(true);
						}
						if (closureStart.before(aggrPeriod.start())){
							// 「期間中の年休積立年休残数を取得」を実行する
							val prevResult = getRemainAnnRscByPeriod(require, cacheCarrier, cID, sID,
									new DatePeriod(closureStart, aggrPeriod.start().addDays(-1)),
									mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
									tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
									isOutputForShortage, Optional.of(true), Optional.empty(), Optional.empty(),
									companySets, employeeSets, Optional.empty(), sttMng, clsStrOpt);
							
							// 受け取った結果をパラメータに反映する
							noCheckStartDate = Optional.of(false);
							prevAnnualLeave = prevResult.getAnnualLeave();
							prevReserveLeave = prevResult.getReserveLeave();
						}
					}
				}
			}
			
			// 期間中の年休残数を取得
			Boolean isNoCheckStartDate = false;
			if (noCheckStartDate.isPresent()) isNoCheckStartDate = noCheckStartDate.get();
			val aggrResultOfAnnualOpt = GetAnnLeaRemNumWithinPeriodProc.algorithm(require, cacheCarrier,
						cID, sID, aggrPeriod,
						mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
						tempAnnDataforOverWriteList, prevAnnualLeave, isNoCheckStartDate,
						companySets, employeeSets, monthlyCalcDailys);
	
			// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
			aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
			
			// 期間中の積立年休残数を取得
			List<AnnualLeaveInfo> lapsedAnnualLeaveInfos = new ArrayList<>();
			if (aggrResultOfAnnualOpt.isPresent()){
				if (aggrResultOfAnnualOpt.get().getLapsed().isPresent()){
					lapsedAnnualLeaveInfos = aggrResultOfAnnualOpt.get().getLapsed().get();
				}
			}
			GetRsvLeaRemNumWithinPeriodParam rsvParam = new GetRsvLeaRemNumWithinPeriodParam(
					cID, sID, aggrPeriod, mode, criteriaDate,
					isGetNextMonthData, lapsedAnnualLeaveInfos, isOverWrite, tempRsvDataforOverWriteList,
					isOutputForShortage, noCheckStartDate, prevReserveLeave);
			val aggrResultOfreserveOpt = GetRsvLeaRemNumWithinPeriod.algorithm(require, cacheCarrier,
					rsvParam, companySets, monthlyCalcDailys);
			
			// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
			aggrResult.setReserveLeave(aggrResultOfreserveOpt);
			
			// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}
	
	public static interface RequireM1 extends GetAnnLeaRemNumWithinPeriodProc.RequireM3,
												GetRsvLeaRemNumWithinPeriod.RequireM4 {  
	
	}
	
	public static interface RequireM2 extends GetClosureStartForEmployee.RequireM1, RequireM1 {  

//		Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId);

	}

}
