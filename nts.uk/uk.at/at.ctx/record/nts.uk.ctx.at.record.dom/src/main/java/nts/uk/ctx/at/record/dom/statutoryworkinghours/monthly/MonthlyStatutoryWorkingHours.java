package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.statutoryworkinghours.DailyStatutoryLaborTime;
import nts.uk.ctx.at.shared.dom.common.MonthlyEstimateTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpFlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.FlexSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpFlexSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

public class MonthlyStatutoryWorkingHours {
	
	public static Optional<MonAndWeekStatutoryTime> monAndWeekStatutoryTime(RequireM4 require, CacheCarrier cacheCarrier,
			  String cid, String employmentCd, String employeeId,  GeneralDate baseDate, YearMonth ym, WorkingSystem workingSystem) {
		//enum<労働制> = フレックス勤務、計算対象外　の場合
		if (workingSystem.isFlexTimeWork() || workingSystem.isExcludedWorkingCalculate()) {
			return Optional.empty();
		}
		//週の時間を取得
		MonthlyEstimateTime weeklyTime = getWeeklylyEstimateTime(require, cacheCarrier, cid, 
																employmentCd, employeeId, baseDate, workingSystem);
		//月の時間を取得
		MonthlyEstimateTime monthlyTime = getMonthlyEstimateTime(require, cacheCarrier, cid,
																employmentCd, employeeId, baseDate, ym, workingSystem);
		
		return Optional.of(new MonAndWeekStatutoryTime(weeklyTime,monthlyTime));
	}
	
	public static MonthlyFlexStatutoryLaborTime flexMonAndWeekStatutoryTime(RequireM1 require, CacheCarrier cacheCarrier, 
			String cid, String employmentCd, String employeeId, GeneralDate baseDate, YearMonth ym) {
		
		//取得処理
		return flexMonthlyUnit(require, cacheCarrier, cid, employmentCd, employeeId, baseDate, ym)
				.map(flexMonthUnit -> new MonthlyFlexStatutoryLaborTime(getMonthEstimateTime(flexMonthUnit.getStatutoryList(), ym), 
																		getMonthEstimateTime(flexMonthUnit.getSpecifiedList(), ym), 
																		getMonthEstimateTime(flexMonthUnit.getWeekAveList(), ym)))
				.orElseGet(() -> MonthlyFlexStatutoryLaborTime.zeroMonthlyFlexStatutoryLaborTime());
	}
	
	private static MonthlyEstimateTime getMonthEstimateTime(List<MonthlyUnit> units, YearMonth ym) {
		return units.stream()
				.filter(m -> m.getMonth().v() == ym.month())
				.findFirst()
				.map(c -> c.getMonthlyTime())
				.orElseGet(() -> new MonthlyEstimateTime(0));
	} 
	
	/**
	 * 週の時間を取得
	 * @return
	 */
	private static MonthlyEstimateTime getWeeklylyEstimateTime(RequireM3 require, CacheCarrier cacheCarrier, String cid, 
			String employmentCd, String employeeId, GeneralDate baseDate, WorkingSystem workingSystem) {
		return DailyStatutoryLaborTime.getWorkingTimeSetting(require, cacheCarrier, 
				cid, employmentCd, employeeId, baseDate, workingSystem)
								.map(weeklyTime -> new MonthlyEstimateTime(weeklyTime.getWeeklyTime().getTime().valueAsMinutes()))
								.orElseGet(() -> new MonthlyEstimateTime(0));
	}
	
	
	/**
	 * 月の時間を取得
	 * @return
	 */
	private static MonthlyEstimateTime getMonthlyEstimateTime(RequireM2 require, CacheCarrier cacheCarrier, String cid, 
			String employmentCd, String employeeId, GeneralDate baseDate, YearMonth ym, WorkingSystem workingSystem) {
		
		return getMonthlyUnit(require, cacheCarrier, cid, employmentCd,  employeeId, baseDate, ym, workingSystem)
								.stream()
								   .filter(monthUnit -> monthUnit.getMonth().v() == ym.month())
								   .findFirst()
								   .map(monthUnit -> monthUnit.getMonthlyTime())
								   .orElseGet(() -> new MonthlyEstimateTime(0));
	}
	
