package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetCompanyLaborTimeImpl implements GetCompanyLaborTime{
	
	@Inject
	private ComRegularLaborTimeRepository comRegularLaborTimeRepository;
	
	@Inject
	private ComTransLaborTimeRepository comTransLaborTimeRepository;
	
	
	/**
	 * 会社別設定の取得
	 * @param workingSystem
	 * @param comRegularLaborTime
	 * @param comTransLaborTime
	 * @return
	 */
	public Optional<WorkingTimeSetting> getComWorkingTimeSetting(String companyId,
																 WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return comRegularLaborTimeRepository.find(companyId)
												.map(t->t.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return comTransLaborTimeRepository.find(companyId)
											  .map(t->t.getWorkingTimeSet());
		}
		return Optional.empty();
	}
	
}
