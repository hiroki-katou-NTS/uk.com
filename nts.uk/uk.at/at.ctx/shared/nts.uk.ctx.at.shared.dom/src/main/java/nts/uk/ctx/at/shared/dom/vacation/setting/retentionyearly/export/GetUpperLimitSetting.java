package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import lombok.val;
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
	public static UpperLimitSetting algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate criteriaDate) {
		return algorithm(require, cacheCarrier, companyId, employeeId, criteriaDate, Optional.empty(), Optional.empty());
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
	public static UpperLimitSetting algorithm(RequireM1 require, CacheCarrier cacheCarrier, String companyId, 
			String employeeId, GeneralDate criteriaDate, Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {
		
		// 「所属雇用履歴」を取得する
		val empHisImportOpt = require.employeeEmploymentHis(cacheCarrier, companyId, employeeId, criteriaDate);
		if (empHisImportOpt.isPresent()){
			val employmentCode = empHisImportOpt.get().getEmploymentCode();
			
			// 「雇用積立年休設定」を取得
			Optional<EmptYearlyRetentionSetting> emptYearlyRetentionSetOpt = Optional.empty();
			if (emptYearlyRetentionSetMap.isPresent()){
				if (emptYearlyRetentionSetMap.get().containsKey(employmentCode)){
					emptYearlyRetentionSetOpt = Optional.of(emptYearlyRetentionSetMap.get().get(employmentCode));
				}
			}
			else {
				emptYearlyRetentionSetOpt = require.employmentYearlyRetentionSetting(companyId, employmentCode);
			}
			if (emptYearlyRetentionSetOpt.isPresent()){
				val emptYearlyRetentionSet = emptYearlyRetentionSetOpt.get();
				
				// 管理区分を確認
			/*	if (emptYearlyRetentionSet.getManagementCategory() == ManageDistinct.YES){
					return emptYearlyRetentionSet.getUpperLimitSetting();
				}*/
				return new UpperLimitSetting(new UpperLimitSetCreateMemento(0, 0));
			}
		}
		
		// 「積立年休設定」を取得
		Optional<RetentionYearlySetting> retentionYearlySetOpt = Optional.empty();
		if (retentionYearlySet.isPresent()){
			retentionYearlySetOpt = retentionYearlySet;
		}
		else {
			retentionYearlySetOpt = require.retentionYearlySetting(companyId);
		}
		if (retentionYearlySetOpt.isPresent()){
			
			// 管理区分を確認
			if (retentionYearlySetOpt.get().getManagementCategory() == ManageDistinct.NO){
				return new UpperLimitSetting(new UpperLimitSetCreateMemento(0, 0));
			}
			return retentionYearlySetOpt.get().getUpperLimitSetting();
		}
		return new UpperLimitSetting(new UpperLimitSetCreateMemento(0, 0));
	}
	
	public static interface RequireM1 {

		Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId, String employmentCode);

		Optional<RetentionYearlySetting> retentionYearlySetting(String companyId);
		
		Optional<SEmpHistoryImport> employeeEmploymentHis(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
	}
}
