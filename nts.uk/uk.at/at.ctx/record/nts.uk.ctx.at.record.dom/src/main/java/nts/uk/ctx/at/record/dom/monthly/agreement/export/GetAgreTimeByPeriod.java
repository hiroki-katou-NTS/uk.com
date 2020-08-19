package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.standardtime.AgreementMonthSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.AgreementYearSetting;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSetting;
import nts.uk.ctx.at.record.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.shared.dom.common.Month;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.PeriodAtrOfAgreement;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemCustom;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/**
 * 指定期間36協定時間の取得
 * @author shuichi_ishida
 */
public class GetAgreTimeByPeriod {

	/**
	 * 指定期間36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param startMonth 起算月
	 * @param year 年度
	 * @param periodAtr 期間区分
	 * @return 指定期間36協定時間リスト
	 */
	public static List<AgreementTimeByPeriod> algorithm(RequireM8 require, String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr) {
		
		return internalAlgorithm(require, companyId, employeeId, criteria, startMonth, year, periodAtr, null);
	}

	/**
	 * 指定期間36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param startMonth 起算月
	 * @param year 年度
	 * @param periodAtr 期間区分
	 * @return 指定期間36協定時間リスト
	 */
	public static List<AgreementTimeByPeriod> algorithm(RequireM8 require, String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr, AgeementTimeCommonSetting settingGetter) {
		
		return internalAlgorithm(require, companyId, employeeId, criteria, startMonth, year, periodAtr, settingGetter);
	}
	
	public static List<AgreementTimeByEmp> algorithmImprove(RequireM7 require, 
			String companyId, List<String> employeeIds, GeneralDate criteria,
            Month startMonth, Year year, List<PeriodAtrOfAgreement> periodAtrs,  
            Map<String, YearMonthPeriod> periodWorking) {
		
		YearMonth startYm = YearMonth.of(year.v(), startMonth.v());
		List<YearMonth> periodYmAll = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			periodYmAll.add(startYm.addMonths(i));
		}
		// 36協定時間を取得

		List<AgreementTimeOfManagePeriod> listAgreementTimeOfManagePeriod = require
				.agreementTimeOfManagePeriod(employeeIds, periodYmAll)
				.stream().filter(c -> {
					return periodWorking.get(c.getEmployeeId()).contains(c.getYearMonth());
				}).collect(Collectors.toList());

		Map<String, List<AgreementTimeOfManagePeriod>> agreementTimeAll = listAgreementTimeOfManagePeriod.stream()
				.collect(Collectors.groupingBy(AgreementTimeOfManagePeriod::getEmployeeId));

		// 「労働条件項目」を取得
		Map<String, WorkingConditionItemCustom> workingConditionItemAll = require
				.workingConditionItemCustom(employeeIds, criteria).stream()
				.collect(Collectors.toMap(WorkingConditionItemCustom::getEmployeeId, x -> x));

		Map<String, AgreementYearSetting> yearSetAll = new HashMap<>();
		if (periodAtrs.contains(PeriodAtrOfAgreement.ONE_YEAR)) {
			// 36協定年度設定を取得する
			yearSetAll = require.agreementYearSetting(employeeIds, year.v()).stream()
					.collect(Collectors.toMap(AgreementYearSetting::getEmployeeId, x -> x));
		}

		Map<String, List<AgreementMonthSetting>> monthSetAll = new HashMap<>();
		if (periodAtrs.contains(PeriodAtrOfAgreement.ONE_MONTH)) {
			// 36協定年月設定を取得する

			List<AgreementMonthSetting> agreementMonthSettings = require
					.agreementMonthSetting(employeeIds, periodYmAll)
					.stream().filter(c -> {
						return periodWorking.get(c.getEmployeeId()).contains(c.getYearMonthValue());
					}).collect(Collectors.toList());

			monthSetAll = agreementMonthSettings.stream()
					.collect(Collectors.groupingBy(AgreementMonthSetting::getEmployeeId));
		}

