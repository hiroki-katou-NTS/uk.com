package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeStatusOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 年間超過回数の取得
 * @author shuichi_ishida
 */
public class GetExcessTimesYear {

	/**
	 * [No.458]年間超過回数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	public static AgreementExcessInfo get(RequireM1 require, String employeeId, Year year) {
		
		/** ○管理期間の36協定時間を取得 */
		val agreementDatas = GetAgreementTimeOfMngPeriod.get(require, employeeId, year);
		
		return calcOverTimes(agreementDatas);
	}
	
	/**
	 * [No.458]年間超過回数の取得
	 * @param employeeId List 社員ID
	 * @param year 年度
	 * @return 年間超過回数
	 */
	public static Map<String, AgreementExcessInfo> get(RequireM1 require, List<String> employeeIds, Year year) {
		
		/** ○管理期間の36協定時間を取得 */
		val agreementDatas = GetAgreementTimeOfMngPeriod.get(require, employeeIds, year);
		
		return agreementDatas.entrySet().stream().collect(Collectors.toMap(ad -> ad.getKey(), ad -> {
			
			return calcOverTimes(ad.getValue());
		}));
	}
	
	/**
	 * [NO.555]年間超過回数と残数の取得
	 * @param employeeId 社員ID
	 * @param year 年度
	 * @param baseDate 基準日
	 * @return 年間超過回数
	 */
	public static AgreementExcessInfo getWithRemainTimes(RequireM2 require, String employeeId, Year year, GeneralDate baseDate) {
		
		/** ○[No.458]年間超過回数の取得 */
		val excessInfo = get(require, employeeId, year);
		
		/** 36協定基本設定を取得する */
		val basicAgreementSet = require.basicAgreementSetting(AppContexts.user().companyId(), employeeId, baseDate);
		
		/** ○超過回数の残数 */
		val remainTimes = basicAgreementSet.calcRemainTimes(excessInfo.getExcessTimes());
		
		/** ○36協定超過情報を返す */
		return AgreementExcessInfo.of(excessInfo.getExcessTimes(), remainTimes, excessInfo.getYearMonths());
	}
	
	/** [No.605]年月を指定して年間超過回数の取得 */
	public static AgreementExcessInfo get(RequireM1 require, String employeeId, YearMonth ym) {
		 
		/** 年月を指定して、36協定期間の年度を取得する */
		val year = require.agreementOperationSetting(AppContexts.user().companyId()).map(aos -> aos.getYear(ym)).orElse(null);
		if (year == null) {
			return new AgreementExcessInfo();
		}
		
		/** [No.458]年間超過回数の取得 */
		return get(require, employeeId, year);
	}

	private static AgreementExcessInfo calcOverTimes(List<AgreementTimeOfManagePeriod> agreementDatas) {
		
		/** ○状態が「超過」判定の件数をカウント */
		val overTimes = agreementDatas.stream().mapToInt(ad -> {
			if (isOverAgreementTime(ad)) {
				return 1;
			}
			return 0;
		}).sum();
		
		/** 年月期間を取得する*/
		val yearMonths = agreementDatas.stream().map(ad -> ad.getYm()).collect(Collectors.toList());
		
		/** ○超過している月度を格納 */
		return AgreementExcessInfo.of(overTimes, 0, yearMonths);
	}

	private static boolean isOverAgreementTime(AgreementTimeOfManagePeriod ad) {
		return ad.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR
				|| ad.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_LIMIT_ERROR_SP
				|| ad.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ALARM
				|| ad.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_EXCEPTION_LIMIT_ERROR
				|| ad.getStatus() == AgreementTimeStatusOfMonthly.EXCESS_BG_GRAY;
	}
	
	public static interface RequireM1 extends GetAgreementTimeOfMngPeriod.RequireM1 {
		
	}

	public static interface RequireM2 extends RequireM1 {
	
		BasicAgreementSetting basicAgreementSetting(String cid, String sid, GeneralDate baseDate);
	}
}
