package nts.uk.ctx.at.record.dom.statutoryworkinghours;

//import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
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
	@Override
	public Optional<WorkingTimeSetting> getWkpWorkingTimeSetting(String companyId,
																 String employeeId,
																 GeneralDate baseDate,
																 WorkingSystem workingSystem) {
		val require = new GetWorkingPlaceLaborTimeImpl.Require() {
			@Override
			public Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId) {
				return wkpTransLaborTimeRepository.find(cid, wkpId);
			}
			
			@Override
			public Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String empId, String wkpId) {
				return wkpRegularLaborTimeRepository.find(empId, wkpId);
			}
		};
		
		val cacheCarrier = new CacheCarrier(); 
		
		return getWkpWorkingTimeSettingRequire(require, cacheCarrier, companyId, employeeId, baseDate, workingSystem);
	}
	
	@Override
	public Optional<WorkingTimeSetting> getWkpWorkingTimeSettingRequire(
																 Require require,
																 CacheCarrier cacheCarrier,
																 String companyId,
																 String employeeId,
																 GeneralDate baseDate,
																 WorkingSystem workingSystem) {
		//所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(
				cacheCarrier,
				companyId, 
				employeeId, 
				baseDate);
		
		//通常勤務　の場合
		if (workingSystem.isRegularWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.findWkpRegularLaborTime(companyId, workPlaceId).map(t->t.getWorkingTimeSet());
				if(result.isPresent()) {
					return result;
				}
			}
		}
		//変形労働勤務　の場合
		else if (workingSystem.isVariableWorkingTimeWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.findWkpTransLaborTime(employeeId, workPlaceId).map(t->t.getWorkingTimeSet());
				if(result.isPresent()) {
					return result;
				}
			}
		}
		
		return Optional.empty();
	}
	
	public static interface Require{
//		wkpRegularLaborTimeRepository.find(companyId, workPlaceId).map(t->t.getWorkingTimeSet());
		Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String cid, String wkpId);
//		wkpTransLaborTimeRepository.find(employeeId, workPlaceId).map(t->t.getWorkingTimeSet());
		Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId);
	}
}
