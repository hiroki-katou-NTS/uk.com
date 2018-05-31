package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;


@Stateless
public class GetEmploymentLaborTimeImpl implements GetEmploymentLaborTime{
	
	@Inject
	private EmpRegularWorkTimeRepository empRegularWorkTimeRepository;
	
	@Inject
	private EmpTransWorkTimeRepository empTransWorkTimeRepository;
	
	/**
	 * 雇用別設定の取得
	 * @param workingSystem
	 * @param empRegularLaborTime
	 * @param empTransLaborTime
	 * @return
	 */
	public Optional<WorkingTimeSetting> getEmpWorkingTimeSetting(String companyId,
																 String employmentCode,
																 WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return empRegularWorkTimeRepository.findById(companyId, employmentCode)
					.map(r -> r.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return empTransWorkTimeRepository.find(companyId, employmentCode)
					.map(r -> r.getWorkingTimeSet());
		}
		
		return Optional.empty();
	}
	
}
