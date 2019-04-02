package nts.uk.ctx.at.record.dom.monthlyprocess.aggr.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.RepositoriesRequiredByMonthlyAggr;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreTimeYearStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneYear;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
import nts.uk.shr.com.time.calendar.period.YearMonthPeriod;

/**
 * 実装：36協定時間の取得
 * @author shuichi_ishida
 */
@Stateless
public class GetAgreementTimeImpl implements GetAgreementTime {

	/** 月別集計が必要とするリポジトリ */
	@Inject
	private RepositoriesRequiredByMonthlyAggr repositories;
	/** ドメインサービス：締め */
	@Inject
	private ClosureService closureService;
	
	/** 36協定時間の取得 */
	@Override
	public List<AgreementTimeDetail> get(String companyId, List<String> employeeIds, YearMonth yearMonth,
			ClosureId closureId) {

		GetAgreementTimeProc proc = new GetAgreementTimeProc(this.repositories);
		return proc.get(companyId, employeeIds, yearMonth, closureId);
	}
	
	/** 36協定年間時間の取得 */
	@Override
	public Optional<AgreementTimeYear> getYear(String companyId, String employeeId, YearMonthPeriod period,
			GeneralDate criteria) {
		
		AgreementTimeYear result = new AgreementTimeYear();
		
		// 月別集計で必要な会社別設定を取得　（36協定時間用）
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettingsForAgreement(
				companyId, this.repositories);
		if (companySets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return Optional.empty();
		val agreementOpeSet = companySets.getAgreementOperationSet().get();

		// 年月期間から36協定期間を取得する
		val allAggrPeriodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(period);
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
		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(
				companyId, employeeId, emplSetsPeriod, this.repositories);
		if (employeeSets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 社員に対応する処理締めを取得する
		val closure = this.closureService.getClosureDataByEmployee(employeeId, criteria);
		if (closure == null) return Optional.empty();

		// 集計に必要な日別実績データを取得する
		MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadDataForAgreement(
				employeeId, allAggrPeriod, this.repositories);

		// 合計時間
		int totalMinutes = 0;
		
		// 年月期間分ループ
		for (val procYm : period.yearMonthsBetween()) {
			
			// 年月の締め日を取得
			val closureHisOpt = closure.getHistoryByYearMonth(procYm);
			if (!closureHisOpt.isPresent()) continue;
			val closureHis = closureHisOpt.get();
			
			// 年月から集計期間を取得
			val aggrPeriodOpt = agreementOpeSet.getAggregatePeriodByYearMonth(procYm);
			if (!aggrPeriodOpt.isPresent()) continue;
			val aggrPeriod = aggrPeriodOpt.get();
			
			// 集計前の月別実績データを確認する
			MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(
					employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(), this.repositories);
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreTimeOfMngPeriodOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					companyId, employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(),
					aggrPeriod.getPeriod(), Optional.empty(), Optional.empty(), companySets, employeeSets,
					monthlyCalcDailys, monthlyOldDatas, Optional.empty(), this.repositories);
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
			val basicAgreementSet = this.repositories.getAgreementDomainService().getBasicSet(
					companyId, employeeId, criteria, workingSystem).getBasicAgreementSetting();
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
	
	/** 36協定上限複数月平均時間の取得 */
	@Override
	public Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(String companyId, String employeeId, YearMonth yearMonth,
			GeneralDate criteria) {
		
		// 月別集計で必要な会社別設定を取得　（36協定時間用）
		MonAggrCompanySettings companySets = MonAggrCompanySettings.loadSettingsForAgreement(
				companyId, this.repositories);
		if (companySets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 「36協定運用設定」を取得
		if (!companySets.getAgreementOperationSet().isPresent()) return Optional.empty();
		val agreementOpeSet = companySets.getAgreementOperationSet().get();

		// 年月期間から36協定期間を取得する
		YearMonthPeriod allPeriod = new YearMonthPeriod(yearMonth.addMonths(-5), yearMonth);
		val allAggrPeriodOpt = agreementOpeSet.getAgreementPeriodByYMPeriod(allPeriod);
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
		MonAggrEmployeeSettings employeeSets = MonAggrEmployeeSettings.loadSettings(
				companyId, employeeId, emplSetsPeriod, this.repositories);
		if (employeeSets.getErrorInfos().size() > 0) return Optional.empty();
		
		// 社員に対応する処理締めを取得する
		val closure = this.closureService.getClosureDataByEmployee(employeeId, criteria);
		if (closure == null) return Optional.empty();

		// 集計に必要な日別実績データを取得する
		MonthlyCalculatingDailys monthlyCalcDailys = MonthlyCalculatingDailys.loadDataForAgreement(
				employeeId, allAggrPeriod, this.repositories);

		// 上限時間
		int maxMinutes = 0;
		
		// 労働制を確認する
		val workingCondItemOpt = employeeSets.getWorkingConditionItem(criteria);
		if (workingCondItemOpt.isPresent()) {
			val workingSystem = workingCondItemOpt.get().getLaborSystem();
			
			// 「36協定上限規制」を取得する
			val upperAgreementSet = this.repositories.getAgreementDomainService().getBasicSet(
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
			val aggrPeriodOpt = agreementOpeSet.getAggregatePeriodByYearMonth(procYm);
			if (!aggrPeriodOpt.isPresent()) continue;
			val aggrPeriod = aggrPeriodOpt.get();
			
			// 集計前の月別実績データを確認する
			MonthlyOldDatas monthlyOldDatas = MonthlyOldDatas.loadData(
					employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(), this.repositories);
			
			// 36協定時間の集計
			MonthlyCalculation monthlyCalculationForAgreement = new MonthlyCalculation();
			val agreTimeOfMngPeriodOpt = monthlyCalculationForAgreement.aggregateAgreementTime(
					companyId, employeeId, procYm, closure.getClosureId(), closureHis.getClosureDate(),
					aggrPeriod.getPeriod(), Optional.empty(), Optional.empty(), companySets, employeeSets,
					monthlyCalcDailys, monthlyOldDatas, Optional.empty(), this.repositories);
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
}
