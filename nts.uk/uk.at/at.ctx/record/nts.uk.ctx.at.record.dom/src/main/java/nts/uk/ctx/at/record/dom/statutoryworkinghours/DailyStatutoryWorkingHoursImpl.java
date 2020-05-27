package nts.uk.ctx.at.record.dom.statutoryworkinghours;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.companyNew.ComTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employeeNew.ShainTransLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpRegularWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employmentNew.EmpTransWorkTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.DailyUnit;
import nts.uk.ctx.at.shared.dom.statutory.worktime.sharedNew.WorkingTimeSetting;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpRegularLaborTimeRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTime;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplaceNew.WkpTransLaborTimeRepository;
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
	
	/*Require用*/
	@Inject
	private ShainRegularWorkTimeRepository shainRegularWorkTimeRepository;
	@Inject
	private ShainTransLaborTimeRepository shainTransLaborTimeRepository;
	@Inject
	private WkpRegularLaborTimeRepository wkpRegularLaborTimeRepository;
	@Inject
	private WkpTransLaborTimeRepository wkpTransLaborTimeRepository;
	@Inject
	private EmpRegularWorkTimeRepository empRegularWorkTimeRepository;
	@Inject
	private EmpTransWorkTimeRepository empTransWorkTimeRepository;
	@Inject
	private ComRegularLaborTimeRepository comRegularLaborTimeRepository;
	@Inject
	private ComTransLaborTimeRepository comTransLaborTimeRepository;
	/*Require用*/

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
		val require = new DailyStatutoryWorkingHoursImpl.Require() {
			@Override
			public Optional<ShainTransLaborTime> findShainTransLaborTime(String cid, String empId) {
				return shainTransLaborTimeRepository.find(cid, empId);
			}
			@Override
			public Optional<ShainRegularLaborTime> findShainRegularLaborTime(String Cid, String EmpId) {
				return shainRegularWorkTimeRepository.find(Cid, EmpId);
			}
			@Override
			public Optional<WkpTransLaborTime> findWkpTransLaborTime(String cid, String wkpId) {
				return wkpTransLaborTimeRepository.find(cid, wkpId);
			}
			@Override
			public Optional<WkpRegularLaborTime> findWkpRegularLaborTime(String empId, String wkpId) {
				return wkpRegularLaborTimeRepository.find(empId, wkpId);
			}
			@Override
			public Optional<EmpRegularLaborTime> findById(String cid, String employmentCode) {
				return empRegularWorkTimeRepository.findById(cid, employmentCode);
			}
			@Override
			public Optional<ComTransLaborTime> findcomTransLaborTime(String companyId) {
				return comTransLaborTimeRepository.find(companyId);
			}
			@Override
			public Optional<ComRegularLaborTime> findcomRegularLaborTime(String companyId) {
				return comRegularLaborTimeRepository.find(companyId);
			}
			@Override
			public Optional<EmpTransLaborTime> findEmpTransLaborTime(String cid, String emplId) {
				return empTransWorkTimeRepository.find(cid, emplId);
			}
		};

		val cacheCarrier = new CacheCarrier();

		return getWorkingTimeSettingRequire(require, cacheCarrier, companyId, employmentCd, employeeId, baseDate, workingSystem);
	}

	@Override
	public Optional<WorkingTimeSetting> getWorkingTimeSettingRequire(
			Require require,
			CacheCarrier cacheCarrier,
			String companyId, String employmentCd, String employeeId,
			GeneralDate baseDate, WorkingSystem workingSystem) {

		/* 労働時間と日数の設定の利用単位の設定 */
		return getWorkingTimeSetting(require, cacheCarrier, companyId, employmentCd, employeeId, baseDate, workingSystem, 
				usageUnitSettingRepository.findByCompany(companyId));
	}

	private Optional<WorkingTimeSetting> getWorkingTimeSetting(Require require, CacheCarrier cacheCarrier,
			String companyId, String employmentCd, String employeeId,
			GeneralDate baseDate, WorkingSystem workingSystem, Optional<UsageUnitSetting> usageUnitSetting) {

		/* 労働時間と日数の設定の利用単位の設定 */
		if (!usageUnitSetting.isPresent()) {
			return Optional.empty();
		}

		if (usageUnitSetting.get().isEmployee()) {// 社員の労働時間を管理する場合
			val result = getShainLaborTime.getShainWorkingTimeSettingRequire(require,companyId, employeeId, workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}
		if (usageUnitSetting.get().isWorkPlace()) {// 職場の労働時間を管理する場合
			// 職場の設定を取得
			val result = getWorkingPlaceLaborTime.getWkpWorkingTimeSettingRequire(require, cacheCarrier, companyId, employeeId, baseDate,
					workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}
		if (usageUnitSetting.get().isEmployment()) {// 雇用の労働時間を管理する場合
			// 雇用別設定の取得
			val result = getEmploymentLaborTime.getEmpWorkingTimeSettingRequire(require, companyId, employmentCd, workingSystem);
			if (result.isPresent()) {
				return result;
			}
		}

		// 会社別設定の取得
		return getCompanyLaborTime.getComWorkingTimeSettingRequire(require, companyId, workingSystem);
	}
	
	public static interface Require extends GetCompanyLaborTimeImpl.Require, 
											GetEmploymentLaborTimeImpl.Require,
											GetWorkingPlaceLaborTimeImpl.Require,
											GetShainLaborTimeImpl.Require {

	}		

}