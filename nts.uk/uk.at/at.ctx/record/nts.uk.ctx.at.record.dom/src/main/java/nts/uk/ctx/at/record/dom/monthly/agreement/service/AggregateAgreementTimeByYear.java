package nts.uk.ctx.at.record.dom.monthly.agreement.service;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeMonth;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeOfYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.AgreementTimeYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.oneyear.AgreementOneYearTime;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.setting.AgreementOperationSetting;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting.AgreementOneYear;
import nts.uk.ctx.at.shared.dom.monthly.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

/** 指定する年度の時間をもとに36協定時間を集計する */
public class AggregateAgreementTimeByYear {

	private static final AgreementOneYearTime DEFAULT_AGREEMENT_TIME = new AgreementOneYearTime(0);

	/** [No.684]指定する年度の時間をもとに36協定時間を集計する */
	public static AgreementTimeYear aggregate(RequireM1 require, String sid,
			GeneralDate baseDate, Year year, 
			Map<YearMonth, AttendanceTimeMonth> agreementTimes) {
		
		/**集計の年月期間を取得する */
		val aggregatePeriod = getAggregatePeriod(require, year);
		if (!aggregatePeriod.isPresent()) {
			return new AgreementTimeYear();
		}
		
		/** パラメータ。36協定時間からMap＜年月、36協定年間時間（Temporary）＞に変換する */
		val yearDatas = agreementTimes.entrySet().stream()
										.collect(Collectors.toMap(at -> at.getKey(), 
																	at -> createYearData(at.getValue())));
		/** 36協定年間時間を取得する */
		return aggregate(require, sid, baseDate, year, aggregatePeriod.get(), yearDatas);
	}
	
	/** 36協定年間時間を取得する */
	private static AgreementTimeYear aggregate(RequireM3 require, String sid,
			GeneralDate baseDate, Year year, YearMonthPeriod ymPeriod,
			Map<YearMonth, Pair<AgreementOneYearTime, AgreementOneYearTime>> agreementTimes) {
		
		/** 対象者の３６協定年間設定を取得する */
		val agreementSet = getAgreementSetting(require, sid, baseDate, year);
		
		/** 36協定年間時間（Temporary）を作成する */
		Pair<AgreementOneYearTime, AgreementOneYearTime> yearTime = Pair.of(DEFAULT_AGREEMENT_TIME, DEFAULT_AGREEMENT_TIME);
		
		/** 年月期間をループする */
		YearMonth current = ymPeriod.start();
		while(current.lessThanOrEqualTo(ymPeriod.end())) {
			
			/** 処理中の年月の36協定時間を取得する */
			val agreementTime = getAgreementTime(require, sid, current, agreementTimes);
			
			/** 36協定年間時間（Temporary）に加算する */
			yearTime = Pair.of(yearTime.getLeft().addMinutes(agreementTime.getLeft().valueAsMinutes()), 
								yearTime.getRight().addMinutes(agreementTime.getRight().valueAsMinutes()));
			
			current = current.addMonths(1);
		}
		
		/** 36協定年間時間を作成して返す */
		val state = agreementSet.check(yearTime.getLeft(), yearTime.getRight());
		
		/** 36協定年間時間を作成して返す */
		return AgreementTimeYear.of(AgreementTimeOfYear.of(
											new AgreementOneYearTime(yearTime.getRight().valueAsMinutes()), 
											agreementSet.getSpecConditionLimit()),
									AgreementTimeOfYear.of(
											new AgreementOneYearTime(yearTime.getLeft().valueAsMinutes()), 
											agreementSet.getSpecConditionLimit()), 
									state);
	}
	
	/** 処理中の年月の36協定時間を取得する */
	public static Pair<AgreementOneYearTime, AgreementOneYearTime> getAgreementTime(
			RequireM5 require, String sid, YearMonth ym,
			Map<YearMonth, Pair<AgreementOneYearTime, AgreementOneYearTime>> agreementTimes) {
		
		/** パラメータ。36協定時間に年月のデータが存在するかをチェックする */
		if (agreementTimes.containsKey(ym)) {
			
			/** パラメータ。36協定時間から36協定時間を取得する */
			return agreementTimes.get(ym);
		}
		
		/** 「管理期間の36協定時間」を取得する */
		return require.agreementTimeOfManagePeriod(sid, ym)
				.map(at -> createYearData(at))
				.orElseGet(() -> createYearData(new AttendanceTimeMonth(0)));
	}
	
	/** 対象者の３６協定年間設定を取得する */
	private static AgreementOneYear getAgreementSetting(RequireM4 require, String sid, GeneralDate baseDate, Year year) {
		
		/** ログイン会社ID */
		val cid = AppContexts.user().companyId();
		
		/**36協定基本設定を取得する */
		val basicSetting = require.basicAgreementSetting(cid, sid, baseDate, year);
		
		/** 取得した「36協定基本設定」。1年間を返す */
		return basicSetting.getOneYear();
	}
	
	/** 36協定年間時間（Temporary）を作成する */
	private static Pair<AgreementOneYearTime, AgreementOneYearTime> createYearData(AttendanceTimeMonth agreementTime) {
		
		return Pair.of(new AgreementOneYearTime(agreementTime.valueAsMinutes()), DEFAULT_AGREEMENT_TIME);
	}
	
	/** 36協定年間時間（Temporary）を作成する */
	private static Pair<AgreementOneYearTime, AgreementOneYearTime> createYearData(AgreementTimeOfManagePeriod agreementTime) {
		
		return Pair.of(new AgreementOneYearTime(agreementTime.getAgreementTime().getAgreementTime().valueAsMinutes()),
						new AgreementOneYearTime(agreementTime.getLegalMaxTime().getAgreementTime().valueAsMinutes()));
	}
	
	/** 集計の年月期間を取得する */
	private static Optional<YearMonthPeriod> getAggregatePeriod(RequireM2 require, Year year) {
		
		/** ログイン会社ID */
		val cid = AppContexts.user().companyId();
		
		/** ○ドメインモデル「３６協定運用設定」を取得 */
		val agreementSetting = require.agreementOperationSetting(cid);
		
		return agreementSetting.map(as -> {

			/** 年月期間を計算する */
			val startYM = YearMonth.of(year.v(), as.getStartingMonth().getMonth());
			val endYM = startYM.addMonths(11);
			
			/** 年月期間を返す */
			return new YearMonthPeriod(startYM, endYM);
		});
	}

	public static interface RequireM5 {
		
		Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym);
	}
	
	public static interface RequireM4 {
		
		BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate, Year year);
	}
	
	public static interface RequireM3 extends RequireM4, RequireM5 {
		
	}

	public static interface RequireM2 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
	}
	
	public static interface RequireM1 extends RequireM2, RequireM3 {
		
	}
}