		Map<String, AgreementYearSetting> finalYearSetAll = yearSetAll;
		Map<String, List<AgreementMonthSetting>> finalMonthSetAll = monthSetAll;
		List<AgreementTimeByEmp> agreementTimes = Collections.synchronizedList(new ArrayList<>());
		require.parallelContext().forEach(employeeIds, employeeId -> {
			if (!agreementTimeAll.containsKey(employeeId))
				return;
			List<AgreementTimeOfManagePeriod> agreementTimeByEmp = agreementTimeAll.get(employeeId);

			// 「労働条件項目」を取得
			if (!workingConditionItemAll.containsKey(employeeId))
				return;
			WorkingConditionItemCustom workingConditionItemByEmp = workingConditionItemAll.get(employeeId);

			AgreementYearSetting yearSetByEmp = null;
			if (finalYearSetAll.containsKey(employeeId)) {
				// 36協定年度設定を取得する
				yearSetByEmp = finalYearSetAll.get(employeeId);
			}

			Map<Integer, AgreementMonthSetting> monthSetByEmp = new HashMap<>();
			if (finalMonthSetAll.containsKey(employeeId)) {
				// 36協定年月設定を取得する
				monthSetByEmp = finalMonthSetAll.get(employeeId).stream()
						.collect(Collectors.toMap(x -> x.getYearMonthValue().v(), x -> x));
			}

			List<AgreementTimeByEmp> results = getAgreementTimeByEmp(require, companyId, employeeId, criteria, periodAtrs,
					startYm, agreementTimeByEmp, workingConditionItemByEmp, yearSetByEmp, monthSetByEmp);
			agreementTimes.addAll(results);
		});
		// 年間36協定時間を返す
		return new ArrayList<>(agreementTimes);
	}

	/**
	 * 指定月36協定上限月間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @return 月別実績の36協定上限時間リスト
	 */
	public static List<AgreMaxTimeMonthOut> maxTime(RequireM5 require, 
			String companyId, String employeeId, YearMonthPeriod period) {

		List<AgreMaxTimeMonthOut> results = new ArrayList<>();
		
		// 年月期間を取得　→　年月分ループする
		for (YearMonth procYm = period.start(); procYm.lessThanOrEqualTo(period.end()); procYm = procYm.nextMonth()) {
	
			// 管理期間の36協定時間を取得
			val agreementTimeOfMngPrdOpt = require.agreementTimeOfManagePeriod(employeeId, procYm);
			if (agreementTimeOfMngPrdOpt.isPresent()) {
				results.add(AgreMaxTimeMonthOut.of(
						agreementTimeOfMngPrdOpt.get().getYearMonth(),
						agreementTimeOfMngPrdOpt.get().getAgreementMaxTime().getAgreementTime()));
			}
		}
		
		// 月別実績の36協定月間上限時間リストを返す
		return results;
	}
	
	/**
	 * 指定期間36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param yearMonth 指定年月
	 * @return 36協定上限複数月平均時間
	 */
	public static Optional<AgreMaxAverageTimeMulti> maxAverageTimeMulti(RequireM4 require, 
			String companyId, String employeeId, GeneralDate criteria, YearMonth yearMonth) {
		
		// 「労働条件項目」を取得
		val workingConditionItemOpt = require.workingConditionItem(employeeId, criteria);
		if (!workingConditionItemOpt.isPresent()) return Optional.empty();
		
		// 労働制を確認する
		val workingSystem = workingConditionItemOpt.get().getLaborSystem();
		
		// 36協定基本設定を取得する
		val upperAgreementSet = AgreementDomainService.getBasicSet(require, 
				companyId, employeeId, criteria, workingSystem).getUpperAgreementSetting();
		
		// 上限時間をセット
		int maxMinutes = upperAgreementSet.getUpperMonthAverage().v();
		
		// 指定年月から6ヶ月前を期間とする
		YearMonthPeriod allPeriod = new YearMonthPeriod(yearMonth.addMonths(-5), yearMonth);

		// 管理期間の36協定時間を取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		val agreTimeOfMngPeriodList = require.agreementTimeOfManagePeriod(employeeIds, allPeriod.yearMonthsBetween());
		Map<YearMonth, AgreementTimeOfManagePeriod> agreTimeOfMngPeriodMap = new HashMap<>();
		for (val agreTimeOfMngPeriod : agreTimeOfMngPeriodList) {
			agreTimeOfMngPeriodMap.putIfAbsent(agreTimeOfMngPeriod.getYearMonth(), agreTimeOfMngPeriod);
		}

		// 36協定上限複数月平均時間を作成する
		AgreMaxAverageTimeMulti result = AgreementTimeOfManagePeriod.calcMaxAverageTimeMulti(
				yearMonth, new LimitOneMonth(maxMinutes), agreTimeOfMngPeriodList);
		
		// 36協定上限複数月平均時間を返す
		return Optional.of(result);
	}
	
	/**
	 * 指定年36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @return 36協定年間時間
	 */
	public static Optional<AgreementTimeYear> timeYear(RequireM3 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, GeneralDate criteria, Year year) {
		
		// 「労働条件項目」を取得
		val workingConditionItemOpt = require.workingConditionItem(employeeId, criteria);
		if (!workingConditionItemOpt.isPresent()) return Optional.empty();
		
		// 労働制を確認する
		val workingSystem = workingConditionItemOpt.get().getLaborSystem();
		
		// 36協定基本設定を取得する
		val basicAgreementSet = AgreementDomainService.getBasicSet(require, 
				companyId, employeeId, criteria, workingSystem).getBasicAgreementSetting();
		
		// 上限時間をセット
		int maxMinutes = basicAgreementSet.getLimitOneYear().v();

		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return Optional.empty();

		// 36協定運用設定の取得
		val agreementOpeSetOpt = require.agreementOperationSetting(companyId);
		if (!agreementOpeSetOpt.isPresent()) return Optional.empty();
		val agreementOpeSet = agreementOpeSetOpt.get();
		
		// 年度から36協定の年月期間を取得する
		val period = agreementOpeSet.getYearMonthPeriod(year, closure);
		
		// 管理期間の36協定時間を取得
		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		val agreementTimeOfMngPrdList = require.agreementTimeOfManagePeriod(
				employeeIds, period.yearMonthsBetween());
		int totalMinutes = 0;
		for (val agreementTimeOfMngPrd : agreementTimeOfMngPrdList) {
			
			// 合計時間を合計する
			totalMinutes += agreementTimeOfMngPrd.getAgreementTime().getBreakdown().getTotalTime().v();
		}
		
		// 36協定年間時間を作成する
		AgreementTimeYear result = AgreementTimeYear.of(
				new nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear(maxMinutes),
				new AttendanceTimeYear(totalMinutes),
				AgreTimeYearStatusOfMonthly.NORMAL);
		// エラーチェック
		result.errorCheck();
		
		// 36協定年間時間を返す
		return Optional.of(result);
	}


	private static List<AgreementTimeByPeriod> internalAlgorithm(RequireM8 require, String companyId, String employeeId, GeneralDate criteria,
			Month startMonth, Year year, PeriodAtrOfAgreement periodAtr, 
			AgeementTimeCommonSetting settingGetter) {
		
		List<AgreementTimeByPeriod> results = new ArrayList<>();

		List<String> employeeIds = new ArrayList<>();
		employeeIds.add(employeeId);
		
		// ループする期間を判断
		int stepMon = 2;
		if (periodAtr == PeriodAtrOfAgreement.THREE_MONTHS) stepMon = 3;
		if (periodAtr == PeriodAtrOfAgreement.ONE_MONTH) stepMon = 1;
		if (periodAtr == PeriodAtrOfAgreement.ONE_YEAR) stepMon = 12;
		YearMonth idxYm = YearMonth.of(year.v(), startMonth.v());
		for (int i = 0; i < 12; i += stepMon){
			List<YearMonth> periodYmList = new ArrayList<>();
			for (int ii = 0; ii < stepMon; ii++){
				periodYmList.add(idxYm.addMonths(i + ii));
			}
			
			// 36協定時間を取得
			val agreementTimeList = require.agreementTimeOfManagePeriod(employeeIds, periodYmList);
			if (agreementTimeList.size() == 0) continue;
			
			// 期間をセット
			AgreementTimeByPeriod result = new AgreementTimeByPeriod(
					periodYmList.get(0), periodYmList.get(periodYmList.size() - 1));
			
			// 36協定時間を合計
			for (val agreeemntTime : agreementTimeList){
				result.addMinutesToAgreementTime(agreeemntTime.getAgreementTime().getAgreementTime().getAgreementTime().v());
			}
			
			Optional<Year> checkYearOpt = Optional.empty();
			Optional<YearMonth> checkYmOpt = Optional.empty();
			if (periodAtr == PeriodAtrOfAgreement.ONE_YEAR) checkYearOpt = Optional.of(year);
			if (periodAtr == PeriodAtrOfAgreement.ONE_MONTH) checkYmOpt = Optional.of(periodYmList.get(0));

			// 状態チェック
			{
				// 「労働条件項目」を取得
				val workingConditionItemOpt = getWorkCondition(require, employeeId, criteria, settingGetter);
				if (!workingConditionItemOpt.isPresent()){
					return results;
				}

				// 労働制を確認する
				val workingSystem = workingConditionItemOpt.get().getLaborSystem();
				
				// 36協定基本設定を取得する
				val basicAgreementSet = getBasicSetting(require, companyId, employeeId, criteria, settingGetter, workingSystem);
				
				// 「年度」を確認
				Optional<AgreementYearSetting> yearSetOpt = Optional.empty();
				if (checkYearOpt.isPresent()){
					
					// 36協定年度設定を取得する
					yearSetOpt = require.agreementYearSetting(employeeId, checkYearOpt.get().v());
				}

				// 「年月」を確認
				Optional<AgreementMonthSetting> monthSetOpt = Optional.empty();
				if (checkYmOpt.isPresent()){
					
					// 36協定年月設定を取得する
					monthSetOpt = require.agreementMonthSetting(employeeId, checkYmOpt.get());
				}
				
				// 取得した限度時間をセット
				switch (periodAtr){
				case TWO_MONTHS:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmTwoMonths().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorTwoMonths().v()));
					break;
				case THREE_MONTHS:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmThreeMonths().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorThreeMonths().v()));
					break;
				case ONE_YEAR:
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneYear().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneYear().v()));
					if (yearSetOpt.isPresent()){
						val yearSet = yearSetOpt.get();
						result.setExceptionLimitAlarmTime(Optional.of(
								new LimitOneYear(yearSet.getAlarmOneYear().v())));
						result.setExceptionLimitErrorTime(Optional.of(
								new LimitOneYear(yearSet.getErrorOneYear().v())));
					}
					break;
				default:	// ONE_MONTH
					result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneMonth().v()));
					result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneMonth().v()));
					if (monthSetOpt.isPresent()){
						val monthSet = monthSetOpt.get();
						result.setExceptionLimitAlarmTime(Optional.of(
								new LimitOneYear(monthSet.getAlarmOneMonth().v())));
						result.setExceptionLimitErrorTime(Optional.of(
								new LimitOneYear(monthSet.getErrorOneMonth().v())));
					}
					break;
				}
				
				// チェック処理
				result.errorCheck();
			}
			
			results.add(result);
		}
		
		// 年間36協定時間を返す
		return results;
	}

	private static List<AgreementTimeByEmp> getAgreementTimeByEmp(RequireM6 require, String companyId, String employeeId, 
			GeneralDate criteria, List<PeriodAtrOfAgreement> periodAtrs, YearMonth startYm,
			List<AgreementTimeOfManagePeriod> agreementTimeByEmp, WorkingConditionItemCustom workingConditionItemByEmp,
		  AgreementYearSetting yearSetByEmp, Map<Integer, AgreementMonthSetting> monthSetByEmp){
		List<AgreementTimeByEmp> agreementTimes = new ArrayList<>();
		
		// 労働制を確認する
		val workingSystem = workingConditionItemByEmp.getLaborSystem();

		// 36協定基本設定を取得する
		val basicAgreementSet = AgreementDomainService.getBasicSet(require, 
				companyId, employeeId, criteria, workingSystem)
				.getBasicAgreementSetting();
		
		for (PeriodAtrOfAgreement periodAtr : periodAtrs) {
			// ループする期間を判断
			int stepMon = 2;
			if (periodAtr == PeriodAtrOfAgreement.THREE_MONTHS) stepMon = 3;
			if (periodAtr == PeriodAtrOfAgreement.ONE_MONTH) stepMon = 1;
			if (periodAtr == PeriodAtrOfAgreement.ONE_YEAR) stepMon = 12;

			for (int i = 0; i < 12; i += stepMon) {
				List<YearMonth> periodYmList = new ArrayList<>();
				for (int ii = 0; ii < stepMon; ii++) {
					periodYmList.add(startYm.addMonths(i + ii));
				}

				// 36協定時間を取得
				val agreementTimeList = agreementTimeByEmp.stream().filter(x -> periodYmList.contains(x.getYearMonth()))
						.collect(Collectors.toList());

				if (agreementTimeList.size() == 0) continue;

				// 期間をセット
				AgreementTimeByPeriod result = new AgreementTimeByPeriod(
						periodYmList.get(0), periodYmList.get(periodYmList.size() - 1));

				// 36協定時間を合計
				for (val agreeemntTime : agreementTimeList) {
					result.addMinutesToAgreementTime(agreeemntTime.getAgreementTime().getAgreementTime().getAgreementTime().v());
				}				

				// 取得した限度時間をセット
				switch (periodAtr) {
					case TWO_MONTHS:
						result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmTwoMonths().v()));
						result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorTwoMonths().v()));
						break;
					case THREE_MONTHS:
						result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmThreeMonths().v()));
						result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorThreeMonths().v()));
						break;
					case ONE_YEAR:
						result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneYear().v()));
						result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneYear().v()));
						if (yearSetByEmp != null) {
							result.setExceptionLimitAlarmTime(Optional.of(
									new LimitOneYear(yearSetByEmp.getAlarmOneYear().v())));
							result.setExceptionLimitErrorTime(Optional.of(
									new LimitOneYear(yearSetByEmp.getErrorOneYear().v())));
						}
						break;
					default:    // ONE_MONTH
						result.setLimitAlarmTime(new LimitOneYear(basicAgreementSet.getAlarmOneMonth().v()));
						result.setLimitErrorTime(new LimitOneYear(basicAgreementSet.getErrorOneMonth().v()));
						int month = startYm.addMonths(i + 1).v();
						if (monthSetByEmp.containsKey(month)) {
							val monthSet = monthSetByEmp.get(month);
							result.setExceptionLimitAlarmTime(Optional.of(
									new LimitOneYear(monthSet.getAlarmOneMonth().v())));
							result.setExceptionLimitErrorTime(Optional.of(
									new LimitOneYear(monthSet.getErrorOneMonth().v())));
						}
						break;
				}
				// チェック処理
				result.errorCheck();
				AgreementTimeByEmp agreementTime = new AgreementTimeByEmp(employeeId, periodAtr, result);
				agreementTimes.add(agreementTime);
			}
		}
		return agreementTimes;
	}
	
	/** 指定期間36協定上限複数月平均時間の取得 */
	/** 指定年36協定年間時間の取得 */
	private static Optional<WorkingConditionItem> getWorkCondition(RequireM2 require, 
			String employeeId, GeneralDate criteria, AgeementTimeCommonSetting settingGetter) {
		if(settingGetter == null){
			return require.workingConditionItem(employeeId, criteria);
		}
		return settingGetter.getWorkCondition(employeeId, criteria);
	}

	private static BasicAgreementSetting getBasicSetting(RequireM1 require, String companyId,
			String employeeId, GeneralDate criteria, AgeementTimeCommonSetting settingGetter,
			WorkingSystem workingSystem) {
		if(settingGetter == null){
			return AgreementDomainService.getBasicSet(require, 
					companyId, employeeId, criteria, workingSystem).getBasicAgreementSetting();
		}
		return settingGetter.getBasicSet(companyId, employeeId, criteria, workingSystem);
	} 

	public static interface RequireM8 extends RequireM1, RequireM2, RequireM0 {

		Optional<AgreementYearSetting> agreementYearSetting(String employeeId, int yearMonth);
		
		Optional<AgreementMonthSetting> agreementMonthSetting(String employeeId, YearMonth yearMonth);
	}
	
	public static interface RequireM7 extends RequireM6, RequireM0 {
		
		List<WorkingConditionItemCustom> workingConditionItemCustom(List<String> employeeIds, GeneralDate baseDate);
		
		List<AgreementYearSetting> agreementYearSetting(List<String> employeeIds, int yearMonth);
		
		List<AgreementMonthSetting> agreementMonthSetting(List<String> employeeIds, List<YearMonth> yearMonths);
		
		ManagedParallelWithContext parallelContext();
	}
	
	public static interface RequireM5 {
		
		Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth);
	}
	
	private static interface RequireM6 extends AgreementDomainService.RequireM3 {
	}
	
	public static interface RequireM4 extends RequireM6, RequireM0 {
		
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
	}
	
	private static interface RequireM0 {
		
		List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> employeeIds, List<YearMonth> yearMonths);
	}
	
	public static interface RequireM3 extends ClosureService.RequireM3, RequireM4 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String companyId);
	}
	
	private static interface RequireM2 {
		
		Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate);
	}
	
	private static interface RequireM1 extends AgreementDomainService.RequireM3 {
	}

}
