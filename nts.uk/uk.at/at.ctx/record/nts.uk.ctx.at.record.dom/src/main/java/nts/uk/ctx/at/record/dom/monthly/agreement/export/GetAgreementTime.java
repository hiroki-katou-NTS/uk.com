package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrCompanySettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonAggrEmployeeSettings;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyCalculatingDailys;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.work.MonthlyOldDatas;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreMaxAverageTimeMulti;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculation;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.calc.MonthlyCalculationByWorkCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.shr.com.context.AppContexts;

/**
 * ドメインサービス：36協定時間の取得
 * @author shuichi_ishida
 */
public class GetAgreementTime {
	
	/**
	 * 【NO.333】36協定時間の取得
	 * @param sid 社員ID
	 * @param ym　年月
	 * @param dailyRecord　上書き日別勤怠(List)
	 * @param baseDate　基準日
	 * @param scheRecAtr　予実区分
	 * @return 管理期間の36協定時間
	 */
	public static AgreementTimeOfManagePeriod get(RequireM6 require, String sid, YearMonth ym,
			List<IntegrationOfDaily> dailyRecord, GeneralDate baseDate, ScheRecAtr scheRecAtr) {
		
		/** 「月別実績の月の計算」を取得する */
		val monthlyCalc = getMonthlyCalcData(require, sid, ym, dailyRecord, baseDate, scheRecAtr);
		
		/** 管理期間の36協定時間の作成 */
		return AgreementTimeOfManagePeriod.aggregate(require, sid, baseDate, ym, monthlyCalc);
	}
	
	/**
	 * 「月別実績の月の計算」を取得する
	 * @param require
	 * @param sid 社員ID
	 * @param ym　年月
	 * @param dailyRecord　日別勤怠（List）
	 * @param baseDate　基準日
	 * @param scheRecAtr　予実区分
	 * @return 月別実績の月の計算
	 */
	private static MonthlyCalculation getMonthlyCalcData(RequireM6 require, String sid, YearMonth ym,
			List<IntegrationOfDaily> dailyRecord, GeneralDate baseDate, ScheRecAtr scheRecAtr) {
		
		/** ○３６協定運用設定を取得 */
		val agrementOperationSet = require.agreementOperationSetting(AppContexts.user().companyId()).orElse(null);
		if (agrementOperationSet == null) {
			return new MonthlyCalculation();
		}
		
		/** 年月から集計期間を取得 */
		val period = agrementOperationSet.getAggregatePeriodByYearMonth(ym);
		
		/** 日別実績の取得(予実区分付き) */
		val dbRecords = getDailyRecords(require, sid, period, scheRecAtr);
		
		/** 対象の日別勤怠一覧にパラメータ。日別勤怠（List）を上書きする */
		val processRecords = dbRecords.stream().map(r -> {
			
			return dailyRecord.stream()
					.filter(or -> r.getEmployeeId().equals(or.getEmployeeId())
							&& r.getYmd().equals(or.getYmd()))
					.findFirst().orElse(r);
		}).collect(Collectors.toList());
		
		/** 「労働条件項目」を取得する */
		val workConItem = require.workingConditionItem(AppContexts.user().companyId(), baseDate, sid);
		
		/** 労働条件ごとに月別実績を集計する */
		val monthlyCalc = MonthlyCalculationByWorkCondition.calcMonth(require, 
				AppContexts.user().companyId(), sid, processRecords, 
				ym, baseDate, workConItem.get(), period);
		
		/** 「月別実績の月の計算」を返す */
		return monthlyCalc;
	}
	
	/** 日別実績の取得(予実区分付き) */
	private static List<IntegrationOfDaily> getDailyRecords(RequireM7 require, String sid,
			DatePeriod period, ScheRecAtr scheRecAtr) {
		
		/** ○日別実績(Work)を取得 */
		val records = require.integrationOfDaily(sid, period);
		
		/** ○INPUT．予実区分を確認 */
		if (scheRecAtr == ScheRecAtr.SCHEDULE) {
			
			/** TODO: 日別実績への申請反映結果を取得 */
		}
		
		/** ○日別実績(Work)Listを返す */
		return records;
	}
	
	public static interface RequireM7 {
		
		List<IntegrationOfDaily> integrationOfDaily(String sid, DatePeriod period);
	}
	
