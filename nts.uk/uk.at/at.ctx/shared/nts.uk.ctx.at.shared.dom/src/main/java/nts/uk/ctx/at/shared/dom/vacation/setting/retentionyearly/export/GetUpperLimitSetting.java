package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 社員の保持年数を取得
 * @author shuichi_ishida
 */
public class GetUpperLimitSetting {

	/**
	 * 社員の保持年数を取得
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @return 上限設定
	 */
	public static Optional<UpperLimitSetting> algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId) {
		return algorithm(require, cacheCarrier, companyId, Optional.empty());
	}


	/**
	 * 社員の保持年数を取得　（月別集計用）
	 * @param companyId 会社ID
	 * @param employeeId 社員ID
	 * @param criteriaDate 基準日
	 * @param retentionYearlySet 積立年休設定
	 * @param emptYearlyRetentionSetMap 雇用積立年休設定マップ
	 * @return 上限設定
	 */
	/** 社員の保持年数を取得 */
	public static Optional<UpperLimitSetting> algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			Optional<RetentionYearlySetting> retentionYearlySet) {

		// 「積立年休設定」を取得
		Optional<RetentionYearlySetting> retentionYearlySetOpt = Optional.empty();
		if (retentionYearlySet.isPresent()){
			retentionYearlySetOpt = retentionYearlySet;
		}
		else {
			retentionYearlySetOpt = require.retentionYearlySetting(companyId);
		}
		if (!retentionYearlySetOpt.isPresent()){
			throw new RuntimeException();
		}

		// 管理区分を確認
		if (retentionYearlySetOpt.get().getManagementCategory() == ManageDistinct.NO){
			return Optional.empty();
		}
		return Optional.of(retentionYearlySetOpt.get().getUpperLimitSetting());
		
	}

	public static interface RequireM1 {

		Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId, String employmentCode);

		Optional<RetentionYearlySetting> retentionYearlySetting(String companyId);

		Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
	}
}