	/**
	 * 取得する単位を取得
	 * @return
	 */
	private static List<MonthlyUnit> getMonthlyUnit(RequireM2 require, CacheCarrier cacheCarrier, String cid, 
			String employmentCd, String empId, GeneralDate baseDate, YearMonth ym, WorkingSystem workingSystem){
		
		/*労働時間と日数の設定の利用単位の設定*/
		val usageUnitSetting = require.usageUnitSetting(cid);
		if (!usageUnitSetting.isPresent()) {
			return new ArrayList<>();
		}
	
		if(usageUnitSetting.get().isEmployee()) {//社員の労働時間を管理する場合
			val result = LaborTimeSettingService.laborTimeSettingByEmployee(require, workingSystem, cid, ym, empId);
			if(!result.isEmpty()) {
				return result;
			}
		}
		if(usageUnitSetting.get().isWorkPlace()) {//職場の労働時間を管理する場合
			//職場の設定を取得
			val result = LaborTimeSettingService.laborTimeSettingByWorkplace(require, cacheCarrier, cid, 
																			workingSystem, empId, baseDate, ym);
			if(!result.isEmpty()) {
				return result;
			}
		}
		if(usageUnitSetting.get().isEmployment()) {//雇用の労働時間を管理する場合
			//雇用別設定の取得
			val result = LaborTimeSettingService.laborTimeSettingByEmployment(require, workingSystem, cid, ym, employmentCd);
			if(!result.isEmpty()) {
				return result;
			}
		}
		//会社別設定の取得
		return LaborTimeSettingService.laborTimeSettingByCompany(require, workingSystem, cid, ym);
	}
	
	/**
	 * 取得処理(フレックス)
	 * @return
	 */
	private static Optional<MonthlyFlexStatutoryLaborTimeList> flexMonthlyUnit(RequireM1 require, 
			CacheCarrier cacheCarrier, String cid, String employmentCd, 
			String employeeId, GeneralDate baseDate, YearMonth ym) {
		
		return require.usageUnitSetting(cid).flatMap(usageUnitSetting -> {/*労働時間と日数の設定の利用単位の設定*/
			if(usageUnitSetting.isEmployee()) {//社員の労働時間を管理する場合
				val result =  internalGetFlexSet(() -> 
					require.flexSettingByEmployee(cid, employeeId, ym.year()).orElse(null));
				if(result.isPresent()) {
					return result;
				}
			}
			if(usageUnitSetting.isWorkPlace()) {//職場の労働時間を管理する場合
				//所属職場を含む上位階層の職場IDを取得
				List<String> workPlaceIdList = require.getCanUseWorkplaceForEmp(cacheCarrier, cid, employeeId, baseDate);
				for(String workPlaceId:workPlaceIdList) {
					val result = internalGetFlexSet(() -> 
						require.flexSettingByWorkplace(cid, workPlaceId, ym.year()).orElse(null));
					
					if(result.isPresent()) {
						return result;
					}
				}
			}
			if(usageUnitSetting.isEmployment()) {//雇用の労働時間を管理する場合
				//雇用別設定の取得
				val result = internalGetFlexSet(() -> 
					require.flexSettingByEmployment(cid, employmentCd, ym.year()).orElse(null));
				if(result.isPresent()) {
					return result;
				}
			}
			//会社別設定の取得
			return internalGetFlexSet(() -> require.flexSettingByCompany(cid, ym.year()).orElse(null));
		});
	}
	
	private static Optional<MonthlyFlexStatutoryLaborTimeList> internalGetFlexSet(Supplier<FlexSetting> getter) {
		//会社別設定の取得
		val result = getter.get();
		if(result != null) {
			return Optional.of(new MonthlyFlexStatutoryLaborTimeList(
					result.getSpecifiedSetting(),
					result.getStatutorySetting(),
					result.getWeekAveSetting()));
		}
		
		return Optional.empty();
	}
	
	public static interface RequireM1 extends RequireM0 {
		
		Optional<ShainFlexSetting> flexSettingByEmployee(String cid, String empId, int year);

		Optional<EmpFlexSetting> flexSettingByEmployment(String cid, String emplCode, int year);

		Optional<ComFlexSetting> flexSettingByCompany(String cid, int year);

		Optional<WkpFlexSetting> flexSettingByWorkplace(String cid, String wkpId, int year);
		
		List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId, GeneralDate baseDate);
	}
	
	public static interface RequireM2 extends LaborTimeSettingService.RequireM1,
												LaborTimeSettingService.RequireM2, 
												LaborTimeSettingService.RequireM3, 
												LaborTimeSettingService.RequireM4,
												RequireM0 {
	}
	
	private static interface RequireM0 {
		
		Optional<UsageUnitSetting> usageUnitSetting(String companyId);
	}
	
	public static interface RequireM3 extends DailyStatutoryLaborTime.RequireM1 {} 
	
	public static interface RequireM4 extends RequireM2, RequireM3 {} 
}
