package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.pastmonth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.AggregateMonthlyRecordServiceProc;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.converter.MonthlyRecordToAttendanceItemConverter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roundingset.RoundingSetOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.AggregateAttendanceTimeValue;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.AgreementTimeAggregateService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.AnyItemAggregateService;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.editstate.EditStateOfMonthlyPerformance;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemWithPeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/** 月別実績過去月集計する */
public class AggregatePastMonthsService {

	/** 集計する */
	public static List<AggregatePastMonthResult> aggregate(Require require, String sid, 
			GeneralDate aggrStartDate, List<IntegrationOfDaily> dailyRecords) {
		
		val cId = AppContexts.user().companyId();
		val cacheCarrier = new CacheCarrier();
		
		/** 月別集計で必要な会社別設定を取得する */
		val comSets = require.monAggrCompanySettings(cId);
		
		/** 集計期間を判断する */
		val aggrPeriods = AggregatePastMonthsPeriodService.calcPeriod(require, cacheCarrier, cId, sid, aggrStartDate);
		
		/** 過去月集計結果一覧を作る */
		List<AggregatePastMonthResult> aggrResults = new ArrayList<>();
		
		aggrPeriods.forEach(ap -> {
			
			/** 過去月集計する */
			val result = aggrPastMonth(require, cacheCarrier, cId, sid, ap, dailyRecords, comSets);
			
			/** 過去月集計結果を一覧に入れる */
			aggrResults.add(result);
		});
		
		return aggrResults;
	}
	
	/** 過去月集計する */
	private static AggregatePastMonthResult aggrPastMonth(RequireM2 require, CacheCarrier cacheCarrier, String cid,
			 String sid, ClosurePeriod closurePeriod, List<IntegrationOfDaily> dailyRecords,
			 MonAggrCompanySettings companySets) {

		val ym = closurePeriod.getYearMonth();
		val closureId = closurePeriod.getClosureId();
		val closureDate = closurePeriod.getClosureDate();
		val period = closurePeriod.getPeriod();
		
		val empSets = require.monAggrEmployeeSettings(cacheCarrier, cid, sid, period);
		
		/** 日別勤怠（Work）一覧を取得する */
		val dailyWorks = MonthlyCalculatingDailys.loadData(require, sid, 
				period, Optional.of(dailyRecords), empSets);
		
		/** 社員の労働条件を期間で取得する */
		val workConditionItems = require.getWorkingConditionItemWithPeriod(cid, Arrays.asList(sid), period);
		workConditionItems.sort((c1, c2) -> c1.getDatePeriod().start().compareTo(c2.getDatePeriod().start()));
		
		/** 集計前の月別実績データを取得する */
		val monthlyOldDatas = MonthlyOldDatas.loadData(require, sid, ym, closureId, closurePeriod.getClosureDate());
		val editStates = require.monthEditStates(sid, ym, closureId, closurePeriod.getClosureDate());
		val converter = require.createMonthlyConverter();
		
		/** 月別実績の勤怠時間を集計する */
		val attendanceTime = aggrMonthAttendanceTime(require, cacheCarrier, cid, sid, ym, period, closureId, 
				closureDate, companySets, empSets, dailyWorks, workConditionItems, monthlyOldDatas, editStates,
				converter);
		
		/** 36協定時間の集計 */
		val agreementTimeResults = AgreementTimeAggregateService.aggregate(require, cacheCarrier, cid, 
				sid, ym, closureId, closureDate, period, companySets, empSets, dailyWorks,  
				monthlyOldDatas, Optional.of(attendanceTime.getAttendanceTime().getMonthlyCalculation()));
		val agreementTimes = agreementTimeResults.stream().filter(c -> c.getAgreementTime().isPresent())
				.map(c -> c.getAgreementTime().get()).collect(Collectors.toList());
		
		/** ○任意項目を集計 */
		val anyItems = AnyItemAggregateService.aggregate(require, cacheCarrier, cid, sid, ym, closureId, 
				closureDate, period, companySets, empSets, dailyWorks, monthlyOldDatas, editStates, 
				attendanceTime.getAttendanceTimeWeeks(), Optional.ofNullable(attendanceTime.getAttendanceTime()));
		
		return AggregatePastMonthResult.builder()
										.monthlyAttdTime(attendanceTime.getAttendanceTime())
										.weeklyAttdTime(attendanceTime.getAttendanceTimeWeeks())
										.agreementTime(agreementTimes)
										.monthlyAnyItem(anyItems)
										.build();
	}
	
