package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.export;

import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;

/**
 * 実装：社員の保持年数を取得
 * @author shuichi_ishida
 */
@Stateless
public class GetUpperLimitSettingImpl implements GetUpperLimitSetting {

	/** 所属雇用履歴を取得する */
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	/** 雇用積立年休設定 */
	@Inject
	private EmploymentSettingRepository employmentSetRepo;
	/** 積立年休設定 */
	@Inject
	private RetentionYearlySettingRepository retentionYearlySetRepo;
	
	/** 社員の保持年数を取得 */
	@Override
	public UpperLimitSetting algorithm(String companyId, String employeeId, GeneralDate criteriaDate) {
		return this.algorithm(companyId, employeeId, criteriaDate, Optional.empty(), Optional.empty());
	}

	/** 社員の保持年数を取得 */
	@Override
	public UpperLimitSetting algorithm(String companyId, String employeeId, GeneralDate criteriaDate,
			Optional<RetentionYearlySetting> retentionYearlySet,
			Optional<Map<String, EmptYearlyRetentionSetting>> emptYearlyRetentionSetMap) {
		
		// 「所属雇用履歴」を取得する
		val empHisImportOpt = this.sysEmploymentHisAdapter.findSEmpHistBySid(companyId, employeeId, criteriaDate);
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
				emptYearlyRetentionSetOpt = this.employmentSetRepo.find(companyId, employmentCode);
			}
			if (emptYearlyRetentionSetOpt.isPresent()){
				val emptYearlyRetentionSet = emptYearlyRetentionSetOpt.get();
				
				// 管理区分を確認
				if (emptYearlyRetentionSet.getManagementCategory() == ManageDistinct.YES){
					return emptYearlyRetentionSet.getUpperLimitSetting();
				}
				return new UpperLimitSetting(new UpperLimitSetCreateMemento(0, 0));
			}
		}
		
		// 「積立年休設定」を取得
		Optional<RetentionYearlySetting> retentionYearlySetOpt = Optional.empty();
		if (retentionYearlySet.isPresent()){
			retentionYearlySetOpt = retentionYearlySet;
		}
		else {
			retentionYearlySetOpt = this.retentionYearlySetRepo.findByCompanyId(companyId);
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
}
