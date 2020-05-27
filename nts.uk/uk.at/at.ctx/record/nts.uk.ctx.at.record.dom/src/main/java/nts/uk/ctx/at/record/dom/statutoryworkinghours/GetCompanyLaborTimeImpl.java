package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
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
	@Override
	public Optional<WorkingTimeSetting> getComWorkingTimeSetting(String companyId,
																 WorkingSystem workingSystem) {
		val require = new GetCompanyLaborTimeImpl.Require() {
			@Override
			public Optional<ComTransLaborTime> findcomTransLaborTime(String companyId) {
				return comTransLaborTimeRepository.find(companyId);
			}
			
			@Override
			public Optional<ComRegularLaborTime> findcomRegularLaborTime(String companyId) {
				return comRegularLaborTimeRepository.find(companyId);
			}
		};
		return getComWorkingTimeSettingRequire(require, companyId, workingSystem);
	}
	
	@Override
	public Optional<WorkingTimeSetting> getComWorkingTimeSettingRequire(
																 Require require,
																 String companyId,
																 WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			return require.findcomRegularLaborTime(companyId)
												.map(t->t.getWorkingTimeSet());
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			return require.findcomTransLaborTime(companyId)
											  .map(t->t.getWorkingTimeSet());
		}
		return Optional.empty();
	}
	
	public static interface Require{
//		comRegularLaborTimeRepository.find(companyId)
		Optional<ComRegularLaborTime> findcomRegularLaborTime(String companyId);
//		comTransLaborTimeRepository.find(companyId)
		Optional<ComTransLaborTime> findcomTransLaborTime(String companyId);
	}
}
