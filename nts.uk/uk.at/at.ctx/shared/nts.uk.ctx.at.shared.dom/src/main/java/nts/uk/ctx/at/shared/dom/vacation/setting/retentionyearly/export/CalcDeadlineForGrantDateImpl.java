package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 実装：付与日から期限日を計算
 * @author shuichu_ishida
 */
@Stateless
public class CalcDeadlineForGrantDateImpl implements CalcDeadlineForGrantDate {

	/** 社員の保持年数を取得 */
	@Inject
	private GetUpperLimitSetting getUpperLimitSetting;
	
	/** 付与日から期限日を計算 */
	@Override
	public GeneralDate algorithm(String companyId, String employeeId, GeneralDate criteriaDate, GeneralDate grantDate) {
		return this.algorithm(companyId, employeeId, criteriaDate, grantDate, Optional.empty(), Optional.empty());
	}
	
	/** 付与日から期限日を計算 */
	@Override
	public GeneralDate algorithm(String companyId, String employeeId, GeneralDate criteriaDate, GeneralDate grantDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {
		
		// 「社員の保持年数を取得」を実行する
		val upperLimitSet = this.getUpperLimitSetting.algorithm(
				companyId, employeeId, criteriaDate, retentionYearlySet, emptYearlyRetentionSetMap);
		
		return this.algorithm(grantDate, upperLimitSet);
	}
	
	/** 付与日から期限日を計算 */
	@Override
	public GeneralDate algorithm(GeneralDate grantDate,	UpperLimitSetting upperLimitSet) {
		
		// 保持年数を確認する
		int amountYears = 0;
		if (upperLimitSet.getRetentionYearsAmount() != null){
			amountYears = upperLimitSet.getRetentionYearsAmount().v(); 
		}
		if (amountYears <= 0) return grantDate;
		
		// 期限日を計算
		if (grantDate.afterOrEquals(GeneralDate.max().addYears(-amountYears))) return GeneralDate.max();
		return grantDate.addYears(amountYears).addDays(-1);
	}
}
