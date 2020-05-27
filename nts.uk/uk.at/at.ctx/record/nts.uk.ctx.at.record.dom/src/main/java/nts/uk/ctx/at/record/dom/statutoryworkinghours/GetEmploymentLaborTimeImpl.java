package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
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
	@Override
	public Optional<WorkingTimeSetting> getEmpWorkingTimeSetting(String companyId,
																 String employmentCode,
																 WorkingSystem workingSystem) {
		val require = new GetEmploymentLaborTimeImpl.Require() {
			@Override
			public Optional<EmpRegularLaborTime> findById(String cid, String employmentCode) {
				return empRegularWorkTimeRepository.findById(cid, employmentCode);
			}
			
			@Override
			public Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId) {
				return empTransWorkTimeRepository.find(cid, emplId);
			}
		};
		return getEmpWorkingTimeSettingRequire(require, companyId, employmentCode, workingSystem);
	}
	

	@Override
	public Optional<WorkingTimeSetting> getEmpWorkingTimeSettingRequire(
																 Require require,
																 String companyId,
																 String employmentCode,
																 WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return require.findById(companyId, employmentCode)
					.map(r -> r.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return require.findEmpTransLaborTime(companyId, employmentCode)
					.map(r -> r.getWorkingTimeSet());
		}
		
		return Optional.empty();
	}
	
	public static interface Require{
//		empRegularWorkTimeRepository.findById(companyId, employmentCode)
		Optional<EmpRegularLaborTime> findById(String cid, String employmentCode);
//		empTransWorkTimeRepository.find(companyId, employmentCode)
		Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId);
	}
}
