package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
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
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return shainRegularWorkTimeRepository.find(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return shainTransLaborTimeRepository.find(companyId, employeeId).map(t -> t.getWorkingTimeSet());
		}
		return Optional.empty();
	}
	
}
