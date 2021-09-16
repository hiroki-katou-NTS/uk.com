package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 付与日から期限日を計算
 * 
 * @author shuichu_ishida
 */
public class CalcDeadlineForGrantDate {

	/**
	 * 付与日から期限日を計算
	 * 
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員ID
	 * @param criteriaDate
	 *            基準日
	 * @param grantDate
	 *            付与日
	 * @return 期限日
	 */
	public static GeneralDate algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate criteriaDate, GeneralDate grantDate) {
		return algorithm(require, cacheCarrier, companyId, employeeId, criteriaDate, grantDate, Optional.empty(),
				Optional.empty());
	}

	/**
	 * 付与日から期限日を計算 （月別集計用）
	 * 
	 * @param companyId
	 *            会社ID
	 * @param employeeId
	 *            社員ID
	 * @param criteriaDate
	 *            基準日
	 * @param grantDate
	 *            付与日
	 * @param retentionYearlySet
	 *            積立年休設定
	 * @param emptYearlyRetentionSetMap
	 *            雇用積立年休設定マップ
	 * @return 期限日
	 */
	public static GeneralDate algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate criteriaDate, GeneralDate grantDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {

		// 「社員の保持年数を取得」を実行する
		val upperLimitSet = GetUpperLimitSetting.algorithm(require, cacheCarrier, companyId, retentionYearlySet);

		return algorithm(grantDate, upperLimitSet);
	}

	/**
	 * 付与日から期限日を計算 （月別集計用）
	 * 
	 * @param grantDate
	 *            付与日
	 * @param upperLimitSet
	 *            上限設定
	 * @return 期限日
	 */
	public static GeneralDate algorithm(GeneralDate grantDate, UpperLimitSetting upperLimitSet) {

		// 保持年数を確認する
		int amountYears = 0;
		if (upperLimitSet.getRetentionYearsAmount() != null) {
			amountYears = upperLimitSet.getRetentionYearsAmount().v();
		}
		if (amountYears <= 0)
			return grantDate;

		// 期限日を計算
		if (grantDate.afterOrEquals(GeneralDate.max().addYears(-amountYears)))
			return GeneralDate.max();
		return grantDate.addYears(amountYears).addDays(-1);
	}

	public static interface RequireM1 extends GetUpperLimitSetting.RequireM1 {
	}
}
