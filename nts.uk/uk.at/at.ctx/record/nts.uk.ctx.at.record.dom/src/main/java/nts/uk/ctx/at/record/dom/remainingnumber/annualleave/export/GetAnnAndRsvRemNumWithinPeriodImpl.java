package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AnnualLeaveInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.GetRsvLeaRemNumWithinPeriodParam;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.interim.TempAnnualLeaveManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.interim.TmpResereLeaveMng;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 実装：期間中の年休積休残数を取得
 * @author shuichu_ishida
 */
@Stateless
public class GetAnnAndRsvRemNumWithinPeriodImpl implements GetAnnAndRsvRemNumWithinPeriod {

	/** 期間中の年休残数を取得 */
	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;
	/** 期間中の積立年休残数を取得 */
	@Inject
	private GetRsvLeaRemNumWithinPeriod getRsvLeaRemNumWithinPeriod;
	
	/** 期間中の年休積休残数を取得 */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			TempAnnualLeaveMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TempAnnualLeaveManagement>> tempAnnDataforOverWriteList,
			Optional<List<TmpResereLeaveMng>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave) {
		
		return this.algorithm(companyId, employeeId, aggrPeriod, mode, criteriaDate,
				isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
				tempAnnDataforOverWriteList, tempRsvDataforOverWriteList,
				Optional.empty(), Optional.empty(), prevAnnualLeave, prevReserveLeave,
				Optional.empty(), Optional.empty());
	}
	
	/** 期間中の年休積休残数を取得　（月次集計用） */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			TempAnnualLeaveMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TempAnnualLeaveManagement>> tempAnnDataforOverWriteList,
			Optional<List<TmpResereLeaveMng>> tempRsvDataforOverWriteList,
			Optional<Boolean> isOutputForShortage, Optional<Boolean> noCheckStartDate,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave,
			Optional<MonAggrCompanySettings> companySets, Optional<MonthlyCalculatingDailys> monthlyCalcDailys) {
		
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();

		// 期間中の年休残数を取得
		Boolean isNoCheckStartDate = false;
		if (noCheckStartDate.isPresent()) isNoCheckStartDate = noCheckStartDate.get();
		val aggrResultOfAnnualOpt = this.getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId, aggrPeriod,
					mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
					tempAnnDataforOverWriteList, prevAnnualLeave, isNoCheckStartDate, companySets, monthlyCalcDailys);

		// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
		aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
		
		// 期間中の積立年休を取得
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
