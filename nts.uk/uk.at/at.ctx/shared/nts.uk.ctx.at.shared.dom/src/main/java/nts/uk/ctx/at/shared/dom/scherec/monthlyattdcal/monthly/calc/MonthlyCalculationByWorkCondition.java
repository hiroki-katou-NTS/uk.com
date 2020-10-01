package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.AttendanceDaysMonth;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrMessageContent;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
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
		val cacheCarrier = new CacheCarrier();
		val companySets = require.monAggrCompanySettings(cid);
		val employeeSets = require.monAggrEmployeeSettings(cacheCarrier, cid, sid, period);
		
		/** ○社員の労働条件を期間で取得する */
		val workConditions = require.workingConditionItem(cid, Arrays.asList(sid), period);
		
		if (workConditions.isEmpty()) {
			
			/** ○エラーログ書き込み */
			return CalculateResult.fail(new MonthlyAggregationErrorInfo("006", 
												new ErrMessageContent(TextResource.localize("Msg_430"))));
		}
		MonthlyCalculation result = new MonthlyCalculation();
		
		/** 履歴の数だけループ */
		workConditions.stream().forEach(wc -> {
			
			/** ○処理期間を計算 */
			val calcPeriod = MonthlyCalculation.confirmProcPeriod(wc.getDatePeriod(), period);
			val baseDate = calcPeriod.end();
			
			val monthCalc = calcMonth(require, cid, sid, baseDate.yearMonth(), baseDate, 
					annualLeaveDeductDays, absenceDeductTime, aggrAtr, dailyRecords, 
					cacheCarrier, companySets, employeeSets, wc.getWorkingConditionItem(), calcPeriod);
			
			result.sum(monthCalc);
		});
		
		return CalculateResult.success(result);
	}
	
	public static MonthlyCalculation calcMonth(RequireM3 require, String cid, String sid, 
			List<IntegrationOfDaily> dailyRecords, YearMonth ym, GeneralDate baseDate,
			WorkingConditionItem wc, DatePeriod calcPeriod) {
		
		val cacheCarrier = new CacheCarrier();
		val companySets = require.monAggrCompanySettings(cid);
		val employeeSets = require.monAggrEmployeeSettings(cacheCarrier, cid, sid, calcPeriod);
		
		return calcMonth(require, cid, sid, ym, baseDate, Optional.empty(), Optional.empty(),
				MonthlyAggregateAtr.EXCESS_OUTSIDE_WORK, dailyRecords, cacheCarrier,
				companySets, employeeSets, wc, calcPeriod);
	}

	private static MonthlyCalculation calcMonth(RequireM2 require, String cid, String sid,
			YearMonth ym, GeneralDate baseDate, Optional<AttendanceDaysMonth> annualLeaveDeductDays, 
			Optional<AttendanceTimeMonth> absenceDeductTime,
			MonthlyAggregateAtr aggrAtr, List<IntegrationOfDaily> dailyRecords,
			CacheCarrier cacheCarrier, MonAggrCompanySettings companySets,
			MonAggrEmployeeSettings employeeSets, WorkingConditionItem wc, DatePeriod calcPeriod) {
		
		val monthlyCalcDailys = MonthlyCalculatingDailys.create(require, sid, calcPeriod, dailyRecords, wc);
		
		MonthlyCalculation monthCalc = new MonthlyCalculation();
		
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, sid, baseDate);
		val closureDate = closure.getClosureHistories().get(0).getClosureDate();
		val startWeekNo = (baseDate.day() / 7) + 1;
		
		monthCalc.prepareAggregation(require, cacheCarrier, cid, sid, ym,
				closure.getClosureId(), closureDate, calcPeriod, wc,
				startWeekNo, companySets, employeeSets, monthlyCalcDailys, new MonthlyOldDatas());
		
		/** ○処理中の労働制を確認する */
		if (wc.getLaborSystem() == WorkingSystem.FLEX_TIME_WORK) {
			
			monthCalc.aggregate(require, cacheCarrier, calcPeriod, aggrAtr, 
					annualLeaveDeductDays, absenceDeductTime, Optional.empty());
		} else {
			monthCalc.aggregate(require, cacheCarrier, calcPeriod, aggrAtr, 
					Optional.empty(), Optional.empty(), Optional.empty());
		}
		return monthCalc;
	}
	
	public static interface RequireM3 extends RequireM2 {

		MonAggrCompanySettings monAggrCompanySettings(String cid);
		
		MonAggrEmployeeSettings monAggrEmployeeSettings(CacheCarrier cacheCarrier, 
				String companyId, String employeeId, DatePeriod period);
	}
	
	public static interface RequireM2 extends MonthlyCalculatingDailys.RequireM4,
		ClosureService.RequireM3, MonthlyCalculation.RequireM5, MonthlyCalculation.RequireM4 {
		
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
	
	public static interface RequireM1 extends RequireM3 {
		
		List<WorkingConditionItemWithPeriod> workingConditionItem(String companyID, List<String> lstEmpID, DatePeriod datePeriod);
		
	}
}
