package nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.algorithm.monthly;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public class LaborTimeSettingService {

	public static Optional<MonthlyWorkTimeSet> laborTimeSettingByCompany(RequireM4 require, WorkingSystem workingSystem, 
			String companyId, YearMonth yearMonth) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.monthlyWorkTimeSetCom(companyId, LaborWorkTypeAttr.REGULAR_LABOR, yearMonth).orElse(null), 
				() -> require.monthlyWorkTimeSetCom(companyId, LaborWorkTypeAttr.DEFOR_LABOR, yearMonth).orElse(null));
	}

	public static Optional<MonthlyWorkTimeSet> laborTimeSettingByEmployment(RequireM3 require, WorkingSystem workingSystem,
			String companyId, YearMonth yearMonth, String employmentCode) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.monthlyWorkTimeSetEmp(companyId, employmentCode, LaborWorkTypeAttr.REGULAR_LABOR, yearMonth).orElse(null), 
				() -> require.monthlyWorkTimeSetEmp(companyId, employmentCode, LaborWorkTypeAttr.DEFOR_LABOR, yearMonth).orElse(null));
	}
	
	public static Optional<MonthlyWorkTimeSet> laborTimeSettingByEmployee(RequireM2 require, WorkingSystem workingSystem,
			String companyId, YearMonth yearMonth, String empId) {
		
		return getWorkingTimeSetting(workingSystem, 
				() -> require.monthlyWorkTimeSetSha(companyId, empId, LaborWorkTypeAttr.REGULAR_LABOR, yearMonth).orElse(null), 
				() -> require.monthlyWorkTimeSetSha(companyId, empId, LaborWorkTypeAttr.DEFOR_LABOR, yearMonth).orElse(null));
	}
	
	public static Optional<MonthlyWorkTimeSet> laborTimeSettingByWorkplace(RequireM1 require,
			  CacheCarrier cacheCarrier, String companyId,
			  WorkingSystem workingSystem,  String employeeId,
			  GeneralDate baseDate, YearMonth yearMonth) {
		
		//所属職場を含む上位階層の職場IDを取得
		List<String> workPlaceIdList = require.getCanUseWorkplaceForEmp(cacheCarrier,companyId, employeeId, baseDate);
		Optional<MonthlyWorkTimeSet> result = Optional.empty();
		//通常勤務　の場合
		if (workingSystem.isRegularWork()) {
			for(String workPlaceId:workPlaceIdList) {
				result = require.monthlyWorkTimeSetWkp(employeeId, workPlaceId, LaborWorkTypeAttr.REGULAR_LABOR, yearMonth).map(c -> c);
				if(result.isPresent()) {
					return result;
				}
			}
		}
		//変形労働勤務　の場合
		else if (workingSystem.isVariableWorkingTimeWork()) {
			for(String workPlaceId:workPlaceIdList) {
				result = require.monthlyWorkTimeSetWkp(employeeId, workPlaceId, LaborWorkTypeAttr.DEFOR_LABOR, yearMonth).map(c -> c);
				if(result.isPresent()) {
					return result;
				}
			}
		}
		return result;
	}
	
	private static Optional<MonthlyWorkTimeSet> getWorkingTimeSetting(WorkingSystem workingSystem,
											Supplier<MonthlyWorkTimeSet> normalSetGetter,
											Supplier<MonthlyWorkTimeSet> deforSetGetter) {
		
		if (workingSystem.isRegularWork()) {// 通常勤務 の場合
			return Optional.ofNullable(normalSetGetter.get());
		} else if (workingSystem.isVariableWorkingTimeWork()) {// 変形労働勤務 の場合
			return Optional.ofNullable(deforSetGetter.get());
		}
		
		return Optional.empty();
	}
	
	public static interface RequireM1 {
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
		
		Optional<MonthlyWorkTimeSetWkp> monthlyWorkTimeSetWkp(String cid, String workplaceId, LaborWorkTypeAttr laborAttr, YearMonth ym);
	}
	
	public static interface RequireM2 {
		
		Optional<MonthlyWorkTimeSetSha> monthlyWorkTimeSetSha(String cid, String sid, LaborWorkTypeAttr laborAttr, YearMonth ym);
	}
	
	public static interface RequireM3 {
		
		Optional<MonthlyWorkTimeSetEmp> monthlyWorkTimeSetEmp(String cid, String empCode, LaborWorkTypeAttr laborAttr, YearMonth ym);
	}

	public static interface RequireM4 {

		Optional<MonthlyWorkTimeSetCom> monthlyWorkTimeSetCom(String cid, LaborWorkTypeAttr laborAttr, YearMonth ym);
		
	}
}