	/** 月別実績の勤怠時間を集計する */
	private static AggregateAttendanceTimeValue aggrMonthAttendanceTime(RequireM3 require, CacheCarrier cacheCarrier,
			String cid, String sid, YearMonth ym, DatePeriod period, ClosureId closureId, 
			ClosureDate closureDate, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets,
			MonthlyCalculatingDailys dailyWorks, List<WorkingConditionItemWithPeriod> workCondition,
			MonthlyOldDatas monthlyOldDatas, List<EditStateOfMonthlyPerformance> editStates,
			MonthlyRecordToAttendanceItemConverter converter) {
		
		/** 制労働ごと期間を計算する */
		val workConGroup = mergeWorkCondition(workCondition, period);
		val aggrAttendanceTime = new AggregateAttendanceTimeValue(sid, ym, closureId, closureDate, period);
		
		for (val wc : workConGroup) {
			
			/** 月別実績の勤怠時間を集計 */
			val aggrResult = new AggregateAttendanceTimeValue(sid, ym, closureId, closureDate, wc.getDatePeriod());
			val attendanceTimeWeeks = aggrResult.getAttendanceTime().aggregateAttendanceTime(require, cacheCarrier, 
					cid, wc.getDatePeriod(), wc.getWorkingConditionItem(), companySets, employeeSets, 
					dailyWorks, monthlyOldDatas, new HashMap<>());
			aggrResult.getAttendanceTimeWeeks().addAll(attendanceTimeWeeks);
			
			/** データの合算 */
			aggrAttendanceTime.sum(aggrResult);
			
		}
		
		/** 月別実績の時間項目を丸める */
		val rounded = companySets.getRoundingSet().round(require, aggrAttendanceTime.getAttendanceTime());
		aggrAttendanceTime.setAttendanceTime(rounded);
		
		/** 手修正された項目を元に戻す */
		revertAttendanceTime(monthlyOldDatas, editStates, converter, aggrAttendanceTime);
		
		/** 手修正を戻してから計算必要な項目を再度計算 */
		aggrAttendanceTime.getAttendanceTime().recalcSomeItem();
		
		/** 手修正された項目を元に戻す */
		revertAttendanceTime(monthlyOldDatas, editStates, converter, aggrAttendanceTime);
		
		return aggrAttendanceTime;
	}

	/** 手修正された項目を元に戻す */
	private static void revertAttendanceTime(MonthlyOldDatas monthlyOldDatas, List<EditStateOfMonthlyPerformance> editStates,
			MonthlyRecordToAttendanceItemConverter converter, AggregateAttendanceTimeValue aggrAttendanceTime) {

		if (!monthlyOldDatas.getAttendanceTime().isPresent()) return;
		
		val itemIds = editStates.stream().map(c -> c.getAttendanceItemId()).collect(Collectors.toList());
		converter.withAttendanceTime(monthlyOldDatas.getAttendanceTime().get());
		val oldData = converter.convert(itemIds);
		converter.withAttendanceTime(aggrAttendanceTime.getAttendanceTime());
		converter.merge(oldData);
		aggrAttendanceTime.setAttendanceTime(converter.toAttendanceTime().get());
	}
	
	/** 制労働ごと期間を計算する */
	private static List<WorkingConditionItemWithPeriod> mergeWorkCondition(List<WorkingConditionItemWithPeriod> workCondition, DatePeriod period) {
		
		/** 期間開始日をセットする */
		GeneralDate startDate = period.start();
		
		/** 制労働ごと期間一覧を作る */
		List<WorkingConditionItemWithPeriod> workConditions = new ArrayList<>();
		
		while(startDate.beforeOrEquals(period.end())) {
			
			val currentDate = startDate;
			/** 期間開始日を含む労働条件項目を取得する */
			val currentWc = workCondition.stream().filter(c -> c.getDatePeriod().contains(currentDate)).findFirst().orElse(null);
			
			if (currentWc == null) return workConditions;

			/** 取得できた労働条件項目と違う制労働の労働条件項目を取得する */
			val nextWc = workCondition.stream().filter(c -> c.getDatePeriod().start().after(currentWc.getDatePeriod().end()) 
						&& c.getWorkingConditionItem().getLaborSystem() != currentWc.getWorkingConditionItem().getLaborSystem())
					.findFirst().orElse(null);
			
			if (nextWc == null) {
				/** 期間を作って制労働ごと期間一覧に入れる */
				workConditions.add(new WorkingConditionItemWithPeriod(new DatePeriod(startDate, period.end()), 
																	currentWc.getWorkingConditionItem()));
				return workConditions;
			}
			
			/** 期間終了日を計算する */
			val endDate = nextWc.getDatePeriod().end().beforeOrEquals(period.end()) 
					? nextWc.getDatePeriod().start().addDays(-1) : period.end();

			/** 期間を作って制労働ごと期間一覧に入れる */
			workConditions.add(new WorkingConditionItemWithPeriod(new DatePeriod(startDate, endDate), 
																currentWc.getWorkingConditionItem()));
			/** 期間開始日をセットする */
			startDate = endDate.addDays(1);
		}
		
		return workConditions;
	}

	public static interface RequireM4 {
		
	}
	
	public static interface RequireM5 {
		
	}
	
	public static interface RequireM3 extends AggregateMonthlyRecordServiceProc.RequireM13 {
		
	}
	
	public static interface RequireM2 extends RequireM3, RequireM4, RequireM5, 
			MonthlyCalculatingDailys.RequireM4, AgreementTimeAggregateService.Require,
			AnyItemAggregateService.Require {
		
		MonAggrEmployeeSettings monAggrEmployeeSettings(CacheCarrier cacheCarrier, String cid,
				String sid, DatePeriod period);
		
		List<WorkingConditionItemWithPeriod> getWorkingConditionItemWithPeriod(String companyID , List<String> lstEmpID, DatePeriod datePeriod);
		
		List<EditStateOfMonthlyPerformance> monthEditStates(String employeeId, YearMonth yearMonth, ClosureId closureId,
				ClosureDate closureDate);
		
		MonthlyRecordToAttendanceItemConverter createMonthlyConverter();
	}
	
	public static interface Require extends AggregatePastMonthsPeriodService.RequireM3,
			RequireM2 {
		
		Optional<RoundingSetOfMonthly> monthRoundingSet(String companyId);
		
		MonAggrCompanySettings monAggrCompanySettings(String cid);
	}
}

