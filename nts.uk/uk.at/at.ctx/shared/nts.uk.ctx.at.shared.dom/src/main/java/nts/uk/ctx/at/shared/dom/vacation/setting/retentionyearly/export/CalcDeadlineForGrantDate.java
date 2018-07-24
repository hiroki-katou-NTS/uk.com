package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 付与日から期限日を計算
 * @author shuichu_ishida
 */
public interface CalcDeadlineForGrantDate {

	/**
	 * 付与日から期限日を計算
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param grantDate 付与日
	 * @return 期限日
	 */
	GeneralDate algorithm(String companyId, String employeeId, GeneralDate criteriaDate, GeneralDate grantDate);

	/**
	 * 付与日から期限日を計算　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param grantDate 付与日
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 期限日
	 */
	GeneralDate algorithm(String companyId, String employeeId, GeneralDate criteriaDate, GeneralDate grantDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap);

	/**
	 * 付与日から期限日を計算　（月別集計用）
	 * @param grantDate 付与日
	 * @param upperLimitSet 上限設定
	 * @return 期限日
	 */
	GeneralDate algorithm(GeneralDate grantDate, UpperLimitSetting upperLimitSet);
}
