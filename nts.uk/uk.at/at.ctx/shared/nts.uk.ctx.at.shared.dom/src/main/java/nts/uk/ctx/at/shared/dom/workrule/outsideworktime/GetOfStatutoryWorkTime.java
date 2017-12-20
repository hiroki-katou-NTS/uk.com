package nts.uk.ctx.at.shared.dom.workrule.outsideworktime;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.DailyTime;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.employment.WorkingSystem;
import nts.uk.ctx.at.shared.dom.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.company.CompanyWtSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employee.EmployeeWtSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.employment.EmploymentWtSettingRepository;
import nts.uk.ctx.at.shared.dom.statutory.worktime.workplace.WorkPlaceWtSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.statutoryworkTime.StatutoryWorkTime;

/**
 * 法定労働時間の取得
 * @author keisuke_hoshina
 *
 */
@Stateless
public class GetOfStatutoryWorkTime {
	
	@Inject
	private UsageUnitSettingRepository usageUnitSettingRepository;
	
	@Inject
	private CompanyWtSettingRepository companyWtSettingRepository;
	
	@Inject
	private WorkPlaceWtSettingRepository workPlaceWtSettingRepository;
	
	@Inject
	private EmploymentWtSettingRepository employmentWtSettingRepository;
	
	@Inject
	private EmployeeWtSettingRepository employeeWtSettingRepository;


	
	/**
	 * 法定労働時間の取得
	 * @return
	 */
	public DailyTime getDailyTimeFromStaturoyWorkTime() {
		//TODO: get StatutoryWorkTime
		Optional<StatutoryWorkTime> statutoryTime = Optional.empty();  
		if(statutoryTime.isPresent()) {
			return statutoryTime.get().getStatutoryWorkTime();
		}
		else {
			return new DailyTime(0);
		}
	}
	
	/**
	 * 1日の時間取得する(固定・変動用)
	 * @param workingSystem 労働制
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param managementCd　雇用コード
	 * @param employeerId 社員ID
	 * @return
	 */
	private Optional<StatutoryWorkTime> TimeOfDailyForFixedAndFluid(WorkingSystem workingSystem,String companyId,String placeId,String employmentCd,String employeerId,GeneralDate targetDate) {
		switch(workingSystem) {
		case RegularWork:
		case VariableWorkingTimeWork:
			//TODO: get StatutoryWorkTime
//			return Optional.of(StatutoryWorkTime(Optional.empty(),statutoryWorkTimeSet().DailyTime(日単位),workingSystem));
		case ExcludedWorkingCalculate:
		case FlexTimeWork:
			return Optional.empty();
		default:
			throw new RuntimeException("unknown workingSystem" + workingSystem);	
		}
	}
	
	/**
	 * 1日の時間取得する(フレックス用)
	 * @param workingSystem 労働制
	 * @param companyId 会社ID
	 * @param placeId 職場ID
	 * @param managementCd　雇用コード
	 * @param employeerId 社員ID
	 * @return
	 */
	private Optional<StatutoryWorkTime> TimeOfDailyForFlex(WorkingSystem workingSystem,String companyId,String placeId,String employmentCd,String employeerId,GeneralDate targetDate) {
		switch(workingSystem) {
		case RegularWork:
		case VariableWorkingTimeWork:
			return Optional.empty();
		case ExcludedWorkingCalculate:
		case FlexTimeWork:
			//TODO: get StatutoryWorkTime
//			設定 = statutoryWorkTimeSet().DailyTime(日単位);
//			return Optional.of(StatutoryWorkTime(Optional.of(設定).所定労働,設定.法定労働,workingSystem));
		default:
			throw new RuntimeException("unknown workingSystem" + workingSystem);	
		}
	}

	/**
	 * 労働時間設定を見て、法定労働時間設定の取得
	 * @param companyId 会社ID
	 * @param workPlaceId 職場ID
	 * @param employmentCd 雇用コード
	 * @param employeerId 社員ID
	 * @param targetDate 対象日
	 * @return 法定労働時間設定
	 */
	private int statutoryWorkTimeSet(String companyId,String workPlaceId,String employmentCd,String employeerId,GeneralDate targetDate){
		val setting = usageUnitSettingRepository.findByCompany(companyId);
		
		//TODO: get 法定労働時間設定
		if(setting.get().isEmployee()) {
			val statutorySet = employeeWtSettingRepository.find(companyId);
		}
		else if(setting.get().isEmployment()) {
			val statutorySet = employmentWtSettingRepository.find(companyId, targetDate.year(), employmentCd);
		}
		else if(setting.get().isWorkPlace()) {
			val statutorySet = workPlaceWtSettingRepository.find(companyId, targetDate.year(), workPlaceId);
		}
		else {
			val statutorySet = companyWtSettingRepository.find(companyId, targetDate.year());
		}
		return 0; 
	}
}
