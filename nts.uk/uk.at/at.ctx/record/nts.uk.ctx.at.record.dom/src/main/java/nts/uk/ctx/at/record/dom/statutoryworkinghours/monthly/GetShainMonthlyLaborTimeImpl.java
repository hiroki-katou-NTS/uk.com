package nts.uk.ctx.at.record.dom.statutoryworkinghours.monthly;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainDeforLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainNormalSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.MonthlyUnit;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

@Stateless
public class GetShainMonthlyLaborTimeImpl implements GetShainMonthlyLaborTime{
	
	@Inject
	private ShainNormalSettingRepository shainNormalSettingRepository;
	
	@Inject
	private ShainDeforLaborSettingRepository shainDeforLaborSettingRepository;
	
	
	/**
	 * 社員別設定を取得
	 * @param workingSystem
	 * @param shainRegularLaborTime
	 * @param shainTransLaborTime
	 * @return
	 */
	@Override
	public List<MonthlyUnit> getShainWorkingTimeSetting(String companyId, String employeeId, WorkingSystem workingSystem ,YearMonth yearMonth) {
		val require = new GetShainMonthlyLaborTimeImpl.Require() {
			@Override
			public Optional<ShainNormalSetting> findShainNormalSetting(String cid, String empId, int year) {
				return shainNormalSettingRepository.find(companyId, employeeId, yearMonth.year());
			}
			@Override
			public Optional<ShainDeforLaborSetting> findShainDeforLaborSetting(String cid, String empId, int year) {
				return shainDeforLaborSettingRepository.find(companyId, employeeId, yearMonth.year());
			}
		};
		return getShainWorkingTimeSettingRequire(require, companyId, employeeId, workingSystem, yearMonth);
	}
	
	@Override
	public List<MonthlyUnit> getShainWorkingTimeSettingRequire(Require require,String companyId, String employeeId, WorkingSystem workingSystem ,YearMonth yearMonth) {
		if(workingSystem.isRegularWork()) {//通常勤務　の場合
			Optional<ShainNormalSetting> shainNormalSetting = require.findShainNormalSetting(companyId, employeeId, yearMonth.year());
			if(shainNormalSetting.isPresent()) {
				return shainNormalSetting.get().getStatutorySetting();
			}
		}else if(workingSystem.isVariableWorkingTimeWork()) {//変形労働勤務　の場合
			Optional<ShainDeforLaborSetting> ShainDeforLaborSetting = require.findShainDeforLaborSetting(companyId, employeeId, yearMonth.year());
			if(ShainDeforLaborSetting.isPresent()) {
				return ShainDeforLaborSetting.get().getStatutorySetting();
			}
		}
		return new ArrayList<>();
	}
	
	public static interface Require{
//		shainNormalSettingRepository.find(companyId, employeeId, yearMonth.year());
		Optional<ShainNormalSetting> findShainNormalSetting(String cid, String empId, int year);
//		shainDeforLaborSettingRepository.find(companyId, employeeId, yearMonth.year());
		Optional<ShainDeforLaborSetting> findShainDeforLaborSetting(String cid, String empId, int year);
		
	}
}
