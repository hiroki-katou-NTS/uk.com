package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;

/**
 * 
 * @author ken_takasu
 *
 */
@Stateless
public class DailyStatutoryWorkingHoursImpl implements DailyStatutoryWorkingHours {

	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;

	@Inject
	private GetShainLaborTime getShainLaborTime;

	@Inject
	private GetWorkingPlaceLaborTime getWorkingPlaceLaborTime;

	@Inject
	private GetEmploymentLaborTime getEmploymentLaborTime;

	@Inject
	private GetCompanyLaborTime getCompanyLaborTime;

	/**
	 * 日の法定労働時間を取得（通常、変形用）
	 * 
	 * @param workingSystem
	 * @param shainRegularLaborTime
	 * @param shainTransLaborTime
	 * @param wkpRegularLaborTime
	 * @param wkpTransLaborTime
	 * @param empRegularLaborTime
	 * @param empTransLaborTime
	 * @param comRegularLaborTime
	 * @param comTransLaborTime
	 * @return
	 */
	public DailyUnit getDailyUnit(String companyId, String employmentCd, String employeeId, GeneralDate baseDate,
			WorkingSystem workingSystem) {

		if (workingSystem.isFlexTimeWork() || workingSystem.isExcludedWorkingCalculate()) {
			return DailyUnit.zero();
		}

		// 取得する単位を取得
		return getWorkingTimeSetting(companyId, employmentCd, employeeId, baseDate, workingSystem)
				.map(w -> w.getDailyTime()).orElse(DailyUnit.zero());
	}

	public DailyUnit getDailyUnit(String companyId, String employmentCd, String employeeId, GeneralDate baseDate,
			WorkingSystem workingSystem, Optional<UsageUnitSetting> usageSetting) {

		if (workingSystem.isFlexTimeWork() || workingSystem.isExcludedWorkingCalculate()) {
			return DailyUnit.zero();
		}

		// 取得する単位を取得
		return getWorkingTimeSetting(companyId, employmentCd, employeeId, baseDate, workingSystem)
				.map(w -> w.getDailyTime()).orElse(DailyUnit.zero());
	}

	/**
	 * 取得する単位を取得
	 * 
	 * @return
	 */
	public Optional<WorkingTimeSetting> getWorkingTimeSetting(String companyId, String employmentCd, String employeeId,
			GeneralDate baseDate, WorkingSystem workingSystem) {

		/* 労働時間と日数の設定の利用単位の設定 */
		return getWorkingTimeSetting(companyId, employmentCd, employeeId, baseDate, workingSystem, 
				usageUnitSettingRepository.findByCompany(companyId));
	}

	public Optional<WorkingTimeSetting> getWorkingTimeSetting(String companyId, String employmentCd, String employeeId,
			GeneralDate baseDate, WorkingSystem workingSystem, Optional<UsageUnitSetting> usageUnitSetting) {

		/* 労働時間と日数の設定の利用単位の設定 */
		if (!usageUnitSetting.isPresent()) {
			return Optional.empty();
		}

		if (usageUnitSetting.get().isEmployee()) {// 社員の労働時間を管理する場合
			val result = getShainLaborTime.getShainWorkingTimeSetting(companyId, employeeId, workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}
		if (usageUnitSetting.get().isWorkPlace()) {// 職場の労働時間を管理する場合
			// 職場の設定を取得
			val result = getWorkingPlaceLaborTime.getWkpWorkingTimeSetting(companyId, employeeId, baseDate,
					workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}
		if (usageUnitSetting.get().isEmployment()) {// 雇用の労働時間を管理する場合
			// 雇用別設定の取得
			val result = getEmploymentLaborTime.getEmpWorkingTimeSetting(companyId, employmentCd, workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}

		// 会社別設定の取得
		return getCompanyLaborTime.getComWorkingTimeSetting(companyId, workingSystem);
	}

}
