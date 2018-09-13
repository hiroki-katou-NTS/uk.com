package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

/**
 * ドメインサービス：日別実績計算処理
 * @author shuichu_ishida
 */
public interface CalculateDailyRecordService {

	/**
	 * 勤務情報を取得して計算
	 * @param integrationOfDaily 日別実績(Work) 
	 * @param companyCommonSetting 会社共通のマスタ管理クラス
	 * @param managePerPersonDailySet 
	 * @param yesterDayInfo 前日の勤務情報
	 * @param tomorrowDayInfo 翌日の勤務情報
	 * @return 日別実績(Work)
	 */
	default public IntegrationOfDaily calculate(
			IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet managePerPersonDailySet,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
			Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		return this.calculate(
				CalculateOption.asDefault(),
				integrationOfDaily,
				companyCommonSetting,
				managePerPersonDailySet,
				yesterDayInfo,
				tomorrowDayInfo);
	}

	/**
	 * 勤務情報を取得して計算
	 * @param integrationOfDaily 日別実績(Work) 
	 * @param companyCommonSetting 会社共通のマスタ管理クラス
	 * @param yesterDayInfo 前日の勤務情報
	 * @param tomorrowDayInfo 翌日の勤務情報
	 * @return 日別実績(Work)
	 */
	public IntegrationOfDaily calculate(
			CalculateOption option,
			IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet managePerPersonDailySet,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo
			,Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo);

}
