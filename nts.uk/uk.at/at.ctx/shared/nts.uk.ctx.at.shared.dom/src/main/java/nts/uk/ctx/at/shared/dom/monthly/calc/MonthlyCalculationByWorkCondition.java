package nts.uk.ctx.at.shared.dom.monthly.calc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeAggregateService.AggregateResult;
import nts.uk.ctx.at.shared.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.shr.com.i18n.TextResource;

/** 労働条件ごとに月別実績を集計する */
public class MonthlyCalculationByWorkCondition {

	/**
	 * 労働条件ごとに月別実績を集計する
	 * @param cid 会社ID
	 * @param sid 社員ID
	 * @param period 期間
	 * @param annualLeaveDeductDays 年休控除日数　<<optional>>
	 * @param absenceDeductTime 欠勤控除時間　<<optional>>
	 * @param aggrAtr 集計区分
	 * @param dailyRecords 日別実績<<optional>> (List)
	 * @return
	 */
	public static CalculateResult calculate(RequireM1 require, String cid, String sid, 
			DatePeriod period, Optional<AttendanceDaysMonth> annualLeaveDeductDays, 
			Optional<AttendanceTimeMonth> absenceDeductTime, MonthlyAggregateAtr aggrAtr,
			List<IntegrationOfDaily> dailyRecords) {
		
		/** ○社員の労働条件を期間で取得する */
		val workConditions = require.workingConditionItem(cid, Arrays.asList(sid), period);
		
		if (workConditions.isEmpty()) {
			
			/** ○エラーログ書き込み */
			return CalculateResult.fail(new MonthlyAggregationErrorInfo("006", 
												new ErrMessageContent(TextResource.localize("Msg_430"))));
		}
		
		/** 履歴の数だけループ */
		workConditions.stream().forEach(wc -> {
			
			/** ○処理期間を計算 */
			val calcPeriod = calcPeriod(wc, period);
			
			/** ○処理中の労働制を確認する */
			if (wc.getWorkingConditionItem().getLaborSystem() == WorkingSystem.FLEX_TIME_WORK) {
				
				
			}
		});
		
		return null;
	}
	
	/** ○処理期間を計算 */
	private static DatePeriod calcPeriod(WorkingConditionItemWithPeriod workCondition, DatePeriod period) {
		
		if (workCondition.getDatePeriod().contains(period)) {
			return period;
		}
		
		if (workCondition.getDatePeriod().contains(period.start())) {
			return period.cutOffWithNewEnd(workCondition.getDatePeriod().end());
		}
		
		return period.cutOffWithNewStart(workCondition.getDatePeriod().start());
	}
	
	@Getter
	@AllArgsConstructor
	public static class CalculateResult {
		
		private Optional<MonthlyAggregationErrorInfo> error;
		
		private Optional<MonthlyCalculation> agreementTime;
		
		public static CalculateResult fail(MonthlyAggregationErrorInfo error) {
			return new CalculateResult(Optional.of(error), Optional.empty());
		}
		
		public static CalculateResult success(MonthlyCalculation monthlyCalc) {
			return new CalculateResult(Optional.empty(), Optional.of(monthlyCalc));
		}
	}
	
	public static interface RequireM1 {
		
		List<WorkingConditionItemWithPeriod> workingConditionItem(String companyID, List<String> lstEmpID, DatePeriod datePeriod);
	}
}
