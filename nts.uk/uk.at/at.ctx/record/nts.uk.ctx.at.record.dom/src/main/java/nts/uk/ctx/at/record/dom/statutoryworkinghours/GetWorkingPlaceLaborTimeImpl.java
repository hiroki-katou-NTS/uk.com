package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetWorkingPlaceLaborTimeImpl implements GetWorkingPlaceLaborTime{
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularLaborTimeRepository;
	
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTimeRepository;
	
	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;
	
	/**
	 * 職場の設定を取得
	 * @param workingSystem
	 * @param wkpRegularLaborTime
	 * @param wkpTransLaborTime
	 * @return
	 */
	public Optional<WorkingTimeSetting> getWkpWorkingTimeSetting(String companyId,
																 String employeeId,
																 GeneralDate baseDate,
																 WorkingSystem workingSystem) {
		
		
		//所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRoot(companyId, employeeId, baseDate);
		
		//通常勤務　の場合
		if (workingSystem.isRegularWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = wkpRegularLaborTimeRepository.find(companyId, workPlaceId).map(t->t.getWorkingTimeSet());
				if(result.isPresent()) {
					return result;
				}
			}
		}
		//変形労働勤務　の場合
		else if (workingSystem.isVariableWorkingTimeWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = wkpTransLaborTimeRepository.find(employeeId, workPlaceId).map(t->t.getWorkingTimeSet());
				if(result.isPresent()) {
					return result;
				}
			}
		}
		
		return Optional.empty();
	}
	
	
}
