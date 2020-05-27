package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetShainLaborTimeImpl implements GetShainLaborTime{
	
	@Inject
	private ShainRegularWorkTimeRepository shainRegularWorkTimeRepository;
	
	@Inject
	private ShainTransLaborTimeRepository shainTransLaborTimeRepository;
	
	
	/**
	 * 社員別設定を取得
	 * @param workingSystem
	 * @param shainRegularLaborTime
	 * @param shainTransLaborTime
	 * @return
	 */
	@Override
	public Optional<WorkingTimeSetting> getShainWorkingTimeSetting(String companyId, String employeeId, WorkingSystem workingSystem) {
		
		val require = new GetShainLaborTimeImpl.Require() {
			@Override
			public Optional<ShainTransLaborTime> findShainTransLaborTime(String cid, String empId) {
				return shainTransLaborTimeRepository.find(companyId, employeeId);
			}
			@Override
			public Optional<ShainRegularLaborTime> findShainRegularLaborTime(String Cid, String EmpId) {
				return shainRegularWorkTimeRepository.find(companyId, employeeId);
			}
		};
		
		return getShainWorkingTimeSettingRequire(require, companyId, employeeId, workingSystem);
	}
	
	@Override
	public Optional<WorkingTimeSetting> getShainWorkingTimeSettingRequire(Require require,String companyId, String employeeId, WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return require.findShainRegularLaborTime(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return require.findShainTransLaborTime(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		}
		return Optional.empty();
	}
	
	public static interface Require{
//		shainRegularWorkTimeRepository.find(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		Optional<ShainRegularLaborTime> findShainRegularLaborTime(String Cid, String EmpId);
//		shainTransLaborTimeRepository.find(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		Optional<ShainTransLaborTime> findShainTransLaborTime(String cid, String empId);
		
	}
}
