package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetEmploymentMonthlyLaborTimeImpl implements GetEmploymentMonthlyLaborTime{
	
	@Inject
	private EmpNormalSettingRepository empNormalSettingRepository;
	
	@Inject
	private EmpDeforLaborSettingRepository empDeforLaborSettingRepository;
	
	/**
	 * 雇用別設定の取得
	 * @param workingSystem
	 * @param empRegularLaborTime
	 * @param empTransLaborTime
	 * @return
	 */
	@Override
	public List<MonthlyUnit> getEmpWorkingTimeSetting(String companyId,String employmentCode,YearMonth yearMonth,WorkingSystem workingSystem) {
		val require = new GetEmploymentMonthlyLaborTimeImpl.Require() {
			@Override
			public Optional<EmpNormalSetting> findEmpNormalSetting(String cid, String emplCode, int year) {
				return empNormalSettingRepository.find(companyId, emplCode, yearMonth.year());
			}
			
			@Override
			public Optional<EmpDeforLaborSetting> findEmpDeforLaborSetting(String cid, String emplCode, int year) {
				return empDeforLaborSettingRepository.find(companyId, emplCode, yearMonth.year());
			}
		};
		return getEmpWorkingTimeSettingRequire(require, companyId, employmentCode, yearMonth, workingSystem);
	}
	@Override
	public List<MonthlyUnit> getEmpWorkingTimeSettingRequire(
			Require require,String companyId,String employmentCode,YearMonth yearMonth,WorkingSystem workingSystem) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			Optional<EmpNormalSetting> EmpNormalSetting = require.findEmpNormalSetting(companyId, employmentCode, yearMonth.year());
			if(EmpNormalSetting.isPresent()) {
				return EmpNormalSetting.get().getStatutorySetting();
			}
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			Optional<EmpDeforLaborSetting> EmpDeforLaborSetting = require.findEmpDeforLaborSetting(companyId, employmentCode, yearMonth.year());
			if(EmpDeforLaborSetting.isPresent()) {
				return EmpDeforLaborSetting.get().getStatutorySetting();
			}
		}
		return new ArrayList<>();
	}
	
	public static interface Require{
//		empNormalSettingRepository.find(companyId, employmentCode, yearMonth.year());
		Optional<EmpNormalSetting> findEmpNormalSetting(String cid, String emplCode, int year);
//		empDeforLaborSettingRepository.find(companyId, employmentCode, yearMonth.year());
		Optional<EmpDeforLaborSetting> findEmpDeforLaborSetting(String cid, String emplCode, int year);
	}
	
}
