package nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnAndRsvLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.remainingnumber.reserveleave.export.param.AggrResultOfReserveLeave;
import nts.uk.ctx.at.shared.dom.remainingnumber.reserveleave.TempReserveLeaveManagement;
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
	
	/** 期間中の年休積休残数を取得 */
	@Override
	public AggrResultOfAnnAndRsvLeave algorithm(String companyId, String employeeId, DatePeriod aggrPeriod,
			TempAnnualLeaveMngMode mode, GeneralDate criteriaDate, boolean isGetNextMonthData,
			boolean isCalcAttendanceRate, Optional<Boolean> isOverWrite,
			Optional<List<TempAnnualLeaveManagement>> tempAnnDataforOverWriteList,
			Optional<List<TempReserveLeaveManagement>> tempRsvDataforOverWriteList,
			Optional<AggrResultOfAnnualLeave> prevAnnualLeave, Optional<AggrResultOfReserveLeave> prevReserveLeave) {
		
		AggrResultOfAnnAndRsvLeave aggrResult = new AggrResultOfAnnAndRsvLeave();

		// 期間中の年休残数を取得
		val aggrResultOfAnnualOpt = this.getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId, aggrPeriod,
				mode, criteriaDate, isGetNextMonthData, isCalcAttendanceRate, isOverWrite,
				tempAnnDataforOverWriteList, prevAnnualLeave);

		// 「年休積立年休の集計結果．年休」　←　受け取った「年休の集計結果」
		aggrResult.setAnnualLeave(aggrResultOfAnnualOpt);
		
		// 期間中の積立年休を取得
		//*****（未）　処理設計中のため、空クラスを仮に返却。2018.4.11 shuichi_ishida
		val aggrResultOfreserveOpt = Optional.of(new AggrResultOfReserveLeave());
		
		// 「年休積立年休の集計結果．積休」　←　受け取った「積立年休の集計結果」
		aggrResult.setReserveLeave(aggrResultOfreserveOpt);
		
		// 「年休積立年休の集計結果」を返す
		return aggrResult;
	}
}
