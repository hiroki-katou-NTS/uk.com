package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.actualworkinghours.AttendanceTimeOfDailyPerformance;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.agreement.export.GetAgreementPeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.record.dom.standardtime.AgreementOperationSetting;
import nts.uk.ctx.at.record.dom.standardtime.repository.AgreementDomainService;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOutput;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;

/**
 * ドメインサービス：36協定時間の取得
 * @author shuichi_ishida
 */
public class GetAgreementTime {

	/**
	 * 36協定時間の取得
	 * @param companyId 会社ID
	 * @param employeeIds 社員ID
	 * @param yearMonth 年月
	 * @param closureId 締めID
	 * @return 36協定時間一覧
	 */
	public static List<AgreementTimeDetail> get(RequireM5 require, CacheCarrier cacheCarrier, String companyId, 
			List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {
		
		GetAgreementTimeProc proc = new GetAgreementTimeProc();

		return proc.get(require, cacheCarrier, companyId, employeeIds, yearMonth, closureId);
	}
	
	/**
	 * 36協定年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param period 年月期間
	 * @param criteria 基準日
	 * @return 36協定年間時間
	 */
	public static  Optional<AgreementTimeYear> getYear(RequireM3 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, YearMonthPeriod period, GeneralDate criteria) {
		
		AgreementTimeYear result = new AgreementTimeYear();
		
		// 月別集計で必要な会社別設定を取得　（36協定時間用）
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettingsForAgreement(require, companyId);
		if (companySets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return Optional.empty();
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return Optional.empty();
		val agreementOpeSet = companySets.getAgreementOperationSet().get();

		// 年月期間から36協定期間を取得する
		val allAggrPeriodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(period, closure);
		if (!allAggrPeriodOpt.isPresent()) return Optional.empty();
		val allAggrPeriod = allAggrPeriodOpt.get();
		
		// 基準日を含む期間を計算する
		DatePeriod emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), allAggrPeriod.end());
		if (criteria.before(emplSetsPeriod.start())) {
			emplSetsPeriod = new DatePeriod(criteria, allAggrPeriod.end());
		}
		if (criteria.after(emplSetsPeriod.end())) {
			emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), criteria);
		}
		
		// 月別集計で必要な社員別設定を取得
		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier,
				companyId, employeeId, emplSetsPeriod);
		if (employeeSets.getErrorInfos().size() > 0) return Optional.empty();

		// 集計に必要な日別実績データを取得する
		MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadDataForAgreement(
				require, employeeId, allAggrPeriod, employeeSets);

		// 合計時間
		int totalMinutes = 0;
		
		// 年月期間分ループ
		for (val procYm : period.yearMonthsBetween()) {
			
			// 年月の締め日を取得
			val closureHisOpt = closure.getHistoryByYearMonth(procYm);
			if (!closureHisOpt.isPresent()) continue;
			val closureHis = closureHisOpt.get();
			
			// 年月から集計期間を取得
			val aggrPeriodOpt = agreementOpeSet.getAggregatePeriodByYearMonth(procYm, closure);
			if (!aggrPeriodOpt.isPresent()) continue;
			val aggrPeriod = aggrPeriodOpt.get();
			
			// 集計前の月別実績データを確認する
			MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(require, 
					employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate());
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreTimeOfMngPeriodOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					require, cacheCarrier,
					companyId, employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(),
					aggrPeriod.getPeriod(), Optional.empty(), Optional.empty(), Optional.empty(),
					companySets, employeeSets,
					monthlyCalcDailys, monthlyOldDatas, Optional.empty());
			if (agreTimeOfMngPeriodOpt.isPresent()){
				
				// 労働時間の合計
				val breakdown = agreTimeOfMngPeriodOpt.get().getAgreementTime().getBreakdown();
				totalMinutes += breakdown.getTotalTime().v();
			}
		}
		
		// 限度時間
		int limitMinutes = 0;
		
		// 労働制を確認する
		val workingCondItemOpt = employeeSets.getWorkingConditionItem(criteria);
		if (workingCondItemOpt.isPresent()) {
			val workingSystem = workingCondItemOpt.get().getLaborSystem();
			
			// 「36協定基本設定」を取得する
			val basicAgreementSet = AgreementDomainService.getBasicSet(require, companyId, employeeId, criteria, workingSystem)
												.getBasicAgreementSetting();
			limitMinutes = basicAgreementSet.getErrorOneYear().v();
		}
		
		// 36協定年間時間を作成
		result = AgreementTimeYear.of(
				new LimitOneYear(limitMinutes),
				new AttendanceTimeYear(totalMinutes),
				AgreTimeYearStatusOfMonthly.NORMAL);
		
		// エラーチェック
		result.errorCheck();

		// 36協定年間時間を返す
		return Optional.of(result);
	}
	
	/**
	 * 36協定上限複数月平均時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonth 指定年月
	 * @param criteria 基準日
	 * @return 36協定上限複数月平均時間
	 */
	public static Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(RequireM3 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, YearMonth yearMonth, GeneralDate criteria) {
		
		// 月別集計で必要な会社別設定を取得　（36協定時間用）
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettingsForAgreement(require, companyId);
		if (companySets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return Optional.empty();
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return Optional.empty();
		val agreementOpeSet = companySets.getAgreementOperationSet().get();

		// 年月期間から36協定期間を取得する
		YearMonthPeriod allPeriod = new YearMonthPeriod(yearMonth.addMonths(-5), yearMonth);
		val allAggrPeriodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(allPeriod, closure);
		if (!allAggrPeriodOpt.isPresent()) return Optional.empty();
		val allAggrPeriod = allAggrPeriodOpt.get();
		
		// 基準日を含む期間を計算する
		DatePeriod emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), allAggrPeriod.end());
		if (criteria.before(emplSetsPeriod.start())) {
			emplSetsPeriod = new DatePeriod(criteria, allAggrPeriod.end());
		}
		if (criteria.after(emplSetsPeriod.end())) {
			emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), criteria);
		}
		
		// 月別集計で必要な社員別設定を取得
		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier,
				companyId, employeeId, emplSetsPeriod);
		if (employeeSets.getErrorInfos().size() > 0) return Optional.empty();

		// 集計に必要な日別実績データを取得する
		MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadDataForAgreement(require,
				employeeId, allAggrPeriod, employeeSets);

		// 上限時間
		int maxMinutes = 0;
		
		// 労働制を確認する
		val workingCondItemOpt = employeeSets.getWorkingConditionItem(criteria);
		if (workingCondItemOpt.isPresent()) {
			val workingSystem = workingCondItemOpt.get().getLaborSystem();
			
			// 「36協定上限規制」を取得する
			val upperAgreementSet = AgreementDomainService.getBasicSet(require, 
					companyId, employeeId, criteria, workingSystem).getUpperAgreementSetting();
			maxMinutes = upperAgreementSet.getUpperMonthAverage().v();
		}
		
		// 管理期間の36協定時間を取得　（過去6ヶ月分）
		List<AgreementTimeOfManagePeriod> agreTimeOfMngPeriodList = new ArrayList<>();
		for (val procYm : allPeriod.yearMonthsBetween()) {
			
			// 年月の締め日を取得
			val closureHisOpt = closure.getHistoryByYearMonth(procYm);
			if (!closureHisOpt.isPresent()) continue;
			val closureHis = closureHisOpt.get();
			
			// 年月から集計期間を取得
			val aggrPeriodOpt = agreementOpeSet.getAggregatePeriodByYearMonth(procYm, closure);
			if (!aggrPeriodOpt.isPresent()) continue;
			val aggrPeriod = aggrPeriodOpt.get();
			
			// 集計前の月別実績データを確認する
			MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(require,
					employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate());
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreTimeOfMngPeriodOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					require, cacheCarrier,
					companyId, employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(),
					aggrPeriod.getPeriod(), Optional.empty(), Optional.empty(), Optional.empty(),
					companySets, employeeSets,
					monthlyCalcDailys, monthlyOldDatas, Optional.empty());
			if (agreTimeOfMngPeriodOpt.isPresent()){
				agreTimeOfMngPeriodList.add(agreTimeOfMngPeriodOpt.get());
			}
		}

		// 36協定上限複数月平均時間を作成する
		AgreMaxAverageTimeMulti result = AgreementTimeOfManagePeriod.calcMaxAverageTimeMulti(
				yearMonth, new LimitOneMonth(maxMinutes), agreTimeOfMngPeriodList);
		
		// 36協定上限複数月平均時間を返す
		return Optional.of(result);
	}
	
	/**
	 * 36協定上限複数月平均時間と年間時間の取得（日指定）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param averageMonth 指定年月　（複数月平均時間の基準年月）
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定時間Output
	 */
	public static AgreementTimeOutput getAverageAndYear(RequireM4 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, YearMonth averageMonth,
			GeneralDate criteria, ScheRecAtr scheRecAtr) {

		AgreementTimeOutput result = new AgreementTimeOutput();
		
		// 社員に対応する処理締めを取得する
		Closure closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return result;
		
		// 「36協定運用設定」を取得する
		Optional<AgreementOperationSetting> agreementOpeSetOpt = require.agreementOperationSetting(companyId);
		if (!agreementOpeSetOpt.isPresent()) return result;
		
		// 指定日を含む年期間を取得　（RQ579）
		Optional<YearMonthPeriod> yearPeriodOpt = GetAgreementPeriod.containsDate(require,
				companyId, criteria, agreementOpeSetOpt, closure);
		if (!yearPeriodOpt.isPresent()) return result;
		YearMonthPeriod yearPeriod = yearPeriodOpt.get();
		
		// 36協定上限複数月平均時間と年間時間の取得
		result = getAverageAndYearProc(require, cacheCarrier, companyId, employeeId, averageMonth, yearPeriod, criteria, scheRecAtr, closure);
		
		// 取得したOutputを返す
		return result;
	}
	
	/**
	 * 36協定上限複数月平均時間と年間時間の取得（年度指定）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteria 基準日
	 * @param year 年度
	 * @param averageMonth 指定年月　（複数月平均時間の基準年月）
	 * @param scheRecAtr 予実区分
	 * @return 36協定時間Output
	 */
	public static AgreementTimeOutput getAverageAndYear(RequireM3 require, CacheCarrier cacheCarrier,
			String companyId, String employeeId, GeneralDate criteria, Year year,
			YearMonth averageMonth, ScheRecAtr scheRecAtr) {
		
		AgreementTimeOutput result = new AgreementTimeOutput();
		
		// 社員に対応する処理締めを取得する
		val closure = ClosureService.getClosureDataByEmployee(require, cacheCarrier, employeeId, criteria);
		if (closure == null) return result;

		// 「36協定運用設定」を取得する
		Optional<AgreementOperationSetting> agreementOpeSetOpt = require.agreementOperationSetting(companyId);
		if (!agreementOpeSetOpt.isPresent()) return result;
		AgreementOperationSetting agreementOpeSet = agreementOpeSetOpt.get();
		
		// 年度から36協定の年月期間を取得
		YearMonthPeriod yearPeriod = agreementOpeSet.getYearMonthPeriod(year, closure);
		
		// 36協定上限複数月平均時間と年間時間の取得
		getAverageAndYearProc(require, cacheCarrier, companyId, employeeId, averageMonth, yearPeriod, criteria, scheRecAtr, closure);
		
		// 取得したOutputを返す
		return result;
	}

	/**
	 * 36協定上限複数月平均時間と年間時間の取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param averageMonth 指定年月（複数月平均の集計で使用）
	 * @param yearPeriod 年月期間（年間時間の集計で使用）
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @param closure 締め
	 * @return 36協定時間Output
	 */
	private static AgreementTimeOutput getAverageAndYearProc(RequireM2 require, CacheCarrier cacheCarrier, 
			String companyId, String employeeId, YearMonth averageMonth,
			YearMonthPeriod yearPeriod, GeneralDate criteria, ScheRecAtr scheRecAtr, Closure closure) {

		AgreementTimeOutput result = new AgreementTimeOutput();
		AgreementTimeYear timeYear = new AgreementTimeYear();
		
		// 月別集計で必要な会社別設定を取得　（36協定時間用）
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettingsForAgreement(require, companyId);
		if (companySets.getErrorInfos().size() > 0) return result;
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return result;
		AgreementOperationSetting agreementOpeSet = companySets.getAgreementOperationSet().get();
		
		// 集計する期間を求める
		List<YearMonth> averageYmList = new ArrayList<>();	// 平均時間用期間
		List<YearMonth> yearYmList = new ArrayList<>();		// 年間時間用期間
		List<YearMonth> procYmList = new ArrayList<>();		// 集計期間
		{
			// 指定年月から期間を計算する
			YearMonthPeriod averagePeriod = new YearMonthPeriod(averageMonth.addMonths(-5), averageMonth);
			
			// 計算した期間を年月期間（年間時間用期間）から重複している期間を除いて集計期間を求める
			yearYmList.addAll(yearPeriod.yearMonthsBetween());
			averageYmList.addAll(averagePeriod.yearMonthsBetween());
			procYmList.addAll(yearYmList);
			for (YearMonth addYm : averageYmList) {
				if (!procYmList.contains(addYm)) procYmList.add(addYm);
			}
			procYmList.sort((a, b) -> a.compareTo(b));
		}
		if (yearYmList.size() <= 0) return result;
		
		// 指定した年月日時点の締め期間を取得する
		GeneralDate maxDate = GeneralDate.today();
		if (maxDate.before(criteria)) maxDate = criteria;
		maxDate = maxDate.addMonths(1);
		Optional<ClosurePeriod> closurePeriodOpt = closure.getClosurePeriodByYmd(maxDate);
		if (!closurePeriodOpt.isPresent()) return result;
		val closurePeriod = closurePeriodOpt.get();
		YearMonth maxYm = closurePeriod.getYearMonth();		// システム日付または基準日＋1か月時点の年月
		
		// 集計期間から未来の年月を削除する
		ListIterator<YearMonth> itrProcYm = procYmList.listIterator();
		while (itrProcYm.hasNext()) {
			YearMonth checkYm = itrProcYm.next();
			if (checkYm.greaterThan(maxYm)) itrProcYm.remove();
		}
		if (procYmList.size() <= 0) return result;
		procYmList.sort((a, b) -> a.compareTo(b));
		
		// 年月期間から36協定期間を取得する
		YearMonthPeriod allPeriod = new YearMonthPeriod(procYmList.get(0), procYmList.get(procYmList.size()-1));
		val allAggrPeriodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(allPeriod, closure);
		if (!allAggrPeriodOpt.isPresent()) return result;
		val allAggrPeriod = allAggrPeriodOpt.get();
		
		// 基準日を含む期間を計算する
		DatePeriod emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), allAggrPeriod.end());
		if (criteria.before(emplSetsPeriod.start())) {
			emplSetsPeriod = new DatePeriod(criteria, allAggrPeriod.end());
		}
		if (criteria.after(emplSetsPeriod.end())) {
			emplSetsPeriod = new DatePeriod(allAggrPeriod.start(), criteria);
		}
		
		// 月別集計で必要な社員別設定を取得
		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(require, cacheCarrier,
				companyId, employeeId, emplSetsPeriod);
		if (employeeSets.getErrorInfos().size() > 0) return result;
		
		// 36協定時間の集計取得
		Map<YearMonth, AgreementTimeOfManagePeriod> agreTimeMap =
				getAggrAgreementTime(require, cacheCarrier, companyId, employeeId, procYmList,
							scheRecAtr, closure, companySets, employeeSets);
		
		// 限度時間
		int limitMinutes = 0;
		
		// 上限時間
		int maxMinutes = 0;
		
		// 労働制を確認する
		val workingCondItemOpt = employeeSets.getWorkingConditionItem(criteria);
		if (workingCondItemOpt.isPresent()) {
			val workingSystem = workingCondItemOpt.get().getLaborSystem();
			val basicSet = AgreementDomainService.getBasicSet(require, 
					companyId, employeeId, criteria, workingSystem);
			
			// 「36協定基本設定」を取得する
			limitMinutes = basicSet.getBasicAgreementSetting().getErrorOneYear().v();
			
			// 「36協定上限規制」を取得する
			maxMinutes = basicSet.getUpperAgreementSetting().getUpperMonthAverage().v();
		}
		
		// 合計時間
		int yearTotalMinutes = 0;
		
		// 36協定年間時間の取得
		{
			for (YearMonth yearYm : yearYmList) {
				if (agreTimeMap.containsKey(yearYm)) {
					
					// 労働時間の合計
					val breakdown = agreTimeMap.get(yearYm).getAgreementTime().getBreakdown();
					yearTotalMinutes += breakdown.getTotalTime().v();
				}
			}
			
			// 36協定年間時間を作成
			timeYear = AgreementTimeYear.of(
					new LimitOneYear(limitMinutes),
					new AttendanceTimeYear(yearTotalMinutes),
					AgreTimeYearStatusOfMonthly.NORMAL);
			
			// エラーチェック
			timeYear.errorCheck();
		}
		
		// 36協定上限複数月平均時間の取得
		List<AgreementTimeOfManagePeriod> agreTimeOfMngPeriodList = new ArrayList<>();
		for (val averageYm : averageYmList) {
			if (agreTimeMap.containsKey(averageYm)) {
				agreTimeOfMngPeriodList.add(agreTimeMap.get(averageYm));
			}
		}
		AgreMaxAverageTimeMulti timeAverage = AgreementTimeOfManagePeriod.calcMaxAverageTimeMulti(
				averageMonth, new LimitOneMonth(maxMinutes), agreTimeOfMngPeriodList);
		
		// 36協定時間Outputを返す
		result.setAgreementTimeYear(Optional.of(timeYear));
		result.setAgreMaxAverageTimeMulti(Optional.of(timeAverage));
		return result;
	}
	
	/**
	 * 36協定時間の集計取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param ymList 年月期間（List）
	 * @param scheRecAtr 予実区分
	 * @param closure 締め
	 * @param companySets 月別集計で必要な会社別設定
	 * @param employeeSets 月別集計で必要な社員別設定
	 * @return 管理期間の36協定時間リスト（Map）
	 */
	private static Map<YearMonth, AgreementTimeOfManagePeriod> getAggrAgreementTime(RequireM1 require,
			CacheCarrier cacheCarrier, String companyId, String employeeId, List<YearMonth> ymList, ScheRecAtr scheRecAtr,
			Closure closure, MonAggrCompanySettings companySets, MonAggrEmployeeSettings employeeSets) {
		
		Map<YearMonth, AgreementTimeOfManagePeriod> results = new HashMap<>();
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return results;
		AgreementOperationSetting agreementOpeSet = companySets.getAgreementOperationSet().get();
		
		// 最新の締め状態管理を取得
		Optional<ClosureStatusManagement> closureStatusMngOpt = require.latestClosureStatusManagement(employeeId);
		YearMonth latestYm = GeneralDate.min().yearMonth();		// 見つからない時は、最小の年月
		if (closureStatusMngOpt.isPresent()) latestYm = closureStatusMngOpt.get().getYearMonth();
		
		// 「管理期間の36協定時間」を取得　（年月期間の内、最新の「締め状態管理．年月」以前の年月のデータを一括取得する）
		Map<YearMonth, AgreementTimeOfManagePeriod> loadDataMap = new HashMap<>();
		List<String> employeeIds = new ArrayList<String>();
		employeeIds.add(employeeId);
		List<YearMonth> loadYmList = new ArrayList<>();
		for (YearMonth ym : ymList) if (ym.lessThanOrEqualTo(latestYm)) loadYmList.add(ym);
		if (loadYmList.size() > 0) {
			List<AgreementTimeOfManagePeriod> agreementTimeList = require.agreementTimeOfManagePeriod(employeeIds, loadYmList);
			for (AgreementTimeOfManagePeriod agreementTime : agreementTimeList) {
				loadDataMap.put(agreementTime.getYearMonth(), agreementTime);
			}
		}
		
		// 年月期間分処理をループする
		for (YearMonth procYm : ymList) {
			
			// 処理中の年月と取得した「締め状態管理．年月」を比較する
			if (procYm.lessThanOrEqualTo(latestYm)) {
				
				// 「管理期間の36協定時間」を取得
				if (loadDataMap.containsKey(procYm)) results.put(procYm, loadDataMap.get(procYm));
			}
			else {
				
				// 年月の締め日を取得
				val closureHisOpt = closure.getHistoryByYearMonth(procYm);
				if (!closureHisOpt.isPresent()) continue;
				val closureHis = closureHisOpt.get();
				
				// 年月から集計期間を取得
				val aggrPeriodOpt = agreementOpeSet.getAggregatePeriodByYearMonth(procYm, closure);
				if (!aggrPeriodOpt.isPresent()) continue;
				val aggrPeriod = aggrPeriodOpt.get();

				// ※　「社員の労働条件を期間で取得する」は不要。employeeSets の中に、集計する全期間分の事前ロードをしてある。
				
				// 日別実績の勤怠時間リスト　※　このリストにデータを追加すると、追加したデータで実績を上書きして計算する
				List<AttendanceTimeOfDailyPerformance> attendanceTimeOfDailys = new ArrayList<>();
				
				// 日別実績（Work）を取得
				{
					// 予実区分を確認
					if (scheRecAtr == ScheRecAtr.SCHEDULE) {
						
						// 未反映申請の仮反映処理　→　日別実績の勤怠時間リストに追加する
						//***** （未）　申請処理側の実装待ち
					}
				}
				
				// 集計に必要な日別実績データを取得する
				MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadDataForAgreement(
						require, employeeId, aggrPeriod.getPeriod(), attendanceTimeOfDailys, employeeSets);
				
				// 集計前の月別実績データを確認する
				MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(require, 
						employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate());
				
				// 36協定時間の集計
				MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
				Optional<AgreementTimeOfManagePeriod> aggrResultOpt =
						monthlyCalculationForAgreement.aggregateAgreementTime(require, cacheCarrier,
								companyId, employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(),
								aggrPeriod.getPeriod(), Optional.empty(), Optional.empty(), Optional.empty(),
								companySets, employeeSets,
								monthlyCalcDailys, monthlyOldDatas, Optional.empty());
				
				// 「管理期間の36協定時間」を返す
				if (aggrResultOpt.isPresent()) results.put(procYm, aggrResultOpt.get());
			}
		}

		// 「管理期間の36協定時間リスト」を返す
		return results;
	}

	public static interface RequireM5 extends GetAgreementTimeProc.RequireM2 {
		
	}
	
	public static interface RequireM4 extends RequireM3, GetAgreementPeriod.RequireM1 {
		
	}
	
	public static interface RequireM3 extends RequireM2, ClosureService.RequireM3 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String companyId);
	}
	
	public static interface RequireM2 extends RequireM1, MonAggrCompanySettings.RequireM5, MonAggrEmployeeSettings.RequireM2 {

	}
	
	public static interface RequireM1 extends MonthlyCalculatingDailys.RequireM3, MonthlyOldDatas.RequireM1,
		MonthlyCalculation.RequireM2{
		
		Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId);
		
		List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> employeeIds, List<YearMonth> yearMonths);
	}
}