	public static interface RequireM6 extends AgreementTimeOfManagePeriod.RequireM2,
		RequireM7, MonthlyCalculationByWorkCondition.RequireM3 {
		
		Optional<WorkingConditionItem> workingConditionItem(String cid , GeneralDate ymd, String sid);
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String companyId);
		
	}
	
	/** [NO.544]36協定年間時間の取得 
	 * @param sid 社員ID
	 * @param period 年月期間
	 * @param baseDate 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定年間時間
	 */
	public static Optional<AgreementTimeYear> getYear(RequireM5 require,
			String sid, YearMonthPeriod period, GeneralDate baseDate, ScheRecAtr scheRecAtr) {
		
		/** ○ドメインモデル「３６協定運用設定」を取得 */
		val agreementSetting = require.agreementOperationSetting(AppContexts.user().companyId()).orElse(null);
		if (agreementSetting == null) {
			return Optional.empty();
		}
		
		/** 空っぽのMap＜年月、管理期間の36協定時間＞を作成する */
		Map<YearMonth, AgreementTimeOfManagePeriod> data = new HashMap<>();
		
		/** 対象の年月期間の管理期間の36協定時間を取得する */
		AgreementTimeGetService.prepareData(require, sid, period, baseDate, data, scheRecAtr);
		
		/** 年月を指定して、36協定期間の年度を取得する */
		val year = agreementSetting.getYear(period.start());
		
		/** 36協定年間時間を取得する */
		return getYear(require, sid, year, baseDate, data);
	}
	
	/**
	 * 36協定年間時間を取得する
	 * @param sid 社員ID
	 * @param year 年度
	 * @param baseDate 基準日
	 * @param agreementTime 36協定時間：Map＜年月、管理期間の36協定時間＞
	 * @return 36協定年間時間
	 */
	public static Optional<AgreementTimeYear> getYear(RequireM3 require,
			String sid, Year year, GeneralDate baseDate,
			Map<YearMonth, AgreementTimeOfManagePeriod> agreementTime) {
		
		/** パラメータ。36協定時間からMap＜年月、36協定年間時間（Temporary）＞に変換する */
		val mapped = agreementTime.entrySet().stream()
									.collect(Collectors.toMap(a -> a.getKey(), 
															  a -> AgreementTimeYearTemporary.map(a.getValue())));
		/** 集計の年月期間を取得する */
		val ymPeriod = AggregateAgreementTimeByYear.getAggregatePeriod(require, year);
		if (!ymPeriod.isPresent()) {
			return Optional.empty();
		}
		
		/** 36協定年間時間を取得する */
		val result = AggregateAgreementTimeByYear.aggregate(require, sid, baseDate, year, ymPeriod.get(), mapped);
		
		/** 36協定年間時間を返す */
		return Optional.of(result);
	}
	
	/**
	 * [NO.541]36協定上限複数月平均時間の取得
	 * @param dailyRecord 日別勤怠（List）
	 * @param employeeId 社員ID
	 * @param yearMonth 指定年月
	 * @param criteria 基準日
	 * @param scheRecAtr 予実区分
	 * @return 36協定上限複数月平均時間
	 */
	public static Optional<AgreMaxAverageTimeMulti> getMaxAverageMulti(RequireM3 require, List<IntegrationOfDaily> dailyRecord, 
			String employeeId, YearMonth yearMonth, GeneralDate criteria, ScheRecAtr scheRecAtr) {
		
		/** 「パラメータ。年月」からループの年月期間を計算する */
		val ymPeriod = new YearMonthPeriod(yearMonth.addMonths(-5), yearMonth);
		
		/** 対象期間の36協定時間を取得する */
		val data = prepareData(require, employeeId, ymPeriod, criteria, scheRecAtr, dailyRecord);
		
		/** [No.683]指定する年月の時間をもとに36協定時間を集計する */
		return Optional.of(AggregateAgreementTimeByYM.aggregate(require, employeeId, criteria, yearMonth, data));
	}
	
	/**
	 * 対象の年月期間の管理期間の36協定時間を取得する
	 * @param sid 社員ID
	 * @param ymPeriod　年月期間
	 * @param baseDate　基準日
	 * @param preData 36協定時間
	 * @param scheRecAtr　予実区分
	 */
	private static Map<YearMonth, AttendanceTimeMonth> prepareData(RequireM4 require, String sid, YearMonthPeriod ymPeriod, 
			GeneralDate baseDate, ScheRecAtr scheRecAtr, List<IntegrationOfDaily> dailyRecord) {
		
		Map<YearMonth, AttendanceTimeMonth> preData = new HashMap<>();
		
		/** 年月期間をループする */
		YearMonth currentYm = ymPeriod.start();
		while(currentYm.lessThanOrEqualTo(ymPeriod.end())) {
			
			/** パラメータ。36協定時間に処理中の年月のデータが存在するかを確認する */
			if (!preData.containsKey(currentYm)) {
				
				/** 指定月が締め処理済みかどうか判断 */
				if (AgreementTimeGetService.isClosured(require, sid, currentYm)) {
					
					/** 「管理期間の36協定時間」を取得する */
					val agreementTime = require.agreementTimeOfManagePeriod(sid, currentYm);
					if (agreementTime.isPresent()) {
						
						/** パラメータ。36協定時間に入れる */
						preData.put(currentYm, agreementTime.get().getAgreementTime().getAgreementTime());
					}
					
				} else {
					
					/** 【NO.333】36協定時間の取得 */
					val agreementTime = GetAgreementTime.get(require, sid, currentYm, dailyRecord, baseDate, scheRecAtr);
					
					/** パラメータ。36協定時間に入れる */
					preData.put(currentYm, agreementTime.getAgreementTime().getAgreementTime());
				}
			}
			
			currentYm = currentYm.addMonths(1);
		}
		
		return preData;
	}
	
	public static interface RequireM5 extends AgreementTimeGetService.RequireM2, RequireM3 {

	}
	
	public static interface RequireM4 extends AgreementTimeGetService.RequireM2, RequireM6 {
		
	}
	
	public static interface RequireM3 extends AggregateAgreementTimeByYear.RequireM3, 
		AggregateAgreementTimeByYear.RequireM2, RequireM4, AggregateAgreementTimeByYM.RequireM1 {
		
	}
	
	public static interface RequireM2 extends RequireM1, MonAggrCompanySettings.RequireM5, MonAggrEmployeeSettings.RequireM2 {

	}
	
	public static interface RequireM1 extends MonthlyCalculatingDailys.RequireM3, MonthlyOldDatas.RequireM1,
		MonthlyCalculation.RequireM2 {
		
		Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId);
		
		List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> employeeIds, List<YearMonth> yearMonths);
	}
}
