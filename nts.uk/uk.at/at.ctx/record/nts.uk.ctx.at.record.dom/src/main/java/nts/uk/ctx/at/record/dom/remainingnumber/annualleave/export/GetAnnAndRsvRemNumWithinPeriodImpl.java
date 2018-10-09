package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriodParam;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TmpAnnualLeaveMngWork;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpReserveLeaveMngWork;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：期間中の年休積休残数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAnnAndRsvRemNumWithinPeriodImpl implements GetAnnAndRsvRemNumWithinPeriod {

	/** 期間中の年休残数を取得 */
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	/** 期間中の積立年休残数を取得 */
	@Inject
	private GetRsvLeaRemNumWithinPeriod getRsvLeaRemNumWithinPeriod;
	/** 締め状態管理 */
	@Inject
	private ClosureStatusManagementRepository closureSttMngRepo;
	/** 社員に対応する締め開始日を取得する */
	@Inject
	private GetClosureStartForEmployee getClosureStartForEmployee;
	
	/** 期間中の年休積休残数を取得 */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TmpAnnualLeaveMngWork>> tempAnnDataforOverWriteList,
			Optional<List<TmpReserveLeaveMngWork>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave) {
		
		return this.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
				tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
				Optional.empty(), Optional.empty(), prevAnnualLeave, prevReserveLeave,
				Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	/** 期間中の年休積休残数を取得　（月次集計用） */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			InterimRemainMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
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
					Optional<ClosureStatusManagement> sttMng = this.closureSttMngRepo.getLatestByEmpId(employeeId);
					if (sttMng.isPresent()){
						closureStart = sttMng.get().getPeriod().end().addDays(1);
					}
					else {
						
						//　社員に対応する締め開始日を取得する
						val closureStartOpt = this.getClosureStartForEmployee.algorithm(employeeId);
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
						val prevResult = this.algorithm(companyId, employeeId,
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
		val aggrResultOfAnnualOpt = this.getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId, aggrPeriod,
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
		val aggrResultOfreserveOpt = this.getRsvLeaRemNumWithinPeriod.algorithm(
				rsvParam, companySets, monthlyCalcDailys);
		
		// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
		aggrResult.setReserveLeave(aggrResultOfreserveOpt);
		
		// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}
}
