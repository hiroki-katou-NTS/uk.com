package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 社員の保持年数を取得
 * @author shuichi_ishida
 */
public interface GetUpperLimitSetting {

	/**
	 * 社員の保持年数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 上限設定
	 */
	UpperLimitSetting algorithm(String companyId, String employeeId, GeneralDate criteriaDate);

	/**
	 * 社員の保持年数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 上限設定
	 */
	UpperLimitSetting algorithm(String companyId, String employeeId, GeneralDate criteriaDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap);
}
