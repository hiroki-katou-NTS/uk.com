package nts.uk.ctx.at.record.dom.monthly.agreement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.record.dom.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.MonthlyAggregationErrorInfo;
import nts.uk.ctx.at.record.dom.monthlyprocess.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.record.dom.weekly.WeeklyCalculation;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreMaxTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.standardtime.primitivevalue.LimitOneMonth;

/**
 * 管理期間の36協定時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeOfManagePeriod extends AggregateRoot {

	/** 社員ID */
	private String employeeId;
	/** 月度 */
	private YearMonth yearMonth;
	/** 年度 */
	private Year year;
	/** 36協定時間 */
	private AgreementTimeManage agreementTime;
	/** 36協定上限時間 */
	private AgreMaxTimeManage agreementMaxTime;

	/** エラー情報 */
	private List<MonthlyAggregationErrorInfo> errorInfos;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 */
	public AgreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth){
		
		super();
		this.employeeId = employeeId;
		this.yearMonth = yearMonth;
		this.year = new Year(yearMonth.year());
		this.agreementTime = new AgreementTimeManage();
		this.agreementMaxTime = new AgreMaxTimeManage();
		
		this.errorInfos = new ArrayList<>();
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 * @param year 年度
	 * @param agreementTime 36協定時間
	 * @param agreementMaxTime 36協定上限時間
	 * @return 管理期間の36協定時間
	 */
	public static AgreementTimeOfManagePeriod of(
			String employeeId,
			YearMonth yearMonth,
			Year year,
			AgreementTimeManage agreementTime,
			AgreMaxTimeManage agreementMaxTime){
	
		AgreementTimeOfManagePeriod domain = new AgreementTimeOfManagePeriod(employeeId, yearMonth);
		domain.year = year;
		domain.agreementTime = agreementTime;
		domain.agreementMaxTime = agreementMaxTime;
		return domain;
	}
	
	/**
	 * ファクトリー　（エラー出力用）
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 * @param errorInfos エラー情報　（月の計算の結果より）
	 * @return 管理期間の36協定時間
	 */
	public static AgreementTimeOfManagePeriod of(
			String employeeId,
			YearMonth yearMonth,
			List<MonthlyAggregationErrorInfo> errorInfos){
	
		AgreementTimeOfManagePeriod domain = new AgreementTimeOfManagePeriod(employeeId, yearMonth);
		domain.errorInfos.addAll(errorInfos);
		return domain;
	}
	
	/**
	 * 作成
	 * @param criteriaDate 基準日
	 * @param aggregateAtr 集計区分
	 * @param monthlyCalculation 月の計算
	 */
	public void aggregate(
			RequireM2 require,
			GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr,
			MonthlyCalculation monthlyCalculation) {

		this.year = monthlyCalculation.getYear();
		
		// 36協定時間の作成
		this.agreementTime.aggregate(require, criteriaDate, aggregateAtr, monthlyCalculation);
		
		// 36協定上限時間の作成
		this.agreementMaxTime.aggregate(require, criteriaDate, aggregateAtr, monthlyCalculation);
	}
	
	public static interface RequireM2 extends AgreementTimeManage.RequireM2, AgreMaxTimeManage.RequireM1 {

	}
	
	public static interface RequireM1 extends AgreementTimeManage.RequireM1 {

	}
	
	/**
	 * 作成　（週用）
	 * @param year 年度
	 * @param criteriaDate 基準日
	 * @param aggregateAtr 集計区分
	 * @param weeklyCalculation 週別の計算
	 * @param companySets 月別集計で必要な会社別設定
	 */
	public void aggregateForWeek(RequireM1 require,
			Year year,
			GeneralDate criteriaDate,
			MonthlyAggregateAtr aggregateAtr,
			WeeklyCalculation weeklyCalculation,
			MonAggrCompanySettings companySets){
		
		this.year = year;
		
		// 36協定時間の作成（週用）
		this.agreementTime.aggregateForWeek(require, criteriaDate, aggregateAtr, weeklyCalculation, companySets);
	}
	
	/**
	 * 36協定複数月平均時間の計算
	 * @param yearMonth 指定年月
	 * @param maxTime 上限時間
	 * @param agreTimeOfMngPeriodList 管理期間の36協定時間リスト
	 * @return 36協定上限複数月平均時間
	 */
	public static AgreMaxAverageTimeMulti calcMaxAverageTimeMulti(
			YearMonth yearMonth,
			LimitOneMonth maxTime,
			List<AgreementTimeOfManagePeriod> agreTimeOfMngPeriodList){

		// Mapに組み換え
		Map<YearMonth, AgreementTimeOfManagePeriod> agreTimeOfMngPeriodMap = new HashMap<>();
		for (val agreTimeOfMngPeriod : agreTimeOfMngPeriodList) {
			agreTimeOfMngPeriodMap.putIfAbsent(agreTimeOfMngPeriod.getYearMonth(), agreTimeOfMngPeriod);
		}
		
		// 36協定上限複数月平均時間を作成する
		AgreMaxAverageTimeMulti result = AgreMaxAverageTimeMulti.of(
				new LimitOneMonth(maxTime.v()), new ArrayList<>());
		
		// 月数分ループ
		for (Integer monNum = 6; monNum >= 2; monNum--) {
		
			// 期間を計算
			YearMonthPeriod period = new YearMonthPeriod(yearMonth.addMonths(-(monNum-1)), yearMonth);
			
			// 36協定上限各月平均時間の計算
			{
				// 合計時間の計算
				Integer totalMinutes = 0;
				for (val procYm : period.yearMonthsBetween()) {
					
					// 労働時間の合計
					if (agreTimeOfMngPeriodMap.containsKey(procYm)) {
						val breakdown = agreTimeOfMngPeriodMap.get(procYm).getAgreementMaxTime().getBreakdown();
						totalMinutes += breakdown.getTotalTime().v();
					}
				}

				// 36協定上限各月平均時間を作成
				AgreMaxAverageTime agreMaxAveTime = AgreMaxAverageTime.of(
						period,
						new AttendanceTimeYear(totalMinutes),
						AgreMaxTimeStatusOfMonthly.NORMAL);
				
				// 36協定複数月平均時間の状態チェック
				agreMaxAveTime.errorCheck(result.getMaxTime());
				
				// 36協定上限各月平均時間を返す
				result.getAverageTimeList().add(agreMaxAveTime);
			}
		}
		
		// 36協定上限複数月平均時間を返す
		return result;
	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfManagePeriod target){
		this.agreementTime.sum(target.agreementTime);
		this.agreementMaxTime.sum(target.agreementMaxTime);
	}
}