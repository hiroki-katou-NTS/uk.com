package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.standardtime.BasicAgreementSettingsGetter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.timesetting.BasicAgreementSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@AllArgsConstructor
@NoArgsConstructor
public class AgeementTimeCommonSetting {

	private BasicAgreementSettingsGetter basicSetGetter;
	/**
	 * 社員の年月日の労働条件項目
	 */
	private Map<String, Map<GeneralDate, WorkingConditionItem>> workCondition;
	/**
	 * ３６協定基本設定
	 * @param companyId
	 * @param employeeId
	 * @param criteria
	 * @param workingSystem
	 * @return
	 */
	public BasicAgreementSetting getBasicSet(String companyId, String employeeId, GeneralDate criteria, WorkingSystem workingSystem){
		return basicSetGetter.getBasicSet(companyId, employeeId, criteria, workingSystem);
	}
	/**
	 * 
	 * @param empId
	 * @param criteria
	 * @return
	 */
	public Optional<WorkingConditionItem> getWorkCondition(String empId, GeneralDate criteria) {
		if(workCondition.containsKey(empId)){
			if(workCondition.get(empId).containsKey(criteria)){
				return Optional.of(workCondition.get(empId).get(criteria));
			}
		}
		return Optional.empty();
	}
}
