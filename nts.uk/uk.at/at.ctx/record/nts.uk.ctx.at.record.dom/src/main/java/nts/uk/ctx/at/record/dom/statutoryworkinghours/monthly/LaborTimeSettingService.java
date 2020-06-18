package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthStatutoryWorkingHourDeforWorker;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpNormalSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public class LaborTimeSettingService {

	public static List<MonthlyUnit> laborTimeSettingByCompany(RequireM4 require, WorkingSystem workingSystem, 
			String companyId, YearMonth yearMonth) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.statutoryWorkTimeSetByCompany(companyId, yearMonth.year()).orElse(null), 
				() -> require.statutoryDeforWorkTimeSetByCompany(companyId, yearMonth.year()).orElse(null));
	}

	public static List<MonthlyUnit> laborTimeSettingByEmployment(RequireM3 require, WorkingSystem workingSystem,
			String companyId, YearMonth yearMonth, String employmentCode) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.statutoryWorkTimeSetByEmployment(companyId, employmentCode, yearMonth.year()).orElse(null), 
				() -> require.statutoryDeforWorkTimeSetByEmployment(companyId, employmentCode, yearMonth.year()).orElse(null));
	}
	
	public static List<MonthlyUnit> laborTimeSettingByEmployee(RequireM2 require, WorkingSystem workingSystem,
			String companyId, YearMonth yearMonth, String empId) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.statutoryWorkTimeSetByEmployee(companyId, empId, yearMonth.year()).orElse(null), 
				() -> require.statutoryDeforWorkTimeSetByEmployee(companyId, empId, yearMonth.year()).orElse(null));
	}
	
	public static List<MonthlyUnit> laborTimeSettingByWorkplace(RequireM1 require,
			  CacheCarrier cacheCarrier, String companyId,
			  WorkingSystem workingSystem,  String employeeId,
			  GeneralDate baseDate, YearMonth yearMonth) {
		
		//所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = require.getCanUseWorkplaceForEmp(cacheCarrier,companyId, employeeId, baseDate);
		
		//通常勤務　の場合
		if (workingSystem.isRegularWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.statutoryWorkTimeSetByWorkplace(companyId, workPlaceId, yearMonth.year());
				if(result.isPresent()) {
					return result.get().getStatutorySetting();
				}
			}
		}
		//変形労働勤務　の場合
		else if (workingSystem.isVariableWorkingTimeWork()) {
			for(String workPlaceId:workPlaceIdList) {
				val result = require.statutoryDeforWorkTimeSetByWorkplace(employeeId, workPlaceId, yearMonth.year());
				if(result.isPresent()) {
					return result.get().getStatutorySetting();
				}
			}
		}
		return new ArrayList<>();
	}
	
	private static List<MonthlyUnit> getWorkingTimeSetting(WorkingSystem workingSystem,
											Supplier<MonthStatutoryWorkingHourDeforWorker> normalSetGetter,
											Supplier<MonthStatutoryWorkingHourDeforWorker> deforSetGetter) {
		MonthStatutoryWorkingHourDeforWorker setting;
		
		if (workingSystem.isRegularWork()) {// 通常勤務 の場合
			setting = normalSetGetter.get();
			
			if (setting != null) {
				return setting.getStatutorySetting();
			}
			
		} else if (workingSystem.isVariableWorkingTimeWork()) {// 変形労働勤務 の場合
			setting = deforSetGetter.get();
			
			if (setting != null) {
				return setting.getStatutorySetting();
			}
		}
		return new ArrayList<>();
	}
	
	public static interface RequireM1 {
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<WkpNormalSetting> statutoryWorkTimeSetByWorkplace(String cid, String wkpId, int year);
		
		Optional<WkpDeforLaborSetting> statutoryDeforWorkTimeSetByWorkplace(String cid, String wkpId, int year);
	}
	
	public static interface RequireM2 {
		
		Optional<ShainNormalSetting> statutoryWorkTimeSetByEmployee(String cid, String empId, int year);
		
		Optional<ShainDeforLaborSetting> statutoryDeforWorkTimeSetByEmployee(String cid, String empId, int year);
	}
	
	public static interface RequireM3 {
		
		Optional<ShainNormalSetting> statutoryWorkTimeSetByEmployment(String cid, String emplCode, int year);
		
		Optional<ShainDeforLaborSetting> statutoryDeforWorkTimeSetByEmployment(String cid, String emplCode, int year);
	}

	public static interface RequireM4 {

		Optional<ComNormalSetting> statutoryWorkTimeSetByCompany(String companyId, int year);

		Optional<ComDeforLaborSetting> statutoryDeforWorkTimeSetByCompany(String companyId, int year);
		
	}
}
