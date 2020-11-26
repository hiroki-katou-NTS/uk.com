package nts.uk.ctx.at.record.dom.dailyprocess.calc;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyprocess.calc.CalculateOption;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerCompanySet;
import nts.uk.ctx.at.shared.dom.worktime.common.JustCorrectionAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailycalprocess.calculation.ManagePerPersonDailySet;

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
	default public ManageCalcStateAndResult calculate(
			IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet managePerPersonDailySet,
			JustCorrectionAtr justCorrectionAtr,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo,
			Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo) {
		return this.calculate(
				CalculateOption.asDefault(),
				integrationOfDaily,
				companyCommonSetting,
				managePerPersonDailySet,
				justCorrectionAtr,
				yesterDayInfo,
				tomorrowDayInfo);
	}

	/**
	 * 勤務情報を取得して計算
	 * @param integrationOfDaily 日別実績(Work) 
	 * @param companyCommonSetting 会社共通のマスタ管理クラス
	 * @param justCorrectionAtr ジャスト補正区分
	 * @param yesterDayInfo 前日の勤務情報
	 * @param tomorrowDayInfo 翌日の勤務情報
	 * @return 日別実績(Work)
	 */
	public ManageCalcStateAndResult calculate(
			CalculateOption option,
			IntegrationOfDaily integrationOfDaily,
			ManagePerCompanySet companyCommonSetting,
			ManagePerPersonDailySet managePerPersonDailySet,
			JustCorrectionAtr justCorrectionAtr,
			Optional<WorkInfoOfDailyPerformance> yesterDayInfo
			,Optional<WorkInfoOfDailyPerformance> tomorrowDayInfo);

}
