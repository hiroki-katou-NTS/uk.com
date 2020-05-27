package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetWorkingPlaceMonthlyLaborTimeImpl implements GetWorkingPlaceMonthlyLaborTime{
	
	@Inject
	private WkpNormalSettingRepository wkpNormalSettingRepository;
	
	@Inject
	private WkpDeforLaborSettingRepository wkpDeforLaborSettingRepository;
	
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
	public List<MonthlyUnit> getWkpWorkingTimeSetting(String companyId,
													  String employeeId,
													  GeneralDate baseDate,
													  YearMonth yearMonth,
													  WorkingSystem workingSystem) {
		val require = new GetWorkingPlaceMonthlyLaborTimeImpl.Require() {
			@Override
			public Optional<WkpNormalSetting> findWkpNormalSetting(String cid, String wkpId, int year) {
				return wkpNormalSettingRepository.find(companyId, wkpId, yearMonth.year());
			}
			@Override
			public Optional<WkpDeforLaborSetting> findWkpDeforLaborSetting(String cid, String wkpId, int year) {
				return wkpDeforLaborSettingRepository.find(employeeId, wkpId, yearMonth.year());
			}
		};
		
		val cacheCarrier = new CacheCarrier();
		
		return getWkpWorkingTimeSettingRequire(require, cacheCarrier, companyId, employeeId, baseDate, yearMonth, workingSystem);
	}
	
	@Override
	public List<MonthlyUnit> getWkpWorkingTimeSettingRequire(Require require,
													  CacheCarrier cacheCarrier,
													  String companyId,
													  String employeeId,
													  GeneralDate baseDate,
													  YearMonth yearMonth,
													  WorkingSystem workingSystem) {
		
		//所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = affWorkplaceAdapter.findAffiliatedWorkPlaceIdsToRootRequire(
				cacheCarrier,companyId, employeeId, baseDate);
		
		//通常勤務　の場合
		if (workingSystem.isRegularWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.findWkpNormalSetting(companyId, workPlaceId, yearMonth.year());
				if(result.isPresent()) {
					return result.get().getStatutorySetting();
				}
			}
		}
		//変形労働勤務　の場合
		else if (workingSystem.isVariableWorkingTimeWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.findWkpDeforLaborSetting(employeeId, workPlaceId, yearMonth.year());
				if(result.isPresent()) {
					return result.get().getStatutorySetting();
				}
			}
		}
		return new ArrayList<>();
	}
	
	public static interface Require{
//		wkpNormalSettingRepository.find(companyId, workPlaceId, yearMonth.year());
		Optional<WkpNormalSetting> findWkpNormalSetting(String cid, String wkpId, int year);
//		wkpDeforLaborSettingRepository.find(employeeId, workPlaceId, yearMonth.year());
		Optional<WkpDeforLaborSetting> findWkpDeforLaborSetting(String cid, String wkpId, int year);
	}
}
