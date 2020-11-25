package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement;

import java.util.Optional;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyAggregateAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.weekly.WeeklyCalculation;
import nts.uk.shr.com.context.AppContexts;

/**
 * 管理期間の36協定時間
 * @author shuichi_ishida
 */
@Getter
public class AgreementTimeOfManagePeriod extends AggregateRoot {

	/** 社員ID */
	private String sid;
	/** 月度 */
	private YearMonth ym;
	/** 36協定対象時間 */
	private AgreementTimeOfMonthly agreementTime;
	/** 法定上限対象時間 */
	private AgreementTimeOfMonthly legalMaxTime;
	/** 内訳 */
	private AgreementTimeBreakdown breakdown;
	/** 状態 */
	private AgreementTimeStatusOfMonthly status;
	
	/**
	 * コンストラクタ
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 */
	public AgreementTimeOfManagePeriod(String employeeId, YearMonth yearMonth){
		super();
		this.sid = employeeId;
		this.ym = yearMonth;
		this.agreementTime = new AgreementTimeOfMonthly();
		this.legalMaxTime = new AgreementTimeOfMonthly();
		this.breakdown = new AgreementTimeBreakdown();
		this.status = AgreementTimeStatusOfMonthly.NORMAL;
	}
	
	/**
	 * ファクトリー
	 * @param employeeId 社員ID
	 * @param yearMonth 月度
	 * @param agreementTime 36協定時間
	 * @param legalUpperTime 36協定上限時間
	 * @param breakdown 内訳
	 * @param status 状態
	 * @return 管理期間の36協定時間
	 */
	public static AgreementTimeOfManagePeriod of(
			String employeeId,
			YearMonth yearMonth,
			AgreementTimeOfMonthly agreementTime,
			AgreementTimeOfMonthly legalUpperTime,
			AgreementTimeBreakdown breakdown,
			AgreementTimeStatusOfMonthly status){
	
		AgreementTimeOfManagePeriod domain = new AgreementTimeOfManagePeriod(employeeId, yearMonth);
		domain.agreementTime = agreementTime;
		domain.legalMaxTime = legalUpperTime;
		domain.breakdown = breakdown;
		domain.status = status;
		return domain;
	}
	
	/**
	 * 管理期間の36協定時間の作成
	 * @param sid 社員ID
	 * @param ym 年月
	 * @param criteriaDate 基準日
	 * @param monthlyCalculation 月の計算
	 */
	public static AgreementTimeOfManagePeriod aggregate(RequireM2 require, String sid,
													GeneralDate criteriaDate, YearMonth ym,
													MonthlyCalculation monthlyCalculation) {
		val cid = AppContexts.user().companyId();
		/** ○パラメータ「cls<月別実績の月の計算>」を受け取る */
		
		/** 時間外超過設定を取得 */
		val outsideOTSetting = require.outsideOTSetting(cid).orElse(null);
		if (outsideOTSetting == null) {
			return new AgreementTimeOfManagePeriod(sid, ym);
		}
		
		/** 36協定限度時間の対象時間を取得 */
		val breakdown = outsideOTSetting.getTargetTime(require, cid, monthlyCalculation);
		
		/** 閾値の取得 */
		val agreementSet = require.basicAgreementSetting(cid, sid, ym, criteriaDate);
		
		/** 36協定対象時間を計算 */
		val agreementTime = breakdown.calcAgreementTime();
		/** 項目移送により、月別実績の36協定時間を作成する */
		val monthlyAgreementTime = AgreementTimeOfMonthly.of(agreementTime, 
																agreementSet.getOneMonth().getBasic());
		
		/** 法定上限時間を計算 */
		val legalLimitTime = breakdown.calcLegalLimitTime();
		/** 項目移送により、月別実績の36協定時間を作成する */
		val monthlyLegalLimitTime = AgreementTimeOfMonthly.of(legalLimitTime, 
																agreementSet.getOneMonth().getSpecConditionLimit());
		
		/** エラーチェック */
		val state = agreementSet.getOneMonth().check(agreementTime, legalLimitTime);
		
		return AgreementTimeOfManagePeriod.of(sid, ym, monthlyAgreementTime, 
												monthlyLegalLimitTime, breakdown, state);
	}
	
	public static interface RequireM2 extends OutsideOTSetting.RequireM1 {

		Optional<OutsideOTSetting> outsideOTSetting(String cid);
		
		BasicAgreementSetting basicAgreementSetting(String cid, String sid, YearMonth ym, GeneralDate baseDate);
	}
	
	public static interface RequireM1 {

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
		
//		this.year = year;
//		
//		// 36協定時間の作成（週用）
//		this.agreementTime.aggregateForWeek(require, criteriaDate, aggregateAtr, weeklyCalculation, companySets);
	}
	
	/**
	 * 36協定複数月平均時間の計算
	 * @param yearMonth 指定年月
	 * @param maxTime 上限時間
	 * @param agreTimeOfMngPeriodList 管理期間の36協定時間リスト
	 * @return 36協定上限複数月平均時間
	 */
//	public static AgreMaxAverageTimeMulti calcMaxAverageTimeMulti(
//			YearMonth yearMonth,
//			AgreementOneMonthTime maxTime,
//			List<AgreementTimeOfManagePeriod> agreTimeOfMngPeriodList){
//
//		// Mapに組み換え
//		Map<YearMonth, AgreementTimeOfManagePeriod> agreTimeOfMngPeriodMap = new HashMap<>();
//		for (val agreTimeOfMngPeriod : agreTimeOfMngPeriodList) {
//			agreTimeOfMngPeriodMap.putIfAbsent(agreTimeOfMngPeriod.getYm(), agreTimeOfMngPeriod);
//		}
//		
//		// 36協定上限複数月平均時間を作成する
//		AgreMaxAverageTimeMulti result = AgreMaxAverageTimeMulti.of(
//				new AgreementOneMonthTime(maxTime.v()), new ArrayList<>());
//		
//		// 月数分ループ
//		for (Integer monNum = 6; monNum >= 2; monNum--) {
//		
//			// 期間を計算
//			YearMonthPeriod period = new YearMonthPeriod(yearMonth.addMonths(-(monNum-1)), yearMonth);
//			
//			// 36協定上限各月平均時間の計算
//			{
//				// 合計時間の計算
//				Integer totalMinutes = 0;
//				for (val procYm : period.yearMonthsBetween()) {
//					
//					// 労働時間の合計
//					if (agreTimeOfMngPeriodMap.containsKey(procYm)) {
//						val breakdown = agreTimeOfMngPeriodMap.get(procYm).getBreakdown();
//						totalMinutes += breakdown.calcLegalLimitTime().v();
//					}
//				}
//
//				// 36協定上限各月平均時間を作成
//				AgreMaxAverageTime agreMaxAveTime = AgreMaxAverageTime.of(
//						period,
//						new AttendanceTimeYear(totalMinutes),
//						AgreMaxTimeStatusOfMonthly.NORMAL);
//				
//				// 36協定複数月平均時間の状態チェック
//				agreMaxAveTime.errorCheck(result.getMaxTime());
//				
//				// 36協定上限各月平均時間を返す
//				result.getAverageTimeList().add(agreMaxAveTime);
//			}
//		}
//		
//		// 36協定上限複数月平均時間を返す
//		return result;
//	}
	
	/**
	 * 合算する
	 * @param target 加算対象
	 */
	public void sum(AgreementTimeOfManagePeriod target){
		this.agreementTime.sum(target.agreementTime);
		this.legalMaxTime.sum(target.legalMaxTime);
		this.breakdown.sum(target.breakdown);
	}
}